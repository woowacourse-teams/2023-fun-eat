package com.funeat.tag.presentation;

import com.funeat.tag.application.TagService;
import com.funeat.tag.dto.TagsResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TagApiController implements TagController {

    private final TagService tagService;

    public TagApiController(final TagService tagService) {
        this.tagService = tagService;
    }

    @GetMapping("/api/tags")
    public ResponseEntity<List<TagsResponse>> getAllTags() {
        final List<TagsResponse> responses = tagService.getAllTags();
        return ResponseEntity.ok(responses);
    }
}
