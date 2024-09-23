package com.stefanini.technicaltest.endpoints;

import com.stefanini.technicaltest.services.PruebaEstudianteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1")
public class TechnicalTestEnd {

    @Autowired
    private PruebaEstudianteService service;



}
