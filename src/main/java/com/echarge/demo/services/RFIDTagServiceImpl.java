package com.echarge.demo.services;

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
    public RFIDTagEntity findOneByNumber(int number) {
        return rfidTagRepository.findOneByNumber(number).orElseThrow((
                () -> new NoSuchElementException(format("RFIDTag with  number =%d not found", number))));
    }

    @Override
    public Collection<RFIDTagEntity> findAllByCustomerId(long customerId) {
        return rfidTagRepository.findAllByCustomerId(customerId);
    }

    @Override
    public RFIDTagEntity findOneByVehicleId(long vehicleId) {
        return rfidTagRepository.findOneByVehicleId(vehicleId).orElseThrow((
                () -> new NoSuchElementException("Provided vehicle wasn't tagged to any RDFID yet")));
    }

    @Override
    public boolean belongsToCustomer(long customerId, long rfidTagId) {
        return rfidTagRepository.findAllByCustomerId(customerId)
                .contains(rfidTagRepository.findOneById(rfidTagId));
    }
}
