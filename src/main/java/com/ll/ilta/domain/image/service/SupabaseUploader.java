package com.ll.ilta.domain.image.service;

import com.ll.ilta.domain.image.client.SupabaseFeignClient;
import com.ll.ilta.domain.image.dto.SupabaseResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class SupabaseUploader {

    private final SupabaseFeignClient supabaseFeignClient;

    @Value("${supabase.bucket}")
    private String bucket;

    public SupabaseResponseDto upload(Long userId, MultipartFile file) {
        String extension = "";
        if (file.getOriginalFilename() != null && file.getOriginalFilename().contains(".")) {
            extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        }
        String uniqueFilename = UUID.randomUUID().toString() + extension;
        String path = userId + "/" + uniqueFilename;

        return supabaseFeignClient.uploadImage(bucket, path, file);
    }
}
