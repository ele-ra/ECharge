package com.echarge.demo.controllers;

import com.echarge.demo.dto.InfoDTO;
import com.echarge.demo.services.InfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfoController {
    private final InfoService infoService;
    private static final Logger log = LoggerFactory.getLogger(InfoController.class);

    @Autowired
    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping(
            value = "/info/version",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public final ResponseEntity<InfoDTO> getAppVersion() {
        log.info("INFO_CONTROLLER: Fetch app info about version");
        return new ResponseEntity<>(infoService.getInfo(), HttpStatus.OK);
    }
}