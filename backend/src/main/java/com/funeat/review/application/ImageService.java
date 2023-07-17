package com.funeat.review.application;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void upload(final MultipartFile image);
}
