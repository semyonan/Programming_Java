package com.lab.services.impl;

import com.lab.security.UserDetailsImpl;
import com.lab.services.AccessService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AccessServiceImpl implements AccessService {
    @Override
    public Long getUserId() {
        return Long.parseLong(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }

    @Override
    public boolean userHasAccess(Long id) {
        return id == Long.parseLong(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername());
    }
}
