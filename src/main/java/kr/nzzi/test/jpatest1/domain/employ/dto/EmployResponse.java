package kr.nzzi.test.jpatest1.domain.employ.dto;

import kr.nzzi.test.jpatest1.configuration.CustomMapper;
import kr.nzzi.test.jpatest1.domain.employ.model.Employ;
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
public class EmployResponse implements Serializable {

    private Long idx;

    private String name;

    public static EmployResponse of(Employ entity) {
        Assert.notNull(entity, "employ entity is null");
        return CustomMapper.getInstance()
                .map(entity, EmployResponse.class);
    }

    public static List<EmployResponse> of(List<Employ> entities) {
        Assert.notNull(entities, "employ entities is null");
        return entities.stream()
                .map(EmployResponse::of)
                .collect(Collectors.toList());
    }
}
