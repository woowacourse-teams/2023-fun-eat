package com.funeat.common;

import com.funeat.member.domain.Member;
import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.domain.Category;
import com.funeat.product.domain.Product;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.application.ReviewService;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.tag.domain.Tag;
import com.funeat.tag.persistence.TagRepository;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.ApplicationEvents;
import org.springframework.test.context.event.RecordApplicationEvents;

@SpringBootTest
@RecordApplicationEvents
@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
public class EventTest {

    @Autowired
    protected ApplicationEvents events;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected CategoryRepository categoryRepository;

    @Autowired
    protected TagRepository tagRepository;

    @Autowired
    protected MemberRepository memberRepository;

    @Autowired
    protected ReviewRepository reviewRepository;

    @Autowired
    protected ReviewService reviewService;

    protected Long 단일_상품_저장(final Product product) {
        return productRepository.save(product).getId();
    }

    protected Long 단일_카테고리_저장(final Category category) {
        return categoryRepository.save(category).getId();
    }

    protected void 복수_태그_저장(final Tag... tagsToSave) {
        final var tags = List.of(tagsToSave);
        tagRepository.saveAll(tags);
    }

    protected Long 단일_멤버_저장(final Member member) {
        return memberRepository.save(member).getId();
    }
}
