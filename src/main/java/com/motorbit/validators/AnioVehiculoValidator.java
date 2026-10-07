package com.motorbit.validators;

import java.time.Year;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AnioVehiculoValidator implements ConstraintValidator<AnioVehiculo, Integer> {
    private static final int ANIO_MINIMO = 1990;

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int anioMaximo = Year.now().getValue() + 1;
        boolean valido = value >= ANIO_MINIMO && value <= anioMaximo;

            if (!valido) {
                context.disableDefaultConstraintViolation();

                context.buildConstraintViolationWithTemplate(
                        String.format("El año del vehículo debe estar entre %s y %s.", ANIO_MINIMO, anioMaximo)
                ).addConstraintViolation();
            }

        return valido;
    }

}
