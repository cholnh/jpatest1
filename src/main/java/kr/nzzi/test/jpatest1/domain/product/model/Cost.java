package kr.nzzi.test.jpatest1.domain.product.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.PositiveOrZero;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Cost {

    @PositiveOrZero
    @Column(name = "krw_value", columnDefinition = "INT default 0")
    private int krwValue;

    @PositiveOrZero
    @Column(name = "usd_value", columnDefinition = "INT default 0")
    private int usdValue;

    @Builder
    public Cost(@PositiveOrZero int krwValue, @PositiveOrZero int usdValue) {
        this.krwValue = krwValue;
        this.usdValue = usdValue;
    }
}
