package com.stefanini.technicaltest.implementations;

import com.stefanini.technicaltest.entities.PruebaEstudiante;
import com.stefanini.technicaltest.repositories.PruebaEstudianteRepository;
import com.stefanini.technicaltest.implementations.services.PruebaEstudianteService;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class PruebaEstudianteImpl implements PruebaEstudianteService {

    private final PruebaEstudianteRepository estudiantesRepo;

    public PruebaEstudianteImpl(PruebaEstudianteRepository estudiantesRepo) {
        this.estudiantesRepo = estudiantesRepo;
    }

    @Override
    public ResponseEntity<?> all() {
        try{
            List<PruebaEstudiante> estudiantes = estudiantesRepo.findAll();
            JSONArray objects = new JSONArray();
            for (PruebaEstudiante pe: estudiantes) {
                JSONObject object = new JSONObject();
                object.appendField("id", pe.getId())
                        .appendField("nombre", pe.getNombre());
                objects.appendElement(object);
            }
            JSONObject result = new JSONObject()
                    .appendField("message", "OK")
                    .appendField("result", objects);
            return new ResponseEntity<>(result.toJSONString(), HttpStatus.OK);
        }catch (Exception e){
            JSONObject error = new JSONObject();
            error.appendField("message", "Error en consulta de estudiantes")
                    .appendField("error", e.getMessage());
            return new ResponseEntity<>(error.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        try{
            PruebaEstudiante estudiante = estudiantesRepo.find(id).orElse(new PruebaEstudiante());
            JSONObject result = new JSONObject()
                    .appendField("message", "OK")
                    .appendField("result", estudiante);
            return new ResponseEntity<>(result.toJSONString(), HttpStatus.OK);
        }catch (Exception e){
            JSONObject error = new JSONObject();
            error.appendField("message", "Error en consulta de estudiante")
                    .appendField("error", e.getMessage());
            return new ResponseEntity<>(error.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
