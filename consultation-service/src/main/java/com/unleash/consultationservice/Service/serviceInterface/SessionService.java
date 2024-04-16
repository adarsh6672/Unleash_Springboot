package com.unleash.consultationservice.Service.serviceInterface;

import org.springframework.http.ResponseEntity;

public interface SessionService {
    ResponseEntity bookSession(int userId, int slotId);
}
