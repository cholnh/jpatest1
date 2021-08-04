package kr.nzzi.test.jpatest1.domain.employ.model;

import kr.nzzi.test.jpatest1.domain.store.model.Store;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "employ_tbl")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Employ {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "name")
    private String name;

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
    public Employ(Long idx, String name, Store store) {
        this.idx = idx;
        this.name = name;
        this.store = store;
    }
}
