package com.funeat.banner.application;

import com.funeat.banner.domain.Banner;
import com.funeat.banner.dto.BannerResponse;
import com.funeat.banner.persistence.BannerRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class BannerService {

    private final BannerRepository bannerRepository;

    public BannerService(final BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    public List<BannerResponse> getAllBanners() {
        final List<Banner> findBanners = bannerRepository.findAllByOrderByIdDesc();

        return findBanners.stream()
                .map(BannerResponse::toResponse)
                .collect(Collectors.toList());
    }
}
