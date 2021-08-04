package kr.nzzi.test.jpatest1.domain.product.service;

import kr.nzzi.test.jpatest1.domain.product.dto.ProductRequest;
import kr.nzzi.test.jpatest1.domain.product.dto.ProductResponse;
import kr.nzzi.test.jpatest1.domain.product.jpa.ProductRepository;
import kr.nzzi.test.jpatest1.domain.product.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

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
