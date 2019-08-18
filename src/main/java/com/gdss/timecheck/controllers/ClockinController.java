package com.gdss.timecheck.controllers;

import com.gdss.timecheck.models.Clockin;
import com.gdss.timecheck.models.ClockinRequest;
import com.gdss.timecheck.services.ClockinService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/clockin")
public class ClockinController {

    @Autowired
    protected ClockinService service;

    @PostMapping("/create")
    public ResponseEntity<Clockin> create(@RequestBody ClockinRequest clockinRequest) {
        Clockin clockin = service.create(clockinRequest);
        return new ResponseEntity<>(clockin, HttpStatus.OK);
    }

}
