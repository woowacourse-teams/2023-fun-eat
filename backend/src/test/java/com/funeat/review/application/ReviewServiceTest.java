package com.funeat.review.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;

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
import com.funeat.review.presentation.dto.SortingReviewDto;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        final var member = 멤버_추가_요청();
        final var product = 상품_추가_요청();
        final var tags = 태그_추가_요청();
        final var tagIds = tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        final var image = 리뷰_페이크_사진_요청();
        final var request = new ReviewCreateRequest(4.5, tagIds, "review", true, member.getId());

        // when
        reviewService.create(product.getId(), image, request);
        final var result = reviewRepository.findAll();

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
        final var member = 멤버_추가_요청();
        final var product = 상품_추가_요청();
        final var tags = 태그_추가_요청();
        final var tagIds = tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        final var image = 리뷰_페이크_사진_요청();
        final var reviewCreaterequest = new ReviewCreateRequest(4.5, tagIds, "review", true, member.getId());

        reviewService.create(product.getId(), image, reviewCreaterequest);
        final var savedReview = reviewRepository.findAll().get(0);

        // when
        final var favoriteRequest = new ReviewFavoriteRequest(true, member.getId());
        reviewService.likeReview(product.getId(), savedReview.getId(), favoriteRequest);
        final var reviewFavoriteResult = reviewFavoriteRepository.findAll().get(0);
        final var reviewResult = reviewRepository.findAll().get(0);

        // then
        final var expected = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, savedReview, true);
        assertThat(reviewResult.getFavoriteCount()).isEqualTo(1L);
        assertThat(reviewFavoriteResult).usingRecursiveComparison()
                .ignoringExpectedNullFields()
                .comparingOnlyFields("member", "review", "checked")
                .isEqualTo(expected);
    }

    @Test
    void 리뷰에_좋아요를_취소_할_수_있다() {
        // given
        final var member = 멤버_추가_요청();
        final var product = 상품_추가_요청();
        final var tags = 태그_추가_요청();
        final var tagIds = tags.stream()
                .map(Tag::getId)
                .collect(Collectors.toList());
        final var image = 리뷰_페이크_사진_요청();
        final var reviewCreaterequest = new ReviewCreateRequest(4.5, tagIds, "review", true, member.getId());

        reviewService.create(product.getId(), image, reviewCreaterequest);
        final var savedReview = reviewRepository.findAll().get(0);

        final var favoriteRequest = new ReviewFavoriteRequest(true, member.getId());
        reviewService.likeReview(product.getId(), savedReview.getId(), favoriteRequest);

        // when
        final var cancelFavoriteRequest = new ReviewFavoriteRequest(false, member.getId());
        reviewService.likeReview(product.getId(), savedReview.getId(), cancelFavoriteRequest);

        final var reviewFavoriteResult = reviewFavoriteRepository.findAll().get(0);
        final var reviewResult = reviewRepository.findAll().get(0);

        // then
        final var expected = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, savedReview, false);
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

    @Nested
    class sortingReviews_페이징_테스트 {

        @Test
        void 좋아요_기준으로_내림차순_정렬이_되는지_확인한다() {
            // given
            final var member1 = new Member("test1", "test1.png", 20, Gender.MALE, "010-1234-1234");
            final var member2 = new Member("test2", "test2.png", 41, Gender.FEMALE, "010-1357-2468");
            final var member3 = new Member("test3", "test3.png", 9, Gender.MALE, "010-9876-4321");
            final var members = List.of(member1, member2, member3);
            복수_유저_추가(members);

            final var product = new Product("김밥", 1000L, "kimbap.png", "우영우가 먹은 그 김밥", null);
            상품_추가(product);

            final var review1 = new Review(member1, product, "review1.jpg", 3.0, "이 김밥은 재밌습니다", true, 351L);
            final var review2 = new Review(member2, product, "review2.jpg", 4.5, "역삼역", true, 24L);
            final var review3 = new Review(member3, product, "review3.jpg", 3.5, "ㅇㅇ", false, 130L);
            final var reviews = List.of(review1, review2, review3);
            복수_리뷰_추가(reviews);

            final var pageable = PageRequest.of(0, 2, Sort.by("favoriteCount").descending());
            final var expected = Stream.of(review1, review3)
                    .map(SortingReviewDto::toDto)
                    .collect(Collectors.toList());

            // when
            final var actual = reviewService.sortingReviews(product.getId(), pageable)
                    .getReviews();

            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }

        @Test
        void 평점_기준으로_오름차순_정렬이_되는지_확인한다() {
            // given
            final var member1 = new Member("test1", "test1.png", 20, Gender.MALE, "010-1234-1234");
            final var member2 = new Member("test2", "test2.png", 41, Gender.FEMALE, "010-1357-2468");
            final var member3 = new Member("test3", "test3.png", 9, Gender.MALE, "010-9876-4321");
            final var members = List.of(member1, member2, member3);
            복수_유저_추가(members);

            final var product = new Product("김밥", 1000L, "kimbap.png", "우영우가 먹은 그 김밥", null);
            상품_추가(product);

            final var review1 = new Review(member1, product, "review1.jpg", 3.0, "이 김밥은 재밌습니다", true, 351L);
            final var review2 = new Review(member2, product, "review2.jpg", 4.5, "역삼역", true, 24L);
            final var review3 = new Review(member3, product, "review3.jpg", 3.5, "ㅇㅇ", false, 130L);
            final var reviews = List.of(review1, review2, review3);
            복수_리뷰_추가(reviews);

            final var pageable = PageRequest.of(0, 2, Sort.by("rating").ascending());
            final var expected = Stream.of(review1, review3)
                    .map(SortingReviewDto::toDto)
                    .collect(Collectors.toList());

            // when
            final var actual = reviewService.sortingReviews(product.getId(), pageable)
                    .getReviews();

            // then
            assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
        }
    }

    private void 복수_유저_추가(final List<Member> members) {
        memberRepository.saveAll(members);
    }

    private void 상품_추가(final Product product) {
        productRepository.save(product);
    }

    private void 복수_리뷰_추가(final List<Review> reviews) {
        reviewRepository.saveAll(reviews);
    }
}
