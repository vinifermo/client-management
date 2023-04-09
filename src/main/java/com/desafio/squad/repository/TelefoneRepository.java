package com.desafio.squad.repository;

import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;

import java.util.UUID;

public interface TelefoneRepository {

    TelefoneResponseDTO telefonePorId(UUID id);

    void atualizar(UUID id, TelefoneRequestDTO telefoneRequestDTO);
}
