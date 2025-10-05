package com.linear_algebra.thinking_machine.service;

import com.linear_algebra.thinking_machine.model.Matrix;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory store for matrices.
 * Survives across requests while JVM is running.
 */
@Service
public class MatrixStore {

    private final ConcurrentHashMap<String, Matrix> store = new ConcurrentHashMap<>();

    public String create(Matrix matrix) {
        String uuid = UUID.randomUUID().toString();
        store.put(uuid, matrix);
        return uuid;
    }

    public Optional<Matrix> get(String uuid) {

        return Optional.ofNullable(store.get(uuid));
    }

    public List<String> listIDs() {
        return new ArrayList<>(store.keySet());
    }

    public void delete(String uuid) {
        store.remove(uuid);
    }

    public boolean update(String uuid, Matrix matrix) {
        return store.replace(uuid, matrix) != null;

    }

    public void clearAll() {
        store.clear();
    }
}
