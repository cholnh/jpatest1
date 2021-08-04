package kr.nzzi.test.jpatest1.domain.store.dto;

import kr.nzzi.test.jpatest1.configuration.CustomMapper;
import kr.nzzi.test.jpatest1.domain.store.model.Store;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StoreRequest {

    @NotBlank(message = "상점명을 입력해주세요.")
    private String name;

    public Store toEntity() {
        return CustomMapper.getInstance()
                .map(this, Store.class);
    }

    @Builder
    public StoreRequest(@NotBlank String name) {
        this.name = name;
    }
}
