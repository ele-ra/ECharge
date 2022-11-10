package com.echarge.demo.services;

import com.echarge.demo.entity.CustomerEntity;
import com.echarge.demo.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public CustomerEntity findOneByUserId(Long userId) {
        return customerRepository.findOneByUserId(userId).orElseThrow((
                () -> new NoSuchElementException("There are no customers associated with a user")));
    }
}
