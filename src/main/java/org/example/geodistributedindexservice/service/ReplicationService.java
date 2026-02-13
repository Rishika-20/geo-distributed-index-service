package org.example.geodistributedindexservice.service;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.example.geodistributedindexservice.model.Product;
import org.example.geodistributedindexservice.region.Region;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@Getter
public class ReplicationService {

    private final Region regionA = new Region();
    private final Region regionB = new Region();

    private ScheduledExecutorService scheduler =
            Executors.newSingleThreadScheduledExecutor();

    @Value("${replication.delay.ms}")
    private long replicationDelay;

    @Value("${replication.initial.delay.ms}")
    private long initialDelay;

    @Value("${replication.network.active}")
    private boolean networkActive;

    @PostConstruct
    public void startReplication() {

        scheduler.scheduleAtFixedRate(() -> {

            try {

                if (!networkActive) {
                    log.info("Network partition active. Replication paused.");
                    return;
                }

                log.info("Replication started...");

                for (Product product : regionA.getLocalIndex().getIndex().values()) {
                    regionB.getLocalIndex().updateIndex(product);
                }

            } catch (Exception e) {
                log.error("Replication error occurred", e);
            }

        }, initialDelay, replicationDelay, TimeUnit.MILLISECONDS);
    }

    public void toggleNetwork(boolean status) {
        this.networkActive = status;
    }
}
