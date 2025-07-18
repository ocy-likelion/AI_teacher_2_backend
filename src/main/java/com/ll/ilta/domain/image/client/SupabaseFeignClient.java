package com.ll.ilta.domain.image.client;

import com.ll.ilta.global.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(
    name = "SupabaseFeignClient",
    url = "${supabase.url}",
    configuration = FeignConfig.class
)
public interface SupabaseFeignClient {

    @PostMapping(
        value = "/storage/v1/object/{bucket}/upload",
        consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    void uploadImage(
        @PathVariable("bucket") String bucket,
        @RequestPart("file") MultipartFile file,
        @RequestParam("name") String filename
    );
}
