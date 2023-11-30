package com.backend.clinicaodontologica.dto.entrada.turno;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class TurnoEntradaDto {
    @FutureOrPresent(message = "La fecha no puede ser anterior al día de hoy")
    @NotNull(message = "Debe especificarse la fecha y hora de ingreso del paciente")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime fechaYHora;

    @NotNull(message = "El turno tiene que tener un odontologo")
    private Long odontologoId;

    @NotNull(message = "El turno tiene que tener un paciente")
    private Long pacienteId;

    public TurnoEntradaDto() {
    }

    public TurnoEntradaDto(LocalDateTime fechaYHora, Long odontologoId, Long pacienteId) {
        this.fechaYHora = fechaYHora;
        this.odontologoId = odontologoId;
        this.pacienteId = pacienteId;
    }

    public LocalDateTime getFechaYHora() {
        return fechaYHora;
    }

    public void setFechaYHora(LocalDateTime fechaYHora) {
        this.fechaYHora = fechaYHora;
    }

    public Long getOdontologoId() {
        return odontologoId;
    }

    public void setOdontologoId(Long odontologoId) {
        this.odontologoId = odontologoId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
