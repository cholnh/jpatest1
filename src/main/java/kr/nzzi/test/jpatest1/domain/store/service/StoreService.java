package kr.nzzi.test.jpatest1.domain.store.service;

import kr.nzzi.test.jpatest1.domain.store.dto.StoreRequest;
import kr.nzzi.test.jpatest1.domain.store.dto.StoreResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StoreService {
    StoreResponse create(StoreRequest request);

    Page<StoreResponse> findAll(Pageable pageable);

    StoreResponse findById(Long id);
}
