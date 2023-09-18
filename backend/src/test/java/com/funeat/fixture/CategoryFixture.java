package com.funeat.fixture;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;

@SuppressWarnings("NonAsciiCharacters")
public class CategoryFixture {

    public static final String 음식 = "food";

    public static final Long 존재하지_않는_카테고리 = 99999L;

    public static Category 카테고리_간편식사_생성() {
        return new Category("간편식사", CategoryType.FOOD, "siksa.jpeg");
    }

    public static Category 카테고리_즉석조리_생성() {
        return new Category("즉석조리", CategoryType.FOOD, "direct.jpeg");
    }

    public static Category 카테고리_과자류_생성() {
        return new Category("과자류", CategoryType.FOOD, "snack.jpeg");
    }

    public static Category 카테고리_아이스크림_생성() {
        return new Category("아이스크림", CategoryType.FOOD, "ice.jpeg");
    }

    public static Category 카테고리_식품_생성() {
        return new Category("식품", CategoryType.FOOD, "food.jpeg");
    }

    public static Category 카테고리_음료_생성() {
        return new Category("음료", CategoryType.FOOD, "drink.jpeg");
    }

    public static Category 카테고리_CU_생성() {
        return new Category("CU", CategoryType.STORE, "cu.jpeg");
    }

    public static Category 카테고리_GS25_생성() {
        return new Category("GS25", CategoryType.STORE, "gs25.jpeg");
    }

    public static Category 카테고리_EMART24_생성() {
        return new Category("EMART24", CategoryType.STORE, "emart.jpeg");
    }

    public static Category 카테고리_세븐일레븐_생성() {
        return new Category("세븐일레븐", CategoryType.STORE, "seven.jpeg");
    }
}
