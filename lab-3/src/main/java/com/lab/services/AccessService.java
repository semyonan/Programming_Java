package com.lab.services;

import org.springframework.stereotype.Service;

@Service
public interface AccessService {
    public Long getUserId();

    public boolean userHasAccess(Long id);
}
