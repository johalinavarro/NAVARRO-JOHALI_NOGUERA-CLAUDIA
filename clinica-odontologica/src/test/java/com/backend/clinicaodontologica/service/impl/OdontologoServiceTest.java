package com.backend.clinicaodontologica.service.impl;

import com.backend.clinicaodontologica.dto.entrada.odontologo.OdontologoEntradaDto;
import com.backend.clinicaodontologica.dto.salida.odontologo.OdontologoSalidaDto;
import com.backend.clinicaodontologica.exception.ResourceNotFoundException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OdontologoServiceTest {

    @Autowired
    private OdontologoService odontologoService;

    @Test
    @Order(1)
    void deberiaRegistrarUnOdontologoDeNombreMarioYRetornarElId(){
        OdontologoEntradaDto odontologoEntradaDto = new OdontologoEntradaDto("1452478526", "Mario","Morales");

        OdontologoSalidaDto odontologoSalidaDto = odontologoService.registrarOdontologo(odontologoEntradaDto);

        assertNotNull(odontologoSalidaDto.getId());
        assertEquals("Mario", odontologoSalidaDto.getNombre());
    }

    @Test
    @Order(2)
    void alIntentarEliminarUnOdontologoConId1YaEliminado_deberiaLanzarUnaResourceNotoundException(){

        try{
            odontologoService.eliminarOdontologo(1L);
        } catch (Exception exception){
            exception.printStackTrace();
        }

        assertThrows(ResourceNotFoundException.class, () -> odontologoService.eliminarOdontologo(1L));
    }

    @Test
    @Order(3)
    void deberiaDevolverUnaListaDeOdontologos(){
        List<OdontologoSalidaDto> odontologosDto = odontologoService.listarOdontologos();

        assertTrue(odontologosDto.isEmpty());
    }

}