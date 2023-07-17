package com.funeat.acceptance.common;

import com.funeat.common.DataClearExtension;
import com.funeat.product.persistence.CategoryRepository;
import com.funeat.product.persistence.ProductRepository;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;

@ExtendWith(DataClearExtension.class)
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

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }
}
