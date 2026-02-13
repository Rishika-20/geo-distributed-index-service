package org.example.geodistributedindexservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.geodistributedindexservice.model.Product;
import org.example.geodistributedindexservice.region.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AsyncReplicationStrategy implements ReplicationStrategy {

    @Qualifier("regionB")
    private final Region regionB;
    private final NetworkService networkService;

    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    @Override
    public void replicate(Product product) {

        if (networkService.isNetworkUp()) {
            log.warn("[ASYNC] Network is DOWN. Replication skipped.");
            return;
        }

        log.info("[ASYNC] Scheduling replication for product {}", product.getId());

        executor.submit(() -> {
            try {
                Thread.sleep(1000); // simulate delay
                regionB.getLocalIndex().updateIndex(product);
                log.info("[ASYNC] Product {} replicated to Region B", product.getId());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
}

