package com.ll.ilta.domain.image.service;

import com.ll.ilta.domain.image.client.SupabaseFeignClient;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ImageUploadService {

    private final SupabaseFeignClient supabaseFeignClient;

    @Value("${supabase.bucket}")
    private String bucket;

    public void uploadImage(MultipartFile file) {
        String filename = URLEncoder.encode(file.getOriginalFilename(), StandardCharsets.UTF_8);

        supabaseFeignClient.uploadImage(bucket, file, filename);
    }
}
