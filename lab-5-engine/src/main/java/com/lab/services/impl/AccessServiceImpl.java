package com.lab.services.impl;

import com.lab.services.AccessService;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {

    private Long userId;
    @Override
    public Long getUserId() {
        return userId;
    }

    @Override
    public void setUserId(Long id) {
        userId = id;
    }

    @Override
    public boolean userHasAccess(Long id) {
        return id.equals(userId);
    }
}
