package com.rk.domain;

public class LongId<T> {
    private long id;

    public LongId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
