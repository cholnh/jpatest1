package kr.nzzi.test.jpatest1.domain.product.model;

import kr.nzzi.test.jpatest1.domain.store.model.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "product_tbl")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "name")
    private String name;

    @Embedded
    @Column(name = "cost")
    private Cost cost;

    @JoinColumn(name = "idx_store")
    @ManyToOne(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            targetEntity = Store.class
    )
    private Store store;

    public void updateStore(Store store) {
        this.store = store;
    }

    @Builder
    public Product(Long idx, String name, Cost cost, Store store) {
        this.idx = idx;
        this.name = name;
        this.cost = cost;
        this.store = store;
    }
}
