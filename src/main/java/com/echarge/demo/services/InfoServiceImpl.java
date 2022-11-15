package com.echarge.demo.services;

import com.echarge.demo.config.AppConfig;
import com.echarge.demo.dto.InfoDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class InfoServiceImpl implements InfoService {
    private static final Logger log = LoggerFactory.getLogger(InfoServiceImpl.class);
    private final AppConfig appConfig;
    private InfoDTO info = null;

    @Autowired
    public InfoServiceImpl(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @Cacheable(value = "info")
    @Override
    public InfoDTO getInfo() {
        if (info == null) {
            info = new InfoDTO();
            info.setVersionApp(appConfig.getVersionApp());
            info.setVersionDb(appConfig.getVersionDb());
            log.debug("INFO_SERVICE: Application Info initialized");
        }
        return info;
    }
}
