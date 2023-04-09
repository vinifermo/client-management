package com.desafio.squad.exception;

import org.springframework.core.MethodParameter;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;

public class TipoDocumentoInvalidoException extends MethodArgumentNotValidException {

    public TipoDocumentoInvalidoException(MethodParameter methodParameter, BeanPropertyBindingResult erros) {
        super(methodParameter, erros);
    }
}
