package org.example.geodistributedindexservice.service;

import org.example.geodistributedindexservice.model.Product;

public interface ReplicationStrategy {

    void replicate(Product product);

}
