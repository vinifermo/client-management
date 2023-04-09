package com.desafio.squad.service;

import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;

import java.util.UUID;

public interface TelefoneService {

    TelefoneResponseDTO telefonePorId(UUID id);

    void atualizar(UUID id, TelefoneRequestDTO telefoneRequestDTO);
}
