package com.backend.clinicaodontologica.service.impl;


import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.DomicilioEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.entrada.turno.TurnoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.dto.salida.turno.TurnoSalidaDto;
import com.backend.clinicaodontologica.exception.BadRequestException;
import com.backend.clinicaodontologica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TurnoServiceTest {

    @Autowired
    PacienteService pacienteService;
    @Autowired
    OdontologoService odontologoService;
    @Autowired
    private TurnoService turnoService;


    @Test
    @Order(1)
    void alIntentarEliminarUnTurnoConId1YaEliminado_deberiaLanzarUnaResourceNotoundException(){

        try{
            turnoService.eliminarTurno(1L);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        assertThrows(ResourceNotFoundException.class, () -> turnoService.eliminarTurno(1L));
    }

    @Test
    @Order(2)
    void deberiaDevolverUnaListaDeTurnos(){
        List<TurnoSalidaDto> turnosDto = turnoService.listarTurnos();

        assertTrue(turnosDto.isEmpty());
    }

    @Test
    @Order(3)
    void registroUnTurno_devuelveUnId() throws BadRequestException, ResourceNotFoundException {
        PacienteEntradaDto pacienteEntradaDto = new PacienteEntradaDto("Mariano", "Guerra", 48576545, LocalDate.of(2023, 12, 15),
                new DomicilioEntradaDto("Moneda", 4856, "Santiago", "Metropolitana"));
        PacienteSalidaDto pacienteSalidaDto = pacienteService.registrarPaciente(pacienteEntradaDto);

        OdontologoEntradaDto odontologoARegistrar = new OdontologoEntradaDto("567538425", "Carolina", "Leiva");
        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoARegistrar);

        TurnoEntradaDto turnoEntradaDto = new TurnoEntradaDto((LocalDateTime.of(2023,12,15,10,30)),odontologoSalidaDto.getId(), pacienteSalidaDto.getId());
        TurnoSalidaDto turnoSalidaDto = turnoService.registrarTurno(turnoEntradaDto);

        assertEquals(3L, turnoSalidaDto.getId());
    }

}