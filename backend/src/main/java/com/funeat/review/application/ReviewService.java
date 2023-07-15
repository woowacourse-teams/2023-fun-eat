package com.funeat.review.application;

import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.domain.ReviewTag;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.review.presentation.dto.SortingReviewDto;
import com.funeat.review.presentation.dto.SortingReviewsPageDto;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public ReviewService(final ReviewRepository reviewRepository, final TagRepository tagRepository,
                         final ReviewTagRepository reviewTagRepository, final MemberRepository memberRepository,
                         final ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.tagRepository = tagRepository;
        this.reviewTagRepository = reviewTagRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    @Transactional
    public void create(final Long productId, final MultipartFile image,
                       final ReviewCreateRequest reviewRequest) {
        final Member findMember = memberRepository.findById(reviewRequest.getMemberId()).get();
        final Product findProduct = productRepository.findById(productId).get();
        writeImage(image);

        final Review savedReview = reviewRepository.save(
                new Review(findMember, findProduct, image.getOriginalFilename(), reviewRequest.getRating(),
                        reviewRequest.getContent(), reviewRequest.getReBuy()));

        final List<Tag> findTags = tagRepository.findTagsByIdIn(reviewRequest.getTagIds());

        final List<ReviewTag> reviewTags = findTags.stream()
                .map(findTag -> new ReviewTag(savedReview, findTag))
                .collect(Collectors.toList());

        reviewTagRepository.saveAll(reviewTags);
    }

    private void writeImage(final MultipartFile image) {
        final String originalImageName = image.getOriginalFilename();
        final Path path = Paths.get("/Users/wugawuga/fun-eat/review/images/" + originalImageName);
        try {
            Files.write(path, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<SortingReviewDto> sortingReviews(final Long productId,
                                                 final Pageable pageable,
                                                 final String sort) {
        if (sort.equals("favorite")) {
            return sortingReviewsByFavoriteCount(productId, pageable);
        }
        return List.of();
    }

    private List<SortingReviewDto> sortingReviewsByFavoriteCount(final Long productId,
                                                                 final Pageable pageable) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);

        final List<Review> sortingReviews = reviewRepository.findReviewsByProductOrderByFavoriteCountDesc(pageable, product);

        return sortingReviews.stream()
                .map(SortingReviewDto::toDto)
                .collect(Collectors.toList());
    }

    public SortingReviewsPageDto computePageInfo(final Pageable pageable,
                                                 final Long productId) {
        final Product product = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);
        final Page<Review> reviewsPage = reviewRepository.findReviewsByProduct(pageable, product);

        return SortingReviewsPageDto.toDto(reviewsPage);
    }
}
