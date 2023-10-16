package com.funeat.review.application;

import static com.funeat.member.exception.MemberErrorCode.MEMBER_DUPLICATE_FAVORITE;
import static com.funeat.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static com.funeat.review.exception.ReviewErrorCode.NOT_AUTHOR_OF_REVIEW;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;

import com.funeat.common.ImageUploader;
import com.funeat.common.dto.PageDto;
import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.member.dto.MemberReviewDto;
import com.funeat.member.dto.MemberReviewsResponse;
import com.funeat.member.exception.MemberException.MemberDuplicateFavoriteException;
import com.funeat.member.exception.MemberException.MemberNotFoundException;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.ReviewFavoriteRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.exception.ProductException.ProductNotFoundException;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.review.dto.MostFavoriteReviewResponse;
import com.funeat.review.dto.RankingReviewDto;
import com.funeat.review.dto.RankingReviewsResponse;
import com.funeat.review.dto.ReviewCreateRequest;
import com.funeat.review.dto.ReviewFavoriteRequest;
import com.funeat.review.dto.SortingReviewDto;
import com.funeat.review.dto.SortingReviewRequest;
import com.funeat.review.dto.SortingReviewsResponse;
import com.funeat.review.exception.ReviewException.NotAuthorOfReviewException;
import com.funeat.review.exception.ReviewException.ReviewNotFoundException;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private static final int START = 0;
    private static final int ONE = 1;
    private static final String EMPTY_URL = "";
    private static final int REVIEW_PAGE_SIZE = 10;

    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewFavoriteRepository reviewFavoriteRepository;
    private final ImageUploader imageUploader;
    private final SortingReviewService sortingReviewService;
    private final ApplicationEventPublisher eventPublisher;

    public ReviewService(final ReviewRepository reviewRepository, final TagRepository tagRepository,
                         final ReviewTagRepository reviewTagRepository, final MemberRepository memberRepository,
                         final ProductRepository productRepository,
                         final ReviewFavoriteRepository reviewFavoriteRepository,
                         final ImageUploader imageUploader, final SortingReviewService sortingReviewService,
                         final ApplicationEventPublisher eventPublisher) {
        this.reviewRepository = reviewRepository;
        this.tagRepository = tagRepository;
        this.reviewTagRepository = reviewTagRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.reviewFavoriteRepository = reviewFavoriteRepository;
        this.imageUploader = imageUploader;
        this.sortingReviewService = sortingReviewService;
        this.eventPublisher = eventPublisher;
    }

    @Transactional
    public void create(final Long productId, final Long memberId, final MultipartFile image,
                       final ReviewCreateRequest reviewRequest) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final String imageUrl = Optional.ofNullable(image)
                .map(imageUploader::upload)
                .orElse(EMPTY_URL);
        final Review savedReview = reviewRepository.save(
                new Review(findMember, findProduct, imageUrl, reviewRequest.getRating(), reviewRequest.getContent(),
                        reviewRequest.getRebuy()));

        final List<Tag> findTags = tagRepository.findTagsByIdIn(reviewRequest.getTagIds());

        final List<ReviewTag> reviewTags = findTags.stream()
                .map(findTag -> ReviewTag.createReviewTag(savedReview, findTag))
                .collect(Collectors.toList());

        final Long countByProduct = reviewRepository.countByProduct(findProduct);

        findProduct.updateAverageRating(savedReview.getRating(), countByProduct);
        findProduct.addReviewCount();
        reviewTagRepository.saveAll(reviewTags);
    }

    @Transactional
    public void likeReview(final Long reviewId, final Long memberId, final ReviewFavoriteRequest request) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Review findReview = reviewRepository.findByIdForUpdate(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND, reviewId));

        final ReviewFavorite savedReviewFavorite = reviewFavoriteRepository.findByMemberAndReview(findMember,
                findReview).orElseGet(() -> saveReviewFavorite(findMember, findReview, request.getFavorite()));

        savedReviewFavorite.updateChecked(request.getFavorite());
    }

    private ReviewFavorite saveReviewFavorite(final Member member, final Review review, final Boolean favorite) {
        try {
            final ReviewFavorite reviewFavorite = ReviewFavorite.create(member, review, favorite);
            return reviewFavoriteRepository.save(reviewFavorite);
        } catch (final DataIntegrityViolationException e) {
            throw new MemberDuplicateFavoriteException(MEMBER_DUPLICATE_FAVORITE, member.getId());
        }
    }

    @Transactional
    public void updateProductImage(final Long productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final PageRequest pageRequest = PageRequest.of(TOP, ONE);

        final List<Review> topFavoriteReview = reviewRepository.findPopularReviewWithImage(productId, pageRequest);
        if (!topFavoriteReview.isEmpty()) {
            final String topFavoriteReviewImage = topFavoriteReview.get(START).getImage();
            product.updateImage(topFavoriteReviewImage);
        }
    }

    public SortingReviewsResponse sortingReviews(final Long productId, final Long memberId,
                                                 final SortingReviewRequest request) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final List<SortingReviewDto> sortingReviews = sortingReviewService.getSortingReviews(findMember, findProduct, request);
        final int resultSize = getResultSize(sortingReviews);

        final List<SortingReviewDto> resizeSortingReviews = sortingReviews.subList(START, resultSize);
        final Boolean hasNext = hasNextPage(sortingReviews);

        return SortingReviewsResponse.toResponse(resizeSortingReviews, hasNext);
    }

    private int getResultSize(final List<SortingReviewDto> sortingReviews) {
        if (sortingReviews.size() <= REVIEW_PAGE_SIZE) {
            return sortingReviews.size();
        }
        return REVIEW_PAGE_SIZE;
    }

    private Boolean hasNextPage(final List<SortingReviewDto> sortingReviews) {
        return sortingReviews.size() > REVIEW_PAGE_SIZE;
    }

    public RankingReviewsResponse getTopReviews() {
        final List<Review> rankingReviews = reviewRepository.findTop3ByOrderByFavoriteCountDescIdDesc();

        final List<RankingReviewDto> dtos = rankingReviews.stream()
                .map(RankingReviewDto::toDto)
                .collect(Collectors.toList());

        return RankingReviewsResponse.toResponse(dtos);
    }

    public MemberReviewsResponse findReviewByMember(final Long memberId, final Pageable pageable) {
        final Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));

        final Page<Review> sortedReviewPages = reviewRepository.findReviewsByMember(findMember, pageable);
        final PageDto pageDto = PageDto.toDto(sortedReviewPages);

        final List<MemberReviewDto> dtos = sortedReviewPages.stream()
                .map(MemberReviewDto::toDto)
                .collect(Collectors.toList());

        return MemberReviewsResponse.toResponse(pageDto, dtos);
    }

    @Transactional
    public void deleteReview(final Long reviewId, final Long memberId) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND, reviewId));
        final Product product = review.getProduct();
        final String image = review.getImage();

        if (review.checkAuthor(member)) {
            eventPublisher.publishEvent(new ReviewDeleteEvent(image));
            deleteThingsRelatedToReview(review);
            updateProductImage(product.getId());
            return;
        }
        throw new NotAuthorOfReviewException(NOT_AUTHOR_OF_REVIEW, memberId);
    }

    private void deleteThingsRelatedToReview(final Review review) {
        deleteReviewTags(review);
        deleteReviewFavorites(review);
        reviewRepository.delete(review);
    }

    private void deleteReviewTags(final Review review) {
        final List<ReviewTag> reviewTags = reviewTagRepository.findByReview(review);
        final List<Long> ids = reviewTags.stream()
                .map(ReviewTag::getId)
                .collect(Collectors.toList());
        reviewTagRepository.deleteAllByIdInBatch(ids);
    }

    private void deleteReviewFavorites(final Review review) {
        final List<ReviewFavorite> reviewFavorites = reviewFavoriteRepository.findByReview(review);
        final List<Long> ids = reviewFavorites.stream()
                .map(ReviewFavorite::getId)
                .collect(Collectors.toList());
        reviewFavoriteRepository.deleteAllByIdInBatch(ids);
    }

    public Optional<MostFavoriteReviewResponse> getMostFavoriteReview(final Long productId) {
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final Optional<Review> review = reviewRepository.findTopByProductOrderByFavoriteCountDescIdDesc(findProduct);

        return MostFavoriteReviewResponse.toResponse(review);
    }
}
