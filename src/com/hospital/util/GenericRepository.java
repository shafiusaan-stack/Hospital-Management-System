package com.hospital.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GenericRepository<T> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(item);
    }

    public List<T> getAll() {
        return new ArrayList<>(items);
    }

    public boolean removeIf(java.util.function.Predicate<T> filter) {
        return items.removeIf(filter);
    }

    public List<T> filter(java.util.function.Predicate<T> predicate) {
        return items.stream().filter(predicate).collect(Collectors.toList());
    }

    public long count() {
        return items.size();
    }
}
