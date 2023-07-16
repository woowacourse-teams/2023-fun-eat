package com.funeat.review.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.funeat.member.domain.Gender;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.ReviewFavoriteRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.ReviewFavoriteRequest;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ReviewFavoriteRepository reviewFavoriteRepository;

    @Autowired
    private ReviewTagRepository reviewTagRepository;

    @BeforeEach
    void init() {
        reviewFavoriteRepository.deleteAll();
        reviewTagRepository.deleteAll();
        reviewRepository.deleteAll();
        memberRepository.deleteAll();
        productRepository.deleteAll();
        tagRepository.deleteAll();
    }

    @Test
    void 리뷰를_추가할_수_있다() {
        // given
        var member = 멤버_추가_요청();
        var product = 상품_추가_요청();
        var tags = 태그_추가_요청();
        var tagIds = tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        var image = 리뷰_페이크_사진_요청();
        var request = new ReviewCreateRequest(4.5, tagIds, "review", true, member.getId());

        // when
        reviewService.create(product.getId(), image, request);
        var result = reviewRepository.findAll();

        // then
        assertThat(result.get(0)).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .comparingOnlyFields("member", "product", "image", "rating", "content", "reBuy")
                .isEqualTo(
                        new Review(member, product, image.getOriginalFilename(), 4.5, "review", true)
                );
    }

    @Test
    void 리뷰에_좋아요를_할_수_있다() {
        // given
        var member = 멤버_추가_요청();
        var product = 상품_추가_요청();
        var tags = 태그_추가_요청();
        var tagIds = tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        var image = 리뷰_페이크_사진_요청();
        var reviewCreaterequest = new ReviewCreateRequest(4.5, tagIds, "review", true, member.getId());

        reviewService.create(product.getId(), image, reviewCreaterequest);
        var savedReview = reviewRepository.findAll().get(0);

        // when
        var favoriteRequest = new ReviewFavoriteRequest(true, member.getId());
        reviewService.likeReview(product.getId(), savedReview.getId(), favoriteRequest);
        var reviewFavoriteResult = reviewFavoriteRepository.findAll().get(0);
        var reviewResult = reviewRepository.findAll().get(0);

        // then
        var expected = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, savedReview, true);
        assertThat(reviewResult.getFavoriteCount()).isEqualTo(1L);
        assertThat(reviewFavoriteResult).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .comparingOnlyFields("member", "review", "checked")
                .isEqualTo(expected);
    }

    @Test
    void 리뷰에_좋아요를_취소_할_수_있다() {
        // given
        var member = 멤버_추가_요청();
        var product = 상품_추가_요청();
        var tags = 태그_추가_요청();
        var tagIds = tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        var image = 리뷰_페이크_사진_요청();
        var reviewCreaterequest = new ReviewCreateRequest(4.5, tagIds, "review", true, member.getId());

        reviewService.create(product.getId(), image, reviewCreaterequest);
        var savedReview = reviewRepository.findAll().get(0);

        var favoriteRequest = new ReviewFavoriteRequest(true, member.getId());
        reviewService.likeReview(product.getId(), savedReview.getId(), favoriteRequest);

        // when
        var cancelFavoriteRequest = new ReviewFavoriteRequest(false, member.getId());
        reviewService.likeReview(product.getId(), savedReview.getId(), cancelFavoriteRequest);

        var reviewFavoriteResult = reviewFavoriteRepository.findAll().get(0);
        var reviewResult = reviewRepository.findAll().get(0);

        // then
        var expected = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, savedReview, false);
        assertThat(reviewResult.getFavoriteCount()).isEqualTo(0L);
        assertThat(reviewFavoriteResult).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .comparingOnlyFields("member", "review", "checked")
                .isEqualTo(expected);
    }

    private MockMultipartFile 리뷰_페이크_사진_요청() {
        return new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[]{1, 2, 3});
    }

    private List<Tag> 태그_추가_요청() {
        final Tag testTag1 = tagRepository.save(new Tag("testTag1"));
        final Tag testTag2 = tagRepository.save(new Tag("testTag2"));

        return List.of(testTag1, testTag2);
    }

    private Product 상품_추가_요청() {
        return productRepository.save(new Product("testName", 1000L, "test.png", "test", null));
    }

    private Member 멤버_추가_요청() {
        return memberRepository.save(
                new Member("test", "image.png", 27, Gender.FEMALE, "01036551086"));
    }
}
