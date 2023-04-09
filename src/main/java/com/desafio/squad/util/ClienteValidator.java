package com.desafio.squad.util;

import com.desafio.squad.exception.ClienteDuplicadoException;
import com.desafio.squad.exception.TelefonePrincipalInvalidoException;
import com.desafio.squad.model.Cliente;
import com.desafio.squad.model.Telefone;
import com.desafio.squad.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.desafio.squad.exception.CustomExceptionHandler.CLIENTE_DUPLICADO;
import static com.desafio.squad.exception.CustomExceptionHandler.TELEFONE_PRINCIPAl_INVALIDO;

@Component
@RequiredArgsConstructor
public class ClienteValidator {

    private final ClienteRepository clienteRepository;

    public void validarTelefones(List<Telefone> telefones) {
        long principalCount = telefones.stream().filter(Telefone::getPrincipal).count();
        if (principalCount != 1) {
            throw new TelefonePrincipalInvalidoException(String.format(TELEFONE_PRINCIPAl_INVALIDO));
        }
    }

    public void validarClienteDuplicado(Cliente cliente) {
        List<Cliente> clientes = clienteRepository.findAllByNumeroDocumento(cliente.getNumeroDocumento());

        if (clientes.size() > 1) {
            throw new ClienteDuplicadoException(String.format(CLIENTE_DUPLICADO));
        }
    }
}
