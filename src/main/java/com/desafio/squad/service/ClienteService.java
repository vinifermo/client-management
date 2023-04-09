package com.desafio.squad.service;

import com.desafio.squad.dto.request.ClienteRequestDTO;
import com.desafio.squad.dto.response.ClienteResponseDTO;
import com.desafio.squad.model.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ClienteService {

    List<ClienteResponseDTO> listar();

    ClienteResponseDTO clientePorId(UUID id);

    Cliente criar(ClienteRequestDTO clienteRequestDTO);

    void atualizar(UUID id, ClienteRequestDTO clienteRequestDTO);

    void deletePorId(UUID id);

    Page<ClienteResponseDTO> findByPage(String filter, Pageable pageable);
}
