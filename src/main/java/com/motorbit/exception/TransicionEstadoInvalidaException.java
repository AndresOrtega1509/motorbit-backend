package com.motorbit.exception;

import com.motorbit.model.enums.EstadoOrden;

public class TransicionEstadoInvalidaException extends RuntimeException {

    public TransicionEstadoInvalidaException(
            EstadoOrden estadoActual,
            EstadoOrden nuevoEstado
    ) {
        super(String.format(
                "No se puede cambiar el estado de %s a %s.",
                estadoActual,
                nuevoEstado
        ));
    }
}
