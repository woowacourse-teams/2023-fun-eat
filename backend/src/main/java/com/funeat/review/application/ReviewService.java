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
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional(readOnly = true)
public class ReviewService {

    @Value("${review.image.path}")
    private String imagePath;

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
        final Member findMember = memberRepository.findById(reviewRequest.getMemberId())
                .orElseThrow(IllegalArgumentException::new);
        final Product findProduct = productRepository.findById(productId)
                .orElseThrow(IllegalArgumentException::new);
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
        final Path path = Paths.get(imagePath + originalImageName);
        try {
            Files.write(path, image.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
