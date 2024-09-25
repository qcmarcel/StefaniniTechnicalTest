package com.stefanini.technicaltest.endpoints;

import com.stefanini.technicaltest.implementations.services.PruebaEstudianteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.swing.text.html.HTMLDocument;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/v1")
public class TechnicalTestEnd {

    private final PruebaEstudianteService service;

    @GetMapping(path = "/hello")
    public ResponseEntity<?> helloWorld(){
        log.info("Hello World");
        return new ResponseEntity<>("Hello World", HttpStatus.OK);
    }

    @GetMapping(path= "/estudiantes")
    public ResponseEntity<?> showEstudiantes(){
        try {
            return service.all();
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path= "/estudiantes/{idEstudiante}")
    public ResponseEntity<?> findByIdEstudiantes(@PathVariable Long idEstudiante){
        try {
            return service.findById(idEstudiante);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
