package com.ll.ilta.domain.problem.service;

import com.ll.ilta.domain.favorite.dto.FavoriteResponseDto;
import com.ll.ilta.domain.favorite.repository.FavoriteRepository;
import com.ll.ilta.domain.image.client.AiFeignClient;
import com.ll.ilta.domain.image.dto.AiResponseDto;
import com.ll.ilta.domain.image.dto.SupabaseResponseDto;
import com.ll.ilta.domain.image.entity.Image;
import com.ll.ilta.domain.image.repository.ImageRepository;
import com.ll.ilta.domain.image.service.SupabaseUploader;
import com.ll.ilta.domain.member.v1.entity.Member;
import com.ll.ilta.domain.member.v1.service.MemberService;
import com.ll.ilta.domain.problem.dto.ConceptDto;
import com.ll.ilta.domain.problem.dto.ProblemResponseDto;
import com.ll.ilta.domain.problem.entity.Concept;
import com.ll.ilta.domain.problem.entity.Problem;
import com.ll.ilta.domain.problem.entity.ProblemConcept;
import com.ll.ilta.domain.problem.entity.ProblemResult;
import com.ll.ilta.domain.problem.repository.ConceptRepository;
import com.ll.ilta.domain.problem.repository.ProblemConceptRepository;
import com.ll.ilta.domain.problem.repository.ProblemRepository;
import com.ll.ilta.domain.problem.repository.ProblemResultRepository;
import com.ll.ilta.global.common.dto.CursorPaginatedResponseDto;
import com.ll.ilta.global.common.service.CursorUtil;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class ProblemService {

    private static final String PROBLEMS_LIST_URL = "/api/v1/problem/list";

    private final MemberService memberService;
    private final SupabaseUploader supabaseUploader;
    private final AiFeignClient aiFeignClient;
    private final ProblemRepository problemRepository;
    private final ProblemResultRepository problemResultRepository;
    private final ImageRepository imageRepository;
    private final FavoriteRepository favoriteRepository;
    private final ConceptRepository conceptRepository;
    private final ProblemConceptRepository problemConceptRepository;

    @Value("${supabase.image-base-url}")
    private String baseUrl;

    @Transactional
    public ProblemResponseDto createProblemWithImage(Long userId, MultipartFile file) {
        Member member = memberService.findById(userId);

        SupabaseResponseDto uploadDto = supabaseUploader.upload(userId, file);
        String imageUrl = baseUrl + '/' + uploadDto.getKey();

        Problem problem = problemRepository.save(Problem.from(member));

        AiResponseDto aiResponseDto = aiFeignClient.sendImageToAiServer(file);

        ProblemResult result = ProblemResult.of(aiResponseDto.getOcrResult(), aiResponseDto.getLlmResult(), true,
            problem);
        problemResultRepository.save(result);

        List<ConceptDto> conceptDtos = aiResponseDto.getConceptTags().stream()
            .map(tag -> ConceptDto.of(tag.getId(), tag.getName())).toList();

        List<Concept> savedConcepts = conceptDtos.stream().map(
                dto -> conceptRepository.findByName(dto.getName()).orElseGet(() -> conceptRepository.save(dto.toEntity())))
            .collect(Collectors.toList());

        List<ProblemConcept> problemConcepts = createProblemConcepts(problem, savedConcepts);

        problemConceptRepository.saveAll(problemConcepts);

        Image image = imageRepository.save(Image.of(imageUrl, problem));

        return ProblemResponseDto.of(problem.getId(), image.getImageUrl(), conceptDtos, false, result.getOcrResult(),
            result.getLlmResult(), problem.getCreatedAt());
    }

    public List<ProblemConcept> createProblemConcepts(Problem problem, List<Concept> concepts) {
        return concepts.stream().map(concept -> ProblemConcept.of(problem, concept)).toList();
    }

    public CursorPaginatedResponseDto<ProblemResponseDto> getProblemList(Long childId, int limit, String afterCursor) {
        List<ProblemResponseDto> problems = problemRepository.findProblemWithCursor(childId, afterCursor, limit + 1);

        if (problems.isEmpty()) {
            return CursorPaginatedResponseDto.of(problems, limit, false, null, buildSelfUrl(limit, afterCursor), null);
        }

        boolean hasNextPage = problems.size() > limit;

        String nextCursor = null;
        if (hasNextPage) {
            ProblemResponseDto lastItem = problems.get(limit - 1); // 현재 페이지 마지막
            nextCursor = CursorUtil.encodeCursor(lastItem.getId(), lastItem.getCreatedAt());
        }

        if (hasNextPage) {
            problems = problems.subList(0, limit);
        }

        String selfUrl = buildSelfUrl(limit, afterCursor);
        String nextUrl = hasNextPage ? buildNextUrl(limit, nextCursor) : null;

        return CursorPaginatedResponseDto.of(problems, limit, hasNextPage, nextCursor, selfUrl, nextUrl);
    }

    public ProblemResponseDto getProblemDetail(Long problemId) {
        return problemRepository.findProblemById(problemId);
    }

    @Transactional
    public void deleteProblem(Long problemId) {
        favoriteRepository.deleteByProblemId(problemId);
        imageRepository.deleteByProblemId(problemId);
        problemResultRepository.deleteByProblemId(problemId);
        problemConceptRepository.deleteByProblemId(problemId);
        problemRepository.deleteById(problemId);
    }

    private String buildSelfUrl(int limit, String afterCursor) {
        StringBuilder url = new StringBuilder(PROBLEMS_LIST_URL + "?limit=").append(limit);
        if (afterCursor != null) {
            url.append("&after_cursor=").append(afterCursor);
        }
        return url.toString();
    }

    private String buildNextUrl(int limit, String nextCursor) {
        return PROBLEMS_LIST_URL + "?limit=" + limit + "&after_cursor=" + nextCursor;
    }
}
