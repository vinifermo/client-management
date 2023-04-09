package com.desafio.squad.service;

import com.desafio.squad.assembler.TelefoneAssembler;
import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;
import com.desafio.squad.model.Cliente;
import com.desafio.squad.model.Telefone;
import com.desafio.squad.repository.TelefoneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class TelefoneServiceImplTest extends AbstractServiceTest{

    @Mock(name = "telefoneRepository")
    private TelefoneRepository telefoneRepository;

    @Mock(name = "telefoneAssembler")
    private TelefoneAssembler telefoneAssembler;

    @Mock
    private TelefoneServiceImpl telefoneServiceMock;

    @InjectMocks
    private TelefoneServiceImpl telefoneServiceImpl;

    private Telefone telefone;
    private TelefoneResponseDTO telefoneResponseDTO;
    private TelefoneRequestDTO telefoneRequestDTO;
    private Cliente cliente;

    @Before
    public void setUp() {
        super.setUp();
        this.telefoneServiceImpl = new TelefoneServiceImpl(telefoneRepository, telefoneAssembler);
        this.registerService(this.telefoneServiceImpl);

        telefone = new Telefone(UUID.fromString("9de5354e-17fc-4cf8-ab1a-17d0a15ac57e"), "321-1234",true,cliente);
        telefoneRequestDTO = new TelefoneRequestDTO("321-1234",true);
        telefoneResponseDTO = new TelefoneResponseDTO(UUID.fromString("9de5354e-17fc-4cf8-ab1a-17d0a15ac57e"),"321-1234",true);
    }

    @Test
    public void deveRetornarUmTelefoneQuandoBuscarPorId() {
        UUID id = UUID.randomUUID();
        lenient().when(telefoneAssembler.toModel(telefone)).thenReturn(telefoneResponseDTO);
        when(telefoneRepository.findById(id)).thenReturn(Optional.of(telefone));

        Telefone response = telefoneRepository.findById(id).orElseThrow();

        assertEquals(telefoneResponseDTO.getId(), response.getId());
        assertEquals(telefoneResponseDTO.getNumero(), response.getNumero());
        assertEquals(telefoneResponseDTO.getPrincipal(), response.getPrincipal());

    }

    @Test
    public void deveRetornarEntityNotFoundQuandoBuscarUmTelefoneInexistente() {
        UUID id = UUID.randomUUID();
        when(telefoneRepository.findById(id)).thenThrow(jakarta.persistence.EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            telefoneRepository.findById(id);
        });
    }

    @Test
    public void deveAtualizarTelefonePassandoUmIdExistente() {
        UUID id = UUID.randomUUID();
        lenient().when(telefoneRepository.findById(id)).thenReturn(Optional.of(telefone));
        when(telefoneRepository.save(telefone)).thenReturn(telefone);

        Telefone response = telefoneRepository.save(telefone);
        telefoneServiceMock.atualizar(id, telefoneRequestDTO);

        assertEquals(telefoneRequestDTO.getNumero(), response.getNumero());
        assertEquals(telefoneRequestDTO.getPrincipal(), response.getPrincipal());
    }
}