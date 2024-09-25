package com.stefanini.technicaltest.implementations;

import com.stefanini.technicaltest.entities.PruebaEstudiante;
import com.stefanini.technicaltest.repositories.PruebaEstudianteRepository;
import com.stefanini.technicaltest.implementations.services.PruebaEstudianteService;
import com.stefanini.technicaltest.types.PruebaEstudianteDto;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class PruebaEstudianteImpl implements PruebaEstudianteService {

    private final PruebaEstudianteRepository estudiantesRepo;
    private JSONObject error = new JSONObject();

    public PruebaEstudianteImpl(PruebaEstudianteRepository estudiantesRepo) {
        this.estudiantesRepo = estudiantesRepo;
    }

    @Override
    public ResponseEntity<?> all() {
        try {
            List<PruebaEstudiante> estudiantes = estudiantesRepo.findAll();
            JSONArray objects = new JSONArray();
            for (PruebaEstudiante pe : estudiantes) {
                PruebaEstudianteDto dto = new PruebaEstudianteDto(pe);
                /* JSONObject object = new JSONObject();
                object.appendField("id", pe.getId())
                        .appendField("nombre", pe.getNombre()); */
                objects.appendElement(dto);
            }
            JSONObject result = new JSONObject()
                    .appendField("message", "OK")
                    .appendField("result", objects);
            return new ResponseEntity<>(result.toJSONString(), HttpStatus.OK);
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.appendField("message", "Error en consulta de estudiantes")
                    .appendField("error", e.getMessage());
            return new ResponseEntity<>(error.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> findById(Long id) {
        try {
            Optional<PruebaEstudiante> exists = estudiantesRepo.find(id);
            if (exists.isEmpty()) return getResponseNotFound(id, "find");
            PruebaEstudianteDto estudiante = new PruebaEstudianteDto(exists.get()
                    /*.orElse(new PruebaEstudiante())*/);
            JSONObject result = new JSONObject()
                    .appendField("message", "OK")
                    .appendField("result", estudiante);
            return new ResponseEntity<>(result.toJSONString(), HttpStatus.OK);
        } catch (Exception e) {
            JSONObject error = new JSONObject();
            error.appendField("message", "Error en consulta de estudiante")
                    .appendField("error", e.getMessage());
            return new ResponseEntity<>(error.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ResponseEntity<String> getResponseNotFound(Long id, String operation) {
        String issue = String.format("Estudiante:(%d) not found: ", id);
        String log_format = new StringBuilder().append("ERROR in Estudiante::")
                .append(operation).append(" -> ")
                .append(issue).toString();
        log.info(log_format);
        error.appendField("error", issue);
        return new ResponseEntity<>(error.toJSONString(), HttpStatus.NOT_FOUND);
    }

    @Override
    public ResponseEntity<?> store(PruebaEstudianteDto estudiante) {
        error.appendField("message", "Error en Creación de Estudiante");
        try {
            Optional<PruebaEstudiante> exists = estudiantesRepo.findByIdOrNombre(estudiante.getId(), estudiante.getNombre());
            if (exists.isPresent()) {
                String issue = String.format("Estudiante:(%d) existe: %s", exists.get().getId(), exists.get().getNombre());
                String log_format = new StringBuilder().append("ERROR in estudiante::create -> ")
                        .append(issue).toString();
                log.info(log_format);
                error.appendField("error", issue);
                return new ResponseEntity<>(error.toJSONString(), HttpStatus.BAD_REQUEST);
            }
            PruebaEstudiante data = estudiante.toEntity();
            estudiantesRepo.save(data);
            String result = new JSONObject()
                    .appendField("message", "OK")
                    .appendField("description", String.format("Estudiante:(%d)", data.getId()))
                    .toJSONString();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception zona_update_e) {
            String log_format = new StringBuilder().append("ERROR in zona::update -> ")
                    .append(zona_update_e.getMessage()).toString();
            log.info(log_format);
            error.appendField("error", zona_update_e.getMessage());
            return new ResponseEntity<>(error.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> update(PruebaEstudianteDto estudiante, Long id) {
        error.appendField("message", "Error en Actualización de Estudiante");
        try {
            Optional<PruebaEstudiante> exists = estudiantesRepo.find(id);
            if (exists.isEmpty()) return getResponseNotFound(id, "update");
            PruebaEstudiante data = estudiante.merge(exists.get());
            estudiantesRepo.save(data);
            String result = new JSONObject()
                    .appendField("message", "OK")
                    .appendField("description", String.format("Estudiante:(%d)", id))
                    .toJSONString();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception zona_update_e) {
            String log_format = new StringBuilder().append("ERROR in Estudiante::update -> ")
                    .append(zona_update_e.getMessage()).toString();
            log.info(log_format);
            error.appendField("error", zona_update_e.getMessage());
            return new ResponseEntity<>(error.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> delete(Long id, Map<String, String> queryParams) {
        error.appendField("message", "Error en Borrado de Estudiante");
        try {
            Optional<PruebaEstudiante> exists = estudiantesRepo.find(id);
            if (exists.isEmpty()) {
                return getResponseNotFound(id, "delete");
            }
            PruebaEstudiante data = exists.get();
            if (!queryParams.containsKey("force") || !queryParams.get("force").equals("true")) {
                estudiantesRepo.saveCache(data);
            }
            estudiantesRepo.delete(data);
            String result = new JSONObject()
                    .appendField("message", "OK")
                    .appendField("description", String.format("Estudiante:(%d)", id))
                    .toJSONString();
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception zona_update_e) {
            String log_format = new StringBuilder().append("ERROR in Estudiante::delete -> ")
                    .append(zona_update_e.getMessage()).toString();
            log.info(log_format);
            error.appendField("error", zona_update_e.getMessage());
            return new ResponseEntity<>(error.toJSONString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
