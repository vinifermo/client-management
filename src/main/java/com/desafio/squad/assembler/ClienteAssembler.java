package com.desafio.squad.assembler;

import com.desafio.squad.dto.request.ClienteRequestDTO;
import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.ClienteResponseDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;
import com.desafio.squad.model.Cliente;
import com.desafio.squad.model.Telefone;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ClienteAssembler {

    public Cliente toEntity(ClienteRequestDTO request) {
        Cliente cliente = new Cliente();

        cliente.setNome(request.getNome());
        cliente.setSituacao(request.getSituacao());
        cliente.setDataCadastro(request.getDataCadastro());
        cliente.setTipoPessoaEnum(request.getTipoPessoaEnum());
        cliente.setNumeroDocumento(request.getNumeroDocumento());
        cliente.setNumeroRegistro(request.getNumeroRegistro());
        setTelefones(request.getTelefones(), cliente);

        return cliente;
    }

    public ClienteResponseDTO toModel(Cliente cliente) {
        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();

        clienteResponseDTO.setNome(cliente.getNome());
        clienteResponseDTO.setSituacao(cliente.getSituacao());
        clienteResponseDTO.setDataCadastro(cliente.getDataCadastro());
        clienteResponseDTO.setTipoPessoa(cliente.getTipoPessoaEnum());
        clienteResponseDTO.setNumeroRegistro(cliente.getNumeroRegistro());
        clienteResponseDTO.setNumeroDocumento(cliente.getNumeroDocumento());
        setTelefonesResponse(cliente.getTelefones(), clienteResponseDTO);

        return clienteResponseDTO;
    }

    public List<ClienteResponseDTO> toCollectionModel(List<Cliente> clientes) {
        return clientes.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }

    private void setTelefones(List<TelefoneRequestDTO> telefonesDTO, Cliente cliente) {
        List<Telefone> telefones = telefonesDTO.stream()
                .map(telefoneDTO -> {
                    Telefone telefone = new Telefone();
                    telefone.setNumero(telefoneDTO.getNumero());
                    telefone.setPrincipal(telefoneDTO.getPrincipal());
                    return telefone;
                })
                .collect(Collectors.toList());

        cliente.setTelefones(telefones);
    }

    private void setTelefonesResponse(List<Telefone> telefonesDTO, ClienteResponseDTO clienteResponseDTO) {
        List<TelefoneResponseDTO> telefones = telefonesDTO.stream()
                .map(telefoneDTO -> {
                    TelefoneResponseDTO telefone = new TelefoneResponseDTO();
                    telefone.setId(telefoneDTO.getId());
                    telefone.setNumero(telefoneDTO.getNumero());
                    telefone.setPrincipal(telefoneDTO.getPrincipal());
                    return telefone;
                })
                .collect(Collectors.toList());

        clienteResponseDTO.setTelefones(telefones);
    }

}
