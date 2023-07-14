package com.funeat.review.application;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.funeat.member.domain.Gender;
import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.domain.Review;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.review.presentation.dto.ReviewCreateRequest;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SuppressWarnings("NonAsciiCharacters")
class ReviewServiceTest {

    @InjectMocks
    private ReviewService reviewService;

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private TagRepository tagRepository;

    @Mock
    private ReviewTagRepository reviewTagRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void 리뷰를_추가할_수_있다() {
        // given
        var memberId = 1L;
        var productId = 1L;
        var request = new ReviewCreateRequest(4.5, List.of(1L, 2L), "review-content", true, memberId);
        var image = new MockMultipartFile("image", "image.jpg", "image/jpeg", new byte[]{1, 2, 3});

        var member = new Member("test", "test.image", 25, Gender.FEMALE, "01036551086");
        var product = new Product("testProduct", 1000L, "product.image", "productContent", null);
        var tags = List.of(new Tag("tag1"), new Tag("tag2"));
        var review = new Review(member, product, image.getOriginalFilename(), request.getRating(), request.getContent(),
                request.getReBuy());

        // when
        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(reviewRepository.save(any(Review.class))).thenReturn(review);
        when(tagRepository.findTagsByIdIn(anyList())).thenReturn(tags);
        when(reviewTagRepository.saveAll(anyList())).thenReturn(new ArrayList<>());

        reviewService.create(productId, image, request);

        // then
        verify(memberRepository, atLeastOnce()).findById(memberId);
        verify(productRepository, atLeastOnce()).findById(productId);
        verify(tagRepository, atLeastOnce()).findTagsByIdIn(request.getTagIds());
        verify(reviewRepository, atLeastOnce()).save(any(Review.class));
        verify(reviewTagRepository, atLeastOnce()).saveAll(anyList());
    }
}
