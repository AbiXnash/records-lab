package com.recordslab.query;

/** Rule 4: pages are numbered from 1; a missing size is 20 and a larger one is cut to 100. */
public record Paging(int page, int size) {
    public static final int DEFAULT_SIZE = 20;
    public static final int MAX_SIZE = 100;

    public static Paging of(Integer page, Integer size) {
        int p = page == null || page < 1 ? 1 : page;
        int s = size == null || size < 1 ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
        return new Paging(p, s);
    }

    public int offset() {
        return (page - 1) * size;
    }
}
