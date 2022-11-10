package com.echarge.demo.services;

import com.echarge.demo.entity.CustomerEntity;
import com.echarge.demo.entity.RFIDTagEntity;
import com.echarge.demo.repository.RFIDTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.NoSuchElementException;

import static java.lang.String.format;

@Service
public class RFIDTagServiceImpl implements RFIDTagService {

    private final RFIDTagRepository rfidTagRepository;

    @Autowired
    public RFIDTagServiceImpl(RFIDTagRepository rfidTagRepository) {
        this.rfidTagRepository = rfidTagRepository;
    }

    @Override
    public RFIDTagEntity findOneByNameAndNumber(String name, Integer number) {
        return rfidTagRepository.findOneByNameAndNumber(name, number).orElseThrow((
                () -> new NoSuchElementException(format("RFIDTag with name = %s and number =%d not found", name, number))));
    }

    @Override
    public Collection<RFIDTagEntity> findAllByCustomerId(Long customerId) {
        return rfidTagRepository.findAllByCustomerId(customerId);
    }

    @Override
    public RFIDTagEntity findOneByVehicleId(Long vehicleId) {
        return rfidTagRepository.findOneByVehicleId(vehicleId).orElseThrow((
                () -> new NoSuchElementException("Provided vehicle wasn't tagged to any RDFID yet")));
    }

    @Override
    public boolean belongsToCustomer(CustomerEntity customer, RFIDTagEntity rfidTag) {
        return rfidTagRepository.findAllByCustomerId(customer.getId()).contains(rfidTag);
    }
}
