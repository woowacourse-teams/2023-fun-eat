package com.funeat.common;

import org.springframework.web.multipart.MultipartFile;

public interface ImageUploader {

    String upload(final MultipartFile image);

    void delete(final String fileName);
}
