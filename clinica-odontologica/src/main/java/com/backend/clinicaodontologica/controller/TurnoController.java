package com.backend.clinicaodontologica.controller;


import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exception.BadRequestException;
import com.backend.clinicaodontologica.exception.ResourceNotFoundException;
import com.backend.clinicaodontologica.service.ITurnoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/turnos")
@CrossOrigin
public class TurnoController {

    private ITurnoService iTurnoService;

    public TurnoController(ITurnoService iTurnoService) {
        this.iTurnoService = iTurnoService;
    }

    //POST
    @PostMapping("/registrar")
    public ResponseEntity<TurnoSalidaDto> registrarTurno(@RequestBody @Valid TurnoEntradaDto turnoEntradaDto) throws BadRequestException, ResourceNotFoundException {
        return new ResponseEntity<>(iTurnoService.registrarTurno(turnoEntradaDto), HttpStatus.CREATED);
    }


    //GET
    @GetMapping("/buscar/{id}")
    public ResponseEntity<TurnoSalidaDto> buscarTurnoPorId(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity<>(iTurnoService.buscarTurnoPorId(id), HttpStatus.OK);
    }

    @GetMapping("/listar")
    public ResponseEntity<List<TurnoSalidaDto>> listarTurnos() {
        return new ResponseEntity<>(iTurnoService.listarTurnos(), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/actualizar")
    public ResponseEntity<TurnoSalidaDto> actualizarTurno(@RequestBody @Valid TurnoModificacionEntradaDto turno) throws ResourceNotFoundException, BadRequestException{
        return new ResponseEntity<>(iTurnoService.actualizarTurno(turno), HttpStatus.CREATED);
    }

    //DELETE
    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<?> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        iTurnoService.eliminarTurno(id);
        return new ResponseEntity<>("Turno eliminado correctamente", HttpStatus.OK);
    }
}
