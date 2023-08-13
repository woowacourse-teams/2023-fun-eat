package com.funeat.fixture;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("NonAsciiCharacters")
public class ImageFixture {

    public static MultipartFile 이미지_생성() {
        return new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[]{1, 2, 3});
    }
}
