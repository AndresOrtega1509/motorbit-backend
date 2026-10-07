package com.motorbit.exception;

public class RecursoNoEncontradoException extends RuntimeException {
    public RecursoNoEncontradoException(String recurso,
                                        String campo,
                                        Object valor
    ) {
        super(String.format(
            "%s no encontrado con %s: %s", 
            recurso, 
            campo, 
            valor
        ));
    }
}
