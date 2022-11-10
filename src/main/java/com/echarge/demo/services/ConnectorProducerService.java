package com.echarge.demo.services;

import com.echarge.demo.entity.ConnectorEntity;
import com.echarge.demo.payload.request.ConnectorAddRequest;

public interface ConnectorProducerService {
    ConnectorEntity addConnector(ConnectorAddRequest request);
}
