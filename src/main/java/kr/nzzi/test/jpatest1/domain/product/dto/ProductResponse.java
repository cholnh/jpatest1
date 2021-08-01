package kr.nzzi.test.jpatest1.domain.product.dto;

import kr.nzzi.test.jpatest1.configuration.CustomMapper;
import kr.nzzi.test.jpatest1.domain.product.model.Product;
import lombok.*;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
public class ProductResponse implements Serializable {

    private Long idx;

    private String name;

    private int krwValue;

    private int usdValue;

    public static ProductResponse of(Product entity) {
        Assert.notNull(entity, "product entity is null");
        return CustomMapper.getInstance()
                .map(entity, ProductResponse.class);
    }

    public static List<ProductResponse> of(List<Product> entities) {
        Assert.notNull(entities, "product entities is null");
        return entities.stream()
                .map(ProductResponse::of)
                .collect(Collectors.toList());
    }
}
