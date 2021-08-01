package kr.nzzi.test.jpatest1.domain.product.service;

import kr.nzzi.test.jpatest1._bases.IntegrationTest;
import kr.nzzi.test.jpatest1.domain.product.dto.ProductRequest;
import kr.nzzi.test.jpatest1.domain.product.dto.ProductResponse;
import kr.nzzi.test.jpatest1.domain.product.jpa.ProductRepository;
import kr.nzzi.test.jpatest1.domain.product.model.Cost;
import kr.nzzi.test.jpatest1.domain.product.model.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest extends IntegrationTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Test
    @DisplayName("Product 정보를 정상적으로 생성한다.")
    void create_simpleProductRequest_createdAndReturnsProductResponse() {
        // given
        final String expectedName = "초코파이";
        final int expectedKrwValue = 3_000;
        final int expectedUsdValue = 3;
        ProductRequest request = ProductRequest.builder()
                .name(expectedName)
                .krwValue(expectedKrwValue)
                .usdValue(expectedUsdValue)
                .build();

        // when
        ProductResponse response = productService.create(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getIdx());
        assertNotNull(response.getName());

        assertEquals(expectedName, response.getName());
        assertEquals(expectedKrwValue, response.getKrwValue());
        assertEquals(expectedUsdValue, response.getUsdValue());
    }

    @Test
    @DisplayName("Product 목록 전체를 page 형태로 가져온다.")
    void findAll_withPageRequest_returnsPageOfProductResponse() {
        // given
        productRepository.save(Product.builder()
                .name("초코파이").cost(Cost.builder().krwValue(1000).usdValue(1).build())
                .build());
        productRepository.save(Product.builder()
                .name("가나파이").cost(Cost.builder().krwValue(2_000).usdValue(2).build())
                .build());
        productRepository.save(Product.builder()
                .name("빅파이").cost(Cost.builder().krwValue(3_000).usdValue(3).build())
                .build());
        Pageable pageable = Pageable.unpaged();

        // when
        Page<ProductResponse> responses = productService.findAll(pageable);

        // then
        assertNotNull(responses);
        assertFalse(responses.isEmpty());

        responses.forEach(productResponse -> {
            assertNotNull(productResponse.getIdx());
            assertNotNull(productResponse.getName());
            assertTrue(productResponse.getKrwValue() >= 0);
            assertTrue(productResponse.getUsdValue() >= 0);
            // System.out.println(productResponse);
        });
    }
}