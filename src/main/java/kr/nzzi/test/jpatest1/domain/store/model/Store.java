package kr.nzzi.test.jpatest1.domain.store.model;

import kr.nzzi.test.jpatest1.domain.employ.model.Employ;
import kr.nzzi.test.jpatest1.domain.product.model.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;

@Entity
@Table(name = "store_tbl")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Store {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(name = "name")
    private String name;

    @OrderBy("idx ASC")
    @JoinColumn(name = "idx_store", nullable = true)
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            targetEntity = Product.class
    )
    private Set<Product> products;

    @OrderBy("idx ASC")
    @JoinColumn(name = "idx_store", nullable = true)
    @OneToMany(
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            targetEntity = Employ.class
    )
    private Set<Employ> employees;
    @Builder
    public Store(Long idx, String name) {
        this.idx = idx;
        this.name = name;
        this.products = new LinkedHashSet<>();
        this.employees = new LinkedHashSet<>();
    }

    public void addProduct(Product product) {
        this.products.add(product);
        product.updateStore(this);
    }

    public void addProduct(List<Product> products) {
        products.forEach(this::addProduct);
    }

    public void addProduct(Product...products) {
        addProduct(asList(products));
    }

    public void addEmploy(Employ employ) {
        this.employees.add(employ);
        employ.updateStore(this);
    }

    public void addEmploy(List<Employ> employees) {
        employees.forEach(this::addEmploy);
    }

    public void addEmploy(Employ...employees) {
        addEmploy(asList(employees));
    }
}
