package org.example.geodistributedindexservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.geodistributedindexservice.model.Product;
import org.example.geodistributedindexservice.service.ReplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ReplicationService replicationService;

    /*** Add product to Region A ***/
    @PostMapping("/regionA")
    public ResponseEntity<String> addProductToRegionA(@RequestBody Product product) {

        log.info("Adding product to Region A: {}", product);

        replicationService.getRegionA()
                .getLocalIndex()
                .updateIndex(product);

        log.info("Product added successfully to Region A");

        return ResponseEntity.ok("Product added to Region A");
    }

    /*** Get product from Region B ***/
    @GetMapping("/regionB/{id}")
    public ResponseEntity<Product> getFromRegionB(@PathVariable Long id) {

        log.info("Fetching product from Region B with ID: {}", id);

        Product product = replicationService
                .getRegionB()
                .getLocalIndex()
                .getIndex()
                .get(id);

        if (product == null) {
            log.warn("Product with ID {} not found in Region B", id);
            return ResponseEntity.notFound().build();
        }

        log.info("Product found in Region B: {}", product);

        return ResponseEntity.ok(product);
    }

    /*** Toggle network partition ***/
    @PostMapping("/network/{status}")
    public ResponseEntity<String> toggleNetwork(@PathVariable boolean status) {

        log.info("Toggling network status to: {}", status);

        replicationService.toggleNetwork(status);

        return ResponseEntity.ok("Network status updated to: " + status);
    }
}
