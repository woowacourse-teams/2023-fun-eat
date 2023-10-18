package com.funeat.banner.presentation;

import com.funeat.banner.application.BannerService;
import com.funeat.banner.dto.BannerResponse;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BannerController implements BannerApiController {

    private final BannerService bannerService;

    public BannerController(final BannerService bannerService) {
        this.bannerService = bannerService;
    }

    @GetMapping("/api/banners")
    public ResponseEntity<List<BannerResponse>> getBanners() {
        final List<BannerResponse> responses = bannerService.getAllBanners();

        return ResponseEntity.ok(responses);
    }
}
