package com.motorbit.exception;

public class RecursoExistenteException extends RuntimeException {

    public RecursoExistenteException(
            String recurso,
            String campo,
            Object valor
    ) {
        super(String.format(
                "%s ya existe con %s: %s",
                recurso,
                campo,
                valor
        ));
    }
}
