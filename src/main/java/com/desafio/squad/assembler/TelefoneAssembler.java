package com.desafio.squad.assembler;

import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;
import com.desafio.squad.model.Cliente;
import com.desafio.squad.model.Telefone;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TelefoneAssembler {

    public List<Telefone> toEntity(List<TelefoneRequestDTO> request, Cliente cliente) {

        return request.stream()
                .map(telefoneDTO -> new Telefone(telefoneDTO.getNumero(), telefoneDTO.getPrincipal(), cliente))
                .collect(Collectors.toList());
    }

    public Telefone toEntity(TelefoneRequestDTO request) {
        Telefone telefone = new Telefone();

        telefone.setNumero(request.getNumero());
        telefone.setPrincipal(request.getPrincipal());

        return telefone;
    }

    public TelefoneResponseDTO toModel(Telefone response) {
        TelefoneResponseDTO telefoneResponseDTO = new TelefoneResponseDTO();

        telefoneResponseDTO.setId(response.getId());
        telefoneResponseDTO.setNumero(response.getNumero());
        telefoneResponseDTO.setPrincipal(response.getPrincipal());

        return telefoneResponseDTO;
    }

}
