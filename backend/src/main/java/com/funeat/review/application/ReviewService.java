package com.funeat.review.application;

import static com.funeat.member.exception.MemberErrorCode.MEMBER_DUPLICATE_FAVORITE;
import static com.funeat.member.exception.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.funeat.product.exception.ProductErrorCode.PRODUCT_NOT_FOUND;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_NOT_FOUND;
import static com.funeat.review.exception.ReviewErrorCode.REVIEW_SORTING_OPTION_NOT_FOUND;

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
import com.funeat.review.exception.ReviewException.ReviewNotFoundException;
import com.funeat.review.exception.ReviewException.ReviewSortingOptionNotFoundException;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.swing.text.StyledEditorKit.BoldAction;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private static final int TOP = 0;
    private static final int ONE = 1;
    private static final Long FIRST = 0L;
    private static final String EMPTY_URL = "";

    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ReviewFavoriteRepository reviewFavoriteRepository;
    private final ImageUploader imageUploader;

    public ReviewService(final ReviewRepository reviewRepository, final TagRepository tagRepository,
                         final ReviewTagRepository reviewTagRepository, final MemberRepository memberRepository,
                         final ProductRepository productRepository,
                         final ReviewFavoriteRepository reviewFavoriteRepository, final ImageUploader imageUploader) {
        this.reviewRepository = reviewRepository;
        this.tagRepository = tagRepository;
        this.reviewTagRepository = reviewTagRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.reviewFavoriteRepository = reviewFavoriteRepository;
        this.imageUploader = imageUploader;
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
            final ReviewFavorite reviewFavorite = ReviewFavorite.create(member, review,
                    favorite);
            return reviewFavoriteRepository.save(reviewFavorite);
        } catch (final DataIntegrityViolationException e) {
            throw new MemberDuplicateFavoriteException(MEMBER_DUPLICATE_FAVORITE, member.getId());
        }
    }

    @Transactional
    public void updateProductImage(final Long reviewId) {
        final Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException(REVIEW_NOT_FOUND, reviewId));

        final Product product = review.getProduct();
        final Long productId = product.getId();
        final PageRequest pageRequest = PageRequest.of(TOP, ONE);

        final List<Review> topFavoriteReview = reviewRepository.findPopularReviewWithImage(productId, pageRequest);
        if (!topFavoriteReview.isEmpty()) {
            final String topFavoriteReviewImage = topFavoriteReview.get(TOP).getImage();
            product.updateImage(topFavoriteReviewImage);
        }
    }

    public SortingReviewsResponse sortingReviews(final Long productId, final Long memberId,
                                                       final SortingReviewRequest request) {
        final Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(MEMBER_NOT_FOUND, memberId));
        final Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final List<SortingReviewDto> sortingReviewsWithoutTags = executeSortingReviews(product, member, request);
        final List<SortingReviewDto> sortingReviews = addTagsToSortingReviews(sortingReviewsWithoutTags);
        final Boolean hasNextReview = hasMoreReview(request.getPageable(), sortingReviews.size());

        return SortingReviewsResponse.toResponse(sortingReviews, hasNextReview);
    }

    private List<SortingReviewDto> executeSortingReviews(final Product product, final Member member,
                                                         final SortingReviewRequest request) {
        final Long lastReviewId = request.getLastReviewId();
        final Pageable pageable = request.getPageable();

        final String sort = request.getSort();

        if (sort.equals("favoriteCount,desc")) {
            if (Objects.equals(lastReviewId, FIRST)) {
                return reviewRepository.findSortingReviewsByFavoriteCountDescFirstPage(product, member, pageable);
            }
            return reviewRepository.findSortingReviewsByFavoriteCountDesc(product, member, lastReviewId, pageable);
        }
        if (sort.equals("createdAt,desc")) {
            if (Objects.equals(lastReviewId, FIRST)) {
                return reviewRepository.findSortingReviewsByCreatedAtDescFirstPage(product, member, pageable);
            }
            return reviewRepository.findSortingReviewsByCreatedAtDesc(product, member, lastReviewId, pageable);
        }
        if (sort.equals("rating,asc") || sort.equals("rating,desc")) {
            if (Objects.equals(lastReviewId, FIRST)) {
                return reviewRepository.findSortingReviewsByRatingFirstPage(product, member, pageable);
            }
            if (sort.equals("rating,asc")) {
                return reviewRepository.findSortingRatingByRatingAsc(product, member, lastReviewId, pageable);
            }
            if (sort.equals("rating,desc")) {
                return reviewRepository.findSortingRatingByRatingDesc(product, member, lastReviewId, pageable);
            }
        }
        throw new ReviewSortingOptionNotFoundException(REVIEW_SORTING_OPTION_NOT_FOUND, product.getId());
    }

    private List<SortingReviewDto> addTagsToSortingReviews(final List<SortingReviewDto> sortingReviews) {
        return sortingReviews.stream()
                .map(review -> SortingReviewDto.toDto(review, tagRepository.findTagsByReviewId(review.getId())))
                .collect(Collectors.toList());
    }

    private Boolean hasMoreReview(final Pageable pageable, final int reviewSize) {
        final int pageSize = pageable.getPageSize();

        return reviewSize == pageSize + ONE;
    }

    public RankingReviewsResponse getTopReviews() {
        final List<Review> rankingReviews = reviewRepository.findTop3ByOrderByFavoriteCountDesc();

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

    public Optional<MostFavoriteReviewResponse> getMostFavoriteReview(final Long productId) {
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND, productId));

        final Optional<Review> review = reviewRepository.findTopByProductOrderByFavoriteCountDescIdDesc(findProduct);

        return MostFavoriteReviewResponse.toResponse(review);
    }
}
