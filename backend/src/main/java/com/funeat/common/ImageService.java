package com.funeat.common;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {

    void upload(final MultipartFile image, final String newFileName);

    String getRandomImageName(MultipartFile image);
}
