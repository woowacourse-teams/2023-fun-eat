package com.funeat.fixture;

import static com.funeat.tag.domain.TagType.ETC;
import static com.funeat.tag.domain.TagType.QUANTITY;
import static com.funeat.tag.domain.TagType.TASTE;

import com.funeat.tag.domain.Tag;

@SuppressWarnings("NonAsciiCharacters")
public class TagFixture {

    public static Tag 태그_맛있어요_TASTE_생성() {
        return new Tag("맛있어요", TASTE);
    }

    public static Tag 태그_단짠단짠_TASTE_생성() {
        return new Tag("단짠딴짠", TASTE);
    }

    public static Tag 태그_갓성비_PRICE_생성() {
        return new Tag("갓성비", QUANTITY);
    }

    public static Tag 태그_푸짐해요_PRICE_생성() {
        return new Tag("푸짐해요", QUANTITY);
    }

    public static Tag 태그_간식_ETC_생성() {
        return new Tag("간식", ETC);
    }

    public static Tag 태그_아침식사_ETC_생성() {
        return new Tag("아침식사", ETC);
    }
}
