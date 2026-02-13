package org.example.geodistributedindexservice.service;

import lombok.Setter;
import org.springframework.stereotype.Service;

@Setter
@Service
public class NetworkService {

    private boolean networkUp = true;

    public boolean isNetworkUp() {
        return !networkUp;
    }
}
