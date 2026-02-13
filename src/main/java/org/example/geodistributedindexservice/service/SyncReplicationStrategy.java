package org.example.geodistributedindexservice.service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.geodistributedindexservice.model.Product;
import org.example.geodistributedindexservice.region.Region;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SyncReplicationStrategy implements ReplicationStrategy {

    @Qualifier("regionB")
    private final Region regionB;
    private final NetworkService networkService;

    @Override
    public void replicate(Product product) {

        if (networkService.isNetworkUp()) {
            log.warn("SYNC.. Network is DOWN. Replication skipped.");
            return;
        }

        log.info("SYNC.. Replicating product {} to Region B", product.getId());
        regionB.getLocalIndex().updateIndex(product);
    }
}
