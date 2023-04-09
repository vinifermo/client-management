package com.desafio.squad.service;

import com.desafio.squad.assembler.ClienteAssembler;
import com.desafio.squad.assembler.TelefoneAssembler;
import com.desafio.squad.dto.request.ClienteRequestDTO;
import com.desafio.squad.dto.response.ClienteResponseDTO;
import com.desafio.squad.model.Cliente;
import com.desafio.squad.model.Telefone;
import com.desafio.squad.repository.ClienteRepository;
import com.desafio.squad.repository.ClienteRepositoryJpa;
import com.desafio.squad.repository.TelefoneRepositoryJpa;
import com.desafio.squad.util.ClienteValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

import static com.desafio.squad.exception.CustomExceptionHandler.CLIENTE_NAO_ENCONTRADO;

@Service
@Transactional
@RequiredArgsConstructor
public class ClienteService implements ClienteRepository {

    private final ClienteRepositoryJpa clienteRepositoryJpa;
    private final ClienteAssembler clienteAssembler;
    private final TelefoneAssembler telefoneAssembler;
    private final TelefoneRepositoryJpa telefoneRepositoryJpa;
    private final ClienteValidator clienteValidator;

    public List<ClienteResponseDTO> listar() {
        List<Cliente> clientes = clienteRepositoryJpa.findAll();
        return clienteAssembler.toCollectionModel(clientes);
    }

    public ClienteResponseDTO clientePorId(UUID id) {
        Cliente cliente = clienteRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CLIENTE_NAO_ENCONTRADO)));
        return clienteAssembler.toModel(cliente);
    }

    public Cliente criar(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteAssembler.toEntity(clienteRequestDTO);
        cliente = clienteRepositoryJpa.save(cliente);
        List<Telefone> telefones = telefoneAssembler.toEntity(clienteRequestDTO.getTelefones(), cliente);
        clienteValidator.validarTelefones(telefones);
        telefoneRepositoryJpa.saveAll(telefones);
        clienteValidator.validarClienteDuplicado(cliente);
        return cliente;
    }

    public void atualizar(UUID id, ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteAssembler.toEntity(clienteRequestDTO);
        clienteRepositoryJpa.findById(id)
                .map(clienteSalvo -> {
                    cliente.setId(clienteSalvo.getId());
                    clienteRepositoryJpa.save(cliente);

                    return clienteSalvo;
                }).orElseThrow(() -> new EntityNotFoundException(String.format(CLIENTE_NAO_ENCONTRADO)));
        List<Telefone> telefones = telefoneAssembler.toEntity(clienteRequestDTO.getTelefones(), cliente);
        clienteValidator.validarTelefones(telefones);
        clienteValidator.validarClienteDuplicado(cliente);
    }

    public void deletePorId(UUID id) {
        Cliente cliente = clienteRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(CLIENTE_NAO_ENCONTRADO)));
        clienteRepositoryJpa.delete(cliente);
    }

    public Page<ClienteResponseDTO> findByPage(String filter, Pageable pageable) {
        return clienteRepositoryJpa.findByPage(filter, pageable);
    }

}

