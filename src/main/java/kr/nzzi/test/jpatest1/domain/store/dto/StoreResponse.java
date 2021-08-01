package kr.nzzi.test.jpatest1.domain.store.dto;

import kr.nzzi.test.jpatest1.configuration.CustomMapper;
import kr.nzzi.test.jpatest1.domain.employ.dto.EmployResponse;
import kr.nzzi.test.jpatest1.domain.product.dto.ProductResponse;
import kr.nzzi.test.jpatest1.domain.store.model.Store;
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
public class StoreResponse implements Serializable {

    private Long idx;

    private String name;

    private List<ProductResponse> products;

    private List<EmployResponse> employees;

    public static StoreResponse of(Store entity) {
        Assert.notNull(entity, "store entity is null");
        return CustomMapper.getInstance()
                .map(entity, StoreResponse.class);
    }

    public static List<StoreResponse> of(List<Store> entities) {
        Assert.notNull(entities, "store entities is null");
        return entities.stream()
                .map(StoreResponse::of)
                .collect(Collectors.toList());
    }
}
