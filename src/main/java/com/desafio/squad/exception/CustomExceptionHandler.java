package com.desafio.squad.exception;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.EntityNotFoundException;

@ControllerAdvice
@RequiredArgsConstructor
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String CLIENTE_NAO_ENCONTRADO = "Cliente não encontrado";
    public static final String TELEFONE_NAO_ENCONTRADO = "Telefone não encontrado";
    public static final String TELEFONE_PRINCIPAl_INVALIDO = "Somente um telefone deve ser marcado como principal.";
    public static final String CLIENTE_DUPLICADO = "Já existe um cliente cadastrado com este CPF/CNPJ.";

    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseError handle(EntityNotFoundException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TipoDocumentoInvalidoException.class)
    public ResponseError handle(TipoDocumentoInvalidoException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(TelefonePrincipalInvalidoException.class)
    public ResponseError handleTelefonePrincipalInvalidoException(TelefonePrincipalInvalidoException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ClienteDuplicadoException.class)
    public ResponseError handleClienteDuplicadoException(ClienteDuplicadoException ex) {
        ex.printStackTrace();
        return new ResponseError(ex.getMessage());
    }

}
