package kr.nzzi.test.jpatest1.domain.store.exception;

public class StoreNotFoundException extends RuntimeException {

    public StoreNotFoundException(String msg) {
        super(msg);
    }
}
