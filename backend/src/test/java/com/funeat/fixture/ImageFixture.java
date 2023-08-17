package com.funeat.fixture;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

@SuppressWarnings("NonAsciiCharacters")
public class ImageFixture {

    public static MultipartFile 이미지_생성() {
        return new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[]{1, 2, 3});
    }

    public static List<MultipartFile> 여러_이미지_생성(final int count) {
        return IntStream.range(0, count)
                .mapToObj(it -> 이미지_생성())
                .collect(Collectors.toList());
    }
}
