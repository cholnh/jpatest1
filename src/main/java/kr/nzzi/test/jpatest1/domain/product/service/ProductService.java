package kr.nzzi.test.jpatest1.domain.product.service;

import kr.nzzi.test.jpatest1.domain.product.dto.ProductRequest;
import kr.nzzi.test.jpatest1.domain.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    ProductResponse create(ProductRequest request);
    Page<ProductResponse> findAll(Pageable pageable);
}
