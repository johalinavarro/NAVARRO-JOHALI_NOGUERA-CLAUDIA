package com.backend.clinicaodontologica.service.impl;


import com.backend.clinicaodontologica.dto.entrada.paciente.PacienteEntradaDto;
import com.backend.clinicaodontologica.dto.modificacion.PacienteModificacionEntradaDto;
import com.backend.clinicaodontologica.dto.salida.paciente.PacienteSalidaDto;
import com.backend.clinicaodontologica.entity.Paciente;
import com.backend.clinicaodontologica.exception.ResourceNotFoundException;
import com.backend.clinicaodontologica.repository.PacienteRepository;
import com.backend.clinicaodontologica.service.IPacienteService;
import com.backend.clinicaodontologica.utils.JsonPrinter;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService implements IPacienteService {

    private final Logger LOGGER = LoggerFactory.getLogger(PacienteService.class);

    private PacienteRepository pacienteRepository;

    private ModelMapper modelMapper;

    public PacienteService(PacienteRepository pacienteRepository, ModelMapper modelMapper) {
        this.pacienteRepository = pacienteRepository;
        this.modelMapper = modelMapper;
        configureMapping();
    }

    public PacienteSalidaDto registrarPaciente(PacienteEntradaDto pacienteEntradaDto) {
        //convertimos mediante el mapper de dtoEntrada a entidad
        LOGGER.info("PacienteEntradaDto: " + JsonPrinter.toString(pacienteEntradaDto));
        Paciente pacienteEntidad = modelMapper.map(pacienteEntradaDto, Paciente.class);

        //mandamos a persistir a la capa dao y obtenemos una entidad
        Paciente pacienteAPersistir = pacienteRepository.save(pacienteEntidad);

        //transformamos la entidad obtenida en salidaDto
        PacienteSalidaDto pacienteSalidaDto = modelMapper.map(pacienteAPersistir, PacienteSalidaDto.class);
        LOGGER.info("PacienteSalidaDto: " + JsonPrinter.toString(pacienteSalidaDto));

        return pacienteSalidaDto;
    }

    public List<PacienteSalidaDto> listarPacientes() {
        List<PacienteSalidaDto> pacienteSalidaDto = pacienteRepository.findAll()
                .stream()
                .map(paciente -> modelMapper.map(paciente, PacienteSalidaDto.class))
                .toList();

        if (LOGGER.isInfoEnabled())
            LOGGER.info("Listado de todos los pacientes: {}", JsonPrinter.toString(pacienteSalidaDto));
        return pacienteSalidaDto;
    }

    @Override
    public PacienteSalidaDto buscarPacientePorId(Long id) throws ResourceNotFoundException{
        Paciente pacienteBuscado = pacienteRepository.findById(id).orElse(null);
        PacienteSalidaDto pacienteEncontrado = null;

        if (pacienteBuscado != null) {
            pacienteEncontrado = modelMapper.map(pacienteBuscado, PacienteSalidaDto.class);
            LOGGER.info("Paciente encontrado: {}", JsonPrinter.toString(pacienteEncontrado));
        } else {
            LOGGER.error("El id no se encuentra registrado en la base de datos");
            throw new ResourceNotFoundException("No se ha encontrado el paciente con id "+ id);
        }

        return pacienteEncontrado;
    }

    @Override
    public PacienteSalidaDto actualizarPaciente(PacienteModificacionEntradaDto pacienteModificacionEntradaDto) throws ResourceNotFoundException{
        Paciente pacienteRecibido = modelMapper.map(pacienteModificacionEntradaDto, Paciente.class);
        Paciente pacienteAActualizar = pacienteRepository.findById(pacienteRecibido.getId()).orElse(null);

        PacienteSalidaDto pacienteSalidaDto = null;

        if (pacienteAActualizar != null) {
            pacienteAActualizar = pacienteRecibido;
            pacienteRepository.save(pacienteAActualizar);

            pacienteSalidaDto = modelMapper.map(pacienteAActualizar, PacienteSalidaDto.class);
            LOGGER.warn("Paciente actualizado: {}", JsonPrinter.toString(pacienteSalidaDto));

        } else {
            LOGGER.error("No fue posible actualizar el paciente porque no se encuentra en nuestra base de datos");
            throw new ResourceNotFoundException("No fue posible actualizar el paciente porque no se encuentra en nuestra base de datos");
        }

        return pacienteSalidaDto;
    }

    @Override
    public void eliminarPaciente(Long id) throws ResourceNotFoundException {
        if (pacienteRepository.findById(id).orElse(null) != null) {
            pacienteRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el paciente con id: {}", id);
        } else {
            LOGGER.error("No se ha encontrado el paciente con id {}", id);
            throw new ResourceNotFoundException("No se ha encontrado el paciente con id " + id);
        }

    }

    private void configureMapping() {
        modelMapper.typeMap(PacienteEntradaDto.class, Paciente.class)
                .addMappings(mapper -> mapper.map(PacienteEntradaDto::getDomicilioEntradaDto, Paciente::setDomicilio));
        modelMapper.typeMap(Paciente.class, PacienteSalidaDto.class)
                .addMappings(modelMapper -> modelMapper.map(Paciente::getDomicilio, PacienteSalidaDto::setDomicilioSalidaDto));
    }


}
