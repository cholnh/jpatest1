package kr.nzzi.test.jpatest1.domain.store.service;

import kr.nzzi.test.jpatest1._bases.IntegrationTest;
import kr.nzzi.test.jpatest1.domain.employ.model.Employ;
import kr.nzzi.test.jpatest1.domain.product.model.Cost;
import kr.nzzi.test.jpatest1.domain.product.model.Product;
import kr.nzzi.test.jpatest1.domain.store.dto.StoreRequest;
import kr.nzzi.test.jpatest1.domain.store.dto.StoreResponse;
import kr.nzzi.test.jpatest1.domain.store.exception.StoreNotFoundException;
import kr.nzzi.test.jpatest1.domain.store.jpa.StoreRepository;
import kr.nzzi.test.jpatest1.domain.store.model.Store;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class StoreServiceImplTest extends IntegrationTest {

    @Autowired
    EntityManager entityManager;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreServiceImpl storeService;

    @Test
    @DisplayName("Store 정보를 정상적으로 생성한다.")
    void create_simpleStoreRequest_createdAndReturnsStoreResponse() {
        // given
        final String expectedName = "파이전문점";
        StoreRequest request = StoreRequest.builder()
                .name(expectedName)
                .build();

        // when
        StoreResponse response = storeService.create(request);

        // then
        assertNotNull(response);
        assertNotNull(response.getIdx());
        assertNotNull(response.getName());
        assertEquals(expectedName, response.getName());
    }

    @Test
    @DisplayName("Store 목록 전체를 page 형태로 가져온다.")
    void findAll_withStoreRequest_returnsPageOfStoreResponse() {
        // given
        storeRepository.save(Store.builder().name("파이전문점").build());
        storeRepository.save(Store.builder().name("치킨나라 피자공주").build());
        storeRepository.save(Store.builder().name("할매순대국").build());
        Pageable pageable = Pageable.unpaged();

        // when
        Page<StoreResponse> responses = storeService.findAll(pageable);

        // then
        assertNotNull(responses);
        assertFalse(responses.isEmpty());

        responses.forEach(productResponse -> {
            assertNotNull(productResponse.getIdx());
            assertNotNull(productResponse.getName());
            // System.out.println(productResponse);
        });
    }

    @Nested
    class findNPlusOneTest {
        @BeforeEach
        void setUp() {
            Store store1 = Store.builder().name("파이전문점").build();
            Product product1 = Product.builder().name("초코파이").cost(Cost.builder().krwValue(1000).usdValue(1).build()).build();
            Product product2 = Product.builder().name("가나파이").cost(Cost.builder().krwValue(2000).usdValue(2).build()).build();
            Product product3 = Product.builder().name("빅파이").cost(Cost.builder().krwValue(3000).usdValue(3).build()).build();
            store1.addProduct(product1, product2, product3);
            Employ employ1 = Employ.builder().name("직원1").build();
            Employ employ2 = Employ.builder().name("직원2").build();
            store1.addEmploy(employ1, employ2);
            storeRepository.save(store1);

            Store store2 = Store.builder().name("치킨나라 피자공주").build();
            Product product4 = Product.builder().name("불고기 피자").cost(Cost.builder().krwValue(1000).usdValue(1).build()).build();
            Product product5 = Product.builder().name("치킨").cost(Cost.builder().krwValue(2000).usdValue(2).build()).build();
            Product product6 = Product.builder().name("콜라").cost(Cost.builder().krwValue(3000).usdValue(3).build()).build();
            store2.addProduct(product4, product5, product6);
            Employ employ3 = Employ.builder().name("직원3").build();
            Employ employ4 = Employ.builder().name("직원4").build();
            store2.addEmploy(employ3, employ4);
            storeRepository.save(store2);

            Store store3 = Store.builder().name("할매순대국").build();
            Product product7 = Product.builder().name("순대국").cost(Cost.builder().krwValue(1000).usdValue(1).build()).build();
            Product product8 = Product.builder().name("뼈해장국").cost(Cost.builder().krwValue(2000).usdValue(2).build()).build();
            Product product9 = Product.builder().name("내장탕").cost(Cost.builder().krwValue(3000).usdValue(3).build()).build();
            store3.addProduct(product7, product8, product9);
            Employ employ5 = Employ.builder().name("직원5").build();
            Employ employ6 = Employ.builder().name("직원6").build();
            store3.addEmploy(employ5, employ6);
            storeRepository.save(store3);

            entityManager.flush();
            entityManager.clear();
        }

        @Test
        @DisplayName("Store findAll N+1 문제 확인")
        void findAll_N_PLUS_1() {

            // N+1 문제 발생 영역 : 내부 stores.map(StoreResponse::of)
            // @EntityGraph(outer fetch join) 와 LinkedHashSet(카테시안 곱 중복 제거) 으로 문제 해결
            Page<StoreResponse> responses = storeService.findAll(Pageable.unpaged());

            // 결과 print
            responses.forEach(storeResponse -> {
                System.out.println(storeResponse.getName());
                storeResponse.getProducts().forEach(productResponse -> System.out.println("\t-"+productResponse.getName()));
                System.out.println("///");
                storeResponse.getEmployees().forEach(employResponse -> System.out.println("\t-"+employResponse.getName()));
                System.out.println("------------------------------------");
            });
        }

        @Test
        @DisplayName("Store findById N+1 문제 확인")
        void find_byId_N_PLUS_1() {
            // given
            Store store = Store.builder().name("파이전문점").build();
            Product product1 = Product.builder().name("초코파이").cost(Cost.builder().krwValue(1000).usdValue(1).build()).build();
            Product product2 = Product.builder().name("가나파이").cost(Cost.builder().krwValue(2000).usdValue(2).build()).build();
            Product product3 = Product.builder().name("빅파이").cost(Cost.builder().krwValue(3000).usdValue(3).build()).build();
            store.addProduct(product1, product2, product3);
            Long savedId = storeRepository.saveAndFlush(store).getIdx();
            entityManager.clear();

            // when
            Store response = storeRepository.findById(savedId)
                    .orElseThrow(() -> new StoreNotFoundException("Invalid Id"));

            // then
            assertNotNull(response.getIdx());
            response.getProducts().forEach(product -> {
                assertNotNull(product.getName());   // 단일 조회는 N+1 문제 발생하지 않음. Lazy Loading 잘 작동함
            });
        }
    }

    @Test
    @DisplayName("Store 에 매핑된 Product 목록 전체를 한번에 가져온다")
    void find_byId_returnsStoreResponse() {
        // given
        Store store = Store.builder().name("파이전문점").build();
        Product product1 = Product.builder().name("초코파이").cost(Cost.builder().krwValue(1000).usdValue(1).build()).build();
        Product product2 = Product.builder().name("가나파이").cost(Cost.builder().krwValue(2000).usdValue(2).build()).build();
        Product product3 = Product.builder().name("빅파이").cost(Cost.builder().krwValue(3000).usdValue(3).build()).build();
        store.addProduct(product1, product2, product3);
        Long savedId = storeRepository.save(store).getIdx();

        // when
        StoreResponse response = storeService.findById(savedId);

        // then
        assertNotNull(response);
        assertNotNull(response.getIdx());
        assertNotNull(response.getName());
        assertNotNull(response.getProducts());
        assertEquals(3, response.getProducts().size());
    }

    @Test
    @DisplayName("Store 조회시 id 값이 null 인 경우 StoreNotFoundException 을 반환한다.")
    void find_byNull_returnsStoreNotFoundException() {
        // given
        Long nullId = null;

        // when
        StoreNotFoundException exception = assertThrows(
                StoreNotFoundException.class,
                () -> storeService.findById(nullId));

        // then
        assertNotNull(exception);
    }

    @Test
    @DisplayName("Store 조회시 id 값이 존재하지 않는 경우 StoreNotFoundException 을 반환한다.")
    void find_byNonExistentId_returnsStoreNotFoundException() {
        // given
        Long nonExistentId = -1L;

        // when
        StoreNotFoundException exception = assertThrows(
                StoreNotFoundException.class,
                () -> storeService.findById(nonExistentId));

        // then
        assertNotNull(exception);
    }
}