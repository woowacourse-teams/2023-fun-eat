package com.funeat.acceptance.common;

import com.funeat.member.persistence.MemberRepository;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import com.funeat.review.persistence.ReviewRepository;
import com.funeat.review.persistence.ReviewTagRepository;
import com.funeat.tag.persistence.TagRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest {

    @LocalServerPort
    private int port;

    @Autowired
    public ProductRepository productRepository;

    @Autowired
    public CategoryRepository categoryRepository;

    @Autowired
    public MemberRepository memberRepository;

    @Autowired
    public ReviewRepository reviewRepository;

    @Autowired
    public TagRepository tagRepository;

    @Autowired
    public ReviewTagRepository reviewTagRepository;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}
