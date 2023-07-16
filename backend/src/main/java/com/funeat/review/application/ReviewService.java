package com.funeat.review.application;

import com.funeat.member.domain.Member;
import com.funeat.member.domain.favorite.ReviewFavorite;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.member.persistence.ReviewFavoriteRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.SortingReviewDto;
import com.funeat.review.presentation.dto.SortingReviewsPageDto;
import com.funeat.review.presentation.dto.SortingReviewsResponse;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final TagRepository tagRepository;
    private final ReviewTagRepository reviewTagRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final ImageService imageService;
    private final ReviewFavoriteRepository reviewFavoriteRepository;

    public ReviewService(final ReviewRepository reviewRepository, final TagRepository tagRepository,
                         final ReviewTagRepository reviewTagRepository, final MemberRepository memberRepository,
                         final ProductRepository productRepository, final ImageService imageService,
                         final ReviewFavoriteRepository reviewFavoriteRepository) {
        this.reviewRepository = reviewRepository;
        this.tagRepository = tagRepository;
        this.reviewTagRepository = reviewTagRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
        this.imageService = imageService;
        this.reviewFavoriteRepository = reviewFavoriteRepository;
    }

    @Transactional
    public void create(final Long productId, final MultipartFile image, final ReviewCreateRequest reviewRequest) {
        final Member findMember = memberRepository.findById(reviewRequest.getMemberId())
                .orElseThrow(IllegalArgumentException::new);
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        final Review savedReview = reviewRepository.save(
                new Review(findMember, findProduct, image.getOriginalFilename(), reviewRequest.getRating(),
                        reviewRequest.getContent(), reviewRequest.getReBuy()));

        final List<Tag> findTags = tagRepository.findTagsByIdIn(reviewRequest.getTagIds());

        final List<ReviewTag> reviewTags = findTags.stream()
                .map(findTag -> ReviewTag.createReviewTag(savedReview, findTag))
                .collect(Collectors.toList());

        reviewTagRepository.saveAll(reviewTags);
        imageService.upload(image);
    }

    @Transactional
    public void likeReview(final Long productId, final Long reviewId, final ReviewFavoriteRequest request) {
        final Member findMember = memberRepository.findById(request.getMemberId())
                .orElseThrow(IllegalArgumentException::new);
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);
        final Review findReview = reviewRepository.findById(reviewId)
                .orElseThrow(IllegalArgumentException::new);

        final ReviewFavorite reviewFavorite = ReviewFavorite.createReviewFavoriteByMemberAndReview(findMember,
                findReview, request.getFavorite());

        final ReviewFavorite findReviewFavorite = reviewFavoriteRepository.findByMemberAndReview(findMember,
                findReview).orElse(reviewFavoriteRepository.save(reviewFavorite));

        findReviewFavorite.updateChecked(request.getFavorite());
    }

    public SortingReviewsResponse sortingReviews(final Long productId,
                                                 final Pageable pageable) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        final Page<Review> reviewPage = reviewRepository.findReviewsByProduct(pageable, product);

        final SortingReviewsPageDto pageDto = SortingReviewsPageDto.toDto(reviewPage);
        final List<SortingReviewDto> reviewDtos = reviewPage.stream()
                .map(SortingReviewDto::toDto)
                .collect(Collectors.toList());

        return SortingReviewsResponse.toResponse(pageDto, reviewDtos);
    }

    public List<Review> computeRankingReviews() {
        return reviewRepository.findTop3ByOrderByFavoriteCountDesc();
    }
}
