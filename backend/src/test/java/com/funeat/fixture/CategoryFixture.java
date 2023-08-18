package com.funeat.fixture;

import com.funeat.product.domain.Category;
import com.funeat.product.domain.CategoryType;

@SuppressWarnings("NonAsciiCharacters")
public class CategoryFixture {

    public static Category 카테고리_간편식사_생성() {
        return new Category("간편식사", CategoryType.FOOD);
    }

    public static Category 카테고리_즉석조리_생성() {
        return new Category("즉석조리", CategoryType.FOOD);
    }

    public static Category 카테고리_과자류_생성() {
        return new Category("과자류", CategoryType.FOOD);
    }

    public static Category 카테고리_아이스크림_생성() {
        return new Category("아이스크림", CategoryType.FOOD);
    }

    public static Category 카테고리_식품_생성() {
        return new Category("식품", CategoryType.FOOD);
    }

    public static Category 카테고리_음료_생성() {
        return new Category("음료", CategoryType.FOOD);
    }

    public static Category 카테고리_CU_생성() {
        return new Category("CU", CategoryType.STORE);
    }

    public static Category 카테고리_GS25_생성() {
        return new Category("GS25", CategoryType.STORE);
    }

    public static Category 카테고리_EMART24_생성() {
        return new Category("EMART24", CategoryType.STORE);
    }

    public static Category 카테고리_세븐일레븐_생성() {
        return new Category("세븐일레븐", CategoryType.STORE);
    }
}
