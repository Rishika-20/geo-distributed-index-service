package org.example.geodistributedindexservice.region;

import lombok.Getter;
import org.example.geodistributedindexservice.model.Product;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Component
public class DataStore {

    private final Map<String, Product> storage = new ConcurrentHashMap<>();

    public void save(Product product) {
        storage.put(product.getId(), product);
    }

    public Product get(String id) {
        return storage.get(id);
    }
}
