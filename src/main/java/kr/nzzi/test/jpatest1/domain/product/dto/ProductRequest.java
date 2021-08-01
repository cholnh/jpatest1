package kr.nzzi.test.jpatest1.domain.product.dto;

import kr.nzzi.test.jpatest1.configuration.CustomMapper;
import kr.nzzi.test.jpatest1.domain.product.model.Product;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductRequest {

    @NotBlank(message = "제품명을 입력해주세요.")
    private String name;

    @NotBlank(message = "가격(KRW)을 입력해주세요.")
    private int krwValue;

    @NotBlank(message = "가격(USD)을 입력해주세요.")
    private int usdValue;

    public Product toEntity() {
        return CustomMapper.getInstance()
                .map(this, Product.class);
    }
}
