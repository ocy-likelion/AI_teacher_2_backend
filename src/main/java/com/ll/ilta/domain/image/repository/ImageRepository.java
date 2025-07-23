package com.ll.ilta.domain.image.repository;

import com.ll.ilta.domain.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {

    void deleteByProblemId(Long problemId);
}
