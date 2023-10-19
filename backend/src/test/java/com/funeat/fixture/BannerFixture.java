package com.funeat.fixture;

import com.funeat.banner.domain.Banner;

public class BannerFixture {

    public static Banner 배너1_생성() {
        return new Banner("배너1링크", "배너1.jpeg");
    }

    public static Banner 배너2_생성() {
        return new Banner("배너2링크", "배너2.jpeg");
    }

    public static Banner 배너3_생성() {
        return new Banner("배너3링크", "배너3.jpeg");
    }

    public static Banner 배너4_생성() {
        return new Banner("배너4링크", "배너4.jpeg");
    }

    public static Banner 배너5_생성() {
        return new Banner("배너5링크", "배너5.jpeg");
    }
}
