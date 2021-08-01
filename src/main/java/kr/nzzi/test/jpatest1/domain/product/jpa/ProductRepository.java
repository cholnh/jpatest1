package kr.nzzi.test.jpatest1.domain.product.jpa;

import kr.nzzi.test.jpatest1.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
