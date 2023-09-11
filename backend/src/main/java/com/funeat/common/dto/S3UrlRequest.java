package com.funeat.common.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;

public class S3UrlRequest {

    @NotBlank(message = "파일명을 확인해주세요")
    private final String fileName;

    @JsonCreator
    public S3UrlRequest(@JsonProperty("fileName") final String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }
}
