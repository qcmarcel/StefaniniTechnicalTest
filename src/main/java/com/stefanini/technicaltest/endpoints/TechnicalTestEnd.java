package com.stefanini.technicaltest.endpoints;

import com.stefanini.technicaltest.implementations.services.PruebaEstudianteService;
import com.stefanini.technicaltest.types.PruebaEstudianteDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.HTMLDocument;
import java.util.Map;

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
    public ResponseEntity<?> findByIdEstudiante(@PathVariable Long idEstudiante){
        try {
            return service.findById(idEstudiante);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping(path = "/estudiantes/")
    public ResponseEntity<?> storeEstudiante(@RequestBody PruebaEstudianteDto estudiante){
        try{
            return service.store(estudiante);
        }catch(Exception update_zona_e){
            return new ResponseEntity<>(update_zona_e.getLocalizedMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @PutMapping(path = "/estudiantes/{idEstudiante}")
    public ResponseEntity<?> updateEstudiante(@PathVariable Long idEstudiante, @RequestBody PruebaEstudianteDto estudiante){
        try{
            return service.update(estudiante, idEstudiante);
        }catch(Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping(path = "/estudiantes/{idEstudiante}" )
    public ResponseEntity<?> deleteEstudiante(@RequestParam Map<String, String> queryParams, @PathVariable Long idEstudiante){
        try{
            return service.delete(idEstudiante, queryParams);
        }catch(Exception e){
            return new ResponseEntity<>(e.getLocalizedMessage(), HttpStatus.BAD_GATEWAY);
        }
    }

}
