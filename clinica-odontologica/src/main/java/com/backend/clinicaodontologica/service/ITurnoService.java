package com.backend.clinicaodontologica.service;



import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.TurnoModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exception.BadRequestException;
import com.backend.clinicaodontologica.exception.ResourceNotFoundException;

import java.util.List;

public interface ITurnoService {
    List<TurnoSalidaDto> listarTurnos();

    TurnoSalidaDto registrarTurno(TurnoEntradaDto turnoEntradaDto) throws BadRequestException, ResourceNotFoundException;

    TurnoSalidaDto buscarTurnoPorId(Long id) throws ResourceNotFoundException;

    void eliminarTurno(Long id) throws ResourceNotFoundException;

    TurnoSalidaDto actualizarTurno(TurnoModificacionEntradaDto TurnoModificacionEntradaDto) throws ResourceNotFoundException, BadRequestException;
}
