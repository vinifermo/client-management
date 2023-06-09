package com.desafio.squad.service;

import com.desafio.squad.assembler.TelefoneAssembler;
import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;
import com.desafio.squad.model.Telefone;
import com.desafio.squad.repository.TelefoneRepository;
import com.desafio.squad.repository.TelefoneRepositoryJpa;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

import static com.desafio.squad.exception.CustomExceptionHandler.TELEFONE_NAO_ENCONTRADO;

@Service
@Transactional
@RequiredArgsConstructor
public class TelefoneService implements TelefoneRepository {

    private final TelefoneRepositoryJpa telefoneRepositoryJpa;
    private final TelefoneAssembler telefoneAssembler;


    public TelefoneResponseDTO telefonePorId(UUID id) {
        Telefone telefone = telefoneRepositoryJpa.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(TELEFONE_NAO_ENCONTRADO)));
        return telefoneAssembler.toModel(telefone);
    }

    public void atualizar(UUID id, TelefoneRequestDTO telefoneRequestDTO) {
        TelefoneResponseDTO telefoneSalvo = telefonePorId(id);
        BeanUtils.copyProperties(telefoneRequestDTO, telefoneSalvo, "id");
        Telefone telefone = telefoneAssembler.toEntity(telefoneRequestDTO);
        telefoneRepositoryJpa.save(telefone);
    }
}