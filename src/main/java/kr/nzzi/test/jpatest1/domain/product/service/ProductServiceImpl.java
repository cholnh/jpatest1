package kr.nzzi.test.jpatest1.domain.product.service;

import kr.nzzi.test.jpatest1.domain.product.dto.ProductRequest;
import kr.nzzi.test.jpatest1.domain.product.dto.ProductResponse;
import kr.nzzi.test.jpatest1.domain.product.jpa.ProductRepository;
import kr.nzzi.test.jpatest1.domain.product.model.Product;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Override
    @Transactional
    public ProductResponse create(ProductRequest request) {
        Product product = request.toEntity();
        return ProductResponse.of(productRepository.save(product));
    }

    @Override
    public Page<ProductResponse> findAll(Pageable pageable) {
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductResponse::of);
    }


}
