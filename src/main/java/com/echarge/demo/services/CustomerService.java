package com.echarge.demo.services;

import com.echarge.demo.entity.CustomerEntity;

public interface CustomerService {
    CustomerEntity findOneByUserId(Long userId);
}
