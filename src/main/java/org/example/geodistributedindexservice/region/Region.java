package org.example.geodistributedindexservice.region;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.geodistributedindexservice.model.Product;

@Getter
@NoArgsConstructor
public class Region {

    private final DataStore dataStore = new DataStore();
    private final LocalIndex localIndex = new LocalIndex();

    public void updateProduct(Product product) {
        dataStore.save(product);
        localIndex.updateIndex(product);
    }

    public Product search(Long id) {
        return localIndex.search(id);
    }
}

