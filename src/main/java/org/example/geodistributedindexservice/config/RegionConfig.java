package org.example.geodistributedindexservice.config;

import org.example.geodistributedindexservice.region.Region;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

    @Configuration
    public class RegionConfig {

        @Bean
        public Region regionA() {
            return new Region();
        }

        @Bean
        public Region regionB() {
            return new Region();
        }
    }

