package com.funeat.review.application;

import static com.funeat.member.exception.MemberErrorCode.MEMBER_NOF_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOF_FOUND;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOF_FOUND;

import com.funeat.common.ImageService;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.ReviewFavoriteRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.review.exception.ReviewException.ReviewNotFoundException;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.review.presentation.dto.RankingReviewDto;
import com.funeat.review.presentation.dto.RankingReviewsResponse;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.ReviewFavoriteRequest;
import com.funeat.review.presentation.dto.SortingReviewDto;
import com.funeat.review.presentation.dto.SortingReviewsPageDto;
import com.funeat.review.presentation.dto.SortingReviewsResponse;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewFavoriteRepository reviewFavoriteRepository;
    private final ImageService imageService;

    public ReviewService(final ReviewRepository reviewRepository, final TagRepository tagRepository,
                         final ReviewTagRepository reviewTagRepository, final MemberRepository memberRepository,
                         final ProductRepository productRepository,
                         final ReviewFavoriteRepository reviewFavoriteRepository, final ImageService imageService) {
        this.reviewRepository = reviewRepository;
        this.tagRepository = tagRepository;
        this.reviewTagRepository = reviewTagRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.reviewFavoriteRepository = reviewFavoriteRepository;
        this.imageService = imageService;
    }

    @Transactional
    public void create(final Long productId, final Long memberId, final MultipartFile image,
                       final ReviewCreateRequest reviewRequest) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOF_FOUND, memberId));
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOF_FOUND, productId));

        final Review savedReview;
        if (Objects.isNull(image)) {
            savedReview = reviewRepository.save(
                    new Review(findMember, findProduct, reviewRequest.getRating(), reviewRequest.getContent(),
                            reviewRequest.getRebuy()));
        } else {
            savedReview = reviewRepository.save(
                    new Review(findMember, findProduct, image.getOriginalFilename(), reviewRequest.getRating(),
                            reviewRequest.getContent(), reviewRequest.getRebuy()));
            imageService.upload(image);
        }

        final List<Tag> findTags = tagRepository.findTagsByIdIn(reviewRequest.getTagIds());

        final List<ReviewTag> reviewTags = findTags.stream()
                .map(findTag -> ReviewTag.createReviewTag(savedReview, findTag))
                .collect(Collectors.toList());

        final Long countByProduct = reviewRepository.countByProduct(findProduct);

        findProduct.updateAverageRating(savedReview.getRating(), countByProduct);
        reviewTagRepository.saveAll(reviewTags);
    }

    @Transactional
    public void likeReview(final Long reviewId, final Long memberId, final ReviewFavoriteRequest request) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOF_FOUND, memberId));
        final Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOF_FOUND, reviewId));

        final ReviewFavorite savedReviewFavorite = reviewFavoriteRepository.findByMemberAndReview(findMember,
                findReview).orElseGet(() -> saveReviewFavorite(findMember, findReview, request.getFavorite()));

        savedReviewFavorite.updateChecked(request.getFavorite());
    }

    private ReviewFavorite saveReviewFavorite(final Member member, final Review review, final Boolean favorite) {
        final ReviewFavorite reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(member, review,
                favorite);

        return reviewFavoriteRepository.save(reviewFavorite);
    }

    public SortingReviewsResponse sortingReviews(final Long productId, final Pageable pageable, final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOF_FOUND, memberId));

        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOF_FOUND, productId));

        final Page<Review> reviewPage = reviewRepository.findReviewsByProduct(pageable, product);

        final SortingReviewsPageDto pageDto = SortingReviewsPageDto.toDto(reviewPage);
        final List<SortingReviewDto> reviewDtos = reviewPage.stream()
                .map(review -> SortingReviewDto.toDto(review, member))
                .collect(Collectors.toList());

        return SortingReviewsResponse.toResponse(pageDto, reviewDtos);
    }

    public RankingReviewsResponse getTopReviews() {
        final List<Review> rankingReviews = reviewRepository.findTop3ByOrderByFavoriteCountDesc();

        final List<RankingReviewDto> dtos = rankingReviews.stream()
                .map(RankingReviewDto::toDto)
                .collect(Collectors.toList());

        return RankingReviewsResponse.toResponse(dtos);
    }
}
