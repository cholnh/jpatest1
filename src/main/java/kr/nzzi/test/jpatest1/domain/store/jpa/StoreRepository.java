package kr.nzzi.test.jpatest1.domain.store.jpa;

import kr.nzzi.test.jpatest1.domain.store.model.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

    @EntityGraph(attributePaths = {"products", "employees"})
    Page<Store> findAll(Pageable pageable);

    @Query("SELECT DISTINCT s FROM Store s JOIN FETCH s.products JOIN FETCH s.employees")
    List<Store> findAllJoinFetch(Pageable pageable);
}
