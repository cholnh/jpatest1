package kr.nzzi.test.jpatest1.domain.store.service;

import kr.nzzi.test.jpatest1.domain.store.dto.StoreRequest;
import kr.nzzi.test.jpatest1.domain.store.dto.StoreResponse;
import kr.nzzi.test.jpatest1.domain.store.exception.StoreNotFoundException;
import kr.nzzi.test.jpatest1.domain.store.jpa.StoreRepository;
import kr.nzzi.test.jpatest1.domain.store.model.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {

    private StoreRepository storeRepository;

    @Autowired
    public StoreServiceImpl(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Override
    @Transactional
    public StoreResponse create(StoreRequest request) {
        Store store = request.toEntity();
        return StoreResponse.of(storeRepository.save(store));
    }

    @Override
    public Page<StoreResponse> findAll(Pageable pageable) {
        Page<Store> stores = storeRepository.findAll(pageable);
        return stores.map(StoreResponse::of);
    }

    public Page<StoreResponse> findAllJoinFetch(Pageable pageable) {
        List<Store> stores = storeRepository.findAllJoinFetch(pageable);
        Page<Store> page = new PageImpl<>(stores, pageable, stores.size());
        return page.map(StoreResponse::of);
    }

    @Override
    public StoreResponse findById(Long id) {
        if (id == null)
            throw new StoreNotFoundException("Id must not be null");
        Store store = storeRepository.findById(id)
                .orElseThrow(() -> new StoreNotFoundException("Invalid Id"));
        return StoreResponse.of(store);
    }
}
