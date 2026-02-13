package org.example.geodistributedindexservice.region;

import lombok.Getter;
import org.example.geodistributedindexservice.model.Product;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class LocalIndex {

    private final Map<Long, Product> index = new ConcurrentHashMap<>();

    public void updateIndex(Product product) {
        Product copy = new Product(
                product.getId(),
                product.getName(),
                product.getStock()
        );
        index.put(Long.valueOf(product.getId()), copy);
    }

    public Product search(Long id) {
        return index.get(Long.valueOf(id));
    }
}
