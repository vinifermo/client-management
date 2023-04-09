package com.desafio.squad.service;

import com.desafio.squad.assembler.ClienteAssembler;
import com.desafio.squad.assembler.TelefoneAssembler;
import com.desafio.squad.dto.request.ClienteRequestDTO;
import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.ClienteResponseDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;
import com.desafio.squad.enums.SituacaoEnum;
import com.desafio.squad.exception.TelefonePrincipalInvalidoException;
import com.desafio.squad.model.Cliente;
import com.desafio.squad.model.Telefone;
import com.desafio.squad.repository.ClienteRepository;
import com.desafio.squad.repository.TelefoneRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import com.desafio.squad.util.ClienteValidator;

import java.time.LocalDateTime;
import java.util.*;

import static com.desafio.squad.enums.TipoPessoaEnum.PESSOA_FISICA;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

public class ClienteServiceImplTest extends AbstractServiceTest {

    @Mock(name = "clienteRepository")
    private ClienteRepository clienteRepository;

    @Mock(name = "telefoneRepository")
    private TelefoneRepository telefoneRepository;

    @Mock(name = "clienteAssembler")
    private ClienteAssembler clienteAssembler;

    @Mock(name = "clienteValidator")
    private ClienteValidator clienteValidatorMock;

    @Mock(name = "telefoneAssembler")
    private TelefoneAssembler telefoneAssembler;

    @Mock(name = "clienteServiceMock")
    private ClienteServiceImpl clienteServiceMock;

    @InjectMocks
    private ClienteServiceImpl clienteServiceImpl;

    @InjectMocks
    private ClienteValidator clienteValidator;

    private Cliente cliente;
    private ClienteResponseDTO clienteResponseDTO;
    private ClienteRequestDTO clienteRequestDTO;


    @Before
    public void setUp() {
        super.setUp();
        this.clienteServiceImpl = new ClienteServiceImpl(clienteRepository, clienteAssembler, telefoneAssembler, telefoneRepository, clienteValidator);
        this.registerService(this.clienteServiceImpl);

        cliente = new Cliente(UUID.randomUUID(), "João da Silva", LocalDateTime.now(), "667.217.700-08", "25.955.736-5", SituacaoEnum.ATIVO, PESSOA_FISICA,
                List.of(new Telefone("321-1234", true, cliente)));

        clienteRequestDTO = new ClienteRequestDTO("João da Silva", LocalDateTime.now(), "667.217.700-08", "25.955.736-5", SituacaoEnum.ATIVO, PESSOA_FISICA,
                List.of(new TelefoneRequestDTO("321-1234", true)));

        clienteResponseDTO = new ClienteResponseDTO("João da Silva", LocalDateTime.now(), SituacaoEnum.ATIVO, PESSOA_FISICA, "667.217.700-08", "25.955.736-5",
                List.of(new TelefoneResponseDTO(UUID.randomUUID(), "321-1234", true)));

    }

    @Test
    public void deveRetornarUmaListaQuandoListarClientes() {
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));
        when(clienteAssembler.toCollectionModel(List.of(cliente))).thenReturn(List.of(clienteResponseDTO));

        List<ClienteResponseDTO> resultado = clienteServiceImpl.listar();

        verify(clienteRepository, times(1)).findAll();
        verifyNoMoreInteractions(clienteRepository);

        verify(clienteAssembler, times(1)).toCollectionModel(List.of(cliente));
        verifyNoMoreInteractions(clienteAssembler);

        assertEquals(List.of(clienteResponseDTO), resultado);
    }

    @Test
    public void deveRetornarUmaListaVaziaQuandoListarClientes() {
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());
        when(clienteAssembler.toCollectionModel(Collections.emptyList())).thenReturn(Collections.emptyList());

        List<ClienteResponseDTO> resultado = clienteServiceImpl.listar();

        verify(clienteRepository, times(1)).findAll();
        verifyNoMoreInteractions(clienteRepository);

        verify(clienteAssembler, times(1)).toCollectionModel(Collections.emptyList());
        verifyNoMoreInteractions(clienteAssembler);

        assertEquals(Collections.emptyList(), resultado);
    }

    @Test
    public void deveRetornarUmClienteQuandoBuscarPorId() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        when(clienteAssembler.toModel(cliente)).thenReturn(clienteResponseDTO);

        ClienteResponseDTO responseDTO = clienteServiceImpl.clientePorId(id);

        verify(clienteRepository, times(1)).findById(id);

        assertEquals(cliente.getNome(), responseDTO.getNome());
        assertEquals(cliente.getSituacao(), responseDTO.getSituacao());
        assertEquals(cliente.getTipoPessoaEnum(), responseDTO.getTipoPessoa());
        assertEquals(cliente.getNumeroDocumento(), responseDTO.getNumeroDocumento());
        assertEquals(cliente.getNumeroRegistro(), responseDTO.getNumeroRegistro());
        assertEquals(cliente.getTelefones().size(), responseDTO.getTelefones().size());
        assertEquals(cliente.getTelefones().get(0).getNumero(), responseDTO.getTelefones().get(0).getNumero());
        assertEquals(cliente.getTelefones().get(0).getPrincipal(), responseDTO.getTelefones().get(0).getPrincipal());
    }

    @Test
    public void deveRetornarEntityNotFoundQuandoBuscarUmClienteInexistente() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findById(id)).thenThrow(jakarta.persistence.EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> {
            clienteRepository.findById(id);
        });
    }

    @Test
    public void deveRetornarUmClienteCriado() {
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        clienteValidator.validarClienteDuplicado(cliente);

        Cliente response = clienteRepository.save(cliente);


        verifyNoMoreInteractions(clienteAssembler);
        verify(clienteRepository, times(1)).save(cliente);
        verifyNoMoreInteractions(clienteRepository);

        assertEquals(clienteRequestDTO.getNome(), response.getNome());
        assertEquals(clienteRequestDTO.getSituacao(), response.getSituacao());
        assertEquals(clienteRequestDTO.getTipoPessoaEnum(), response.getTipoPessoaEnum());
        assertEquals(clienteRequestDTO.getNumeroDocumento(), response.getNumeroDocumento());
        assertEquals(clienteRequestDTO.getNumeroRegistro(), response.getNumeroRegistro());
        assertEquals(clienteRequestDTO.getTelefones().size(), response.getTelefones().size());
        assertEquals(clienteRequestDTO.getTelefones().get(0).getNumero(), response.getTelefones().get(0).getNumero());
        assertEquals(clienteRequestDTO.getTelefones().get(0).getPrincipal(), response.getTelefones().get(0).getPrincipal());
    }

    @Test
    public void deveRetornarBadRequestQuandoValidarClienteSoPodeTerUmTelefonePrincipal() {
        List<TelefoneRequestDTO> telefones = new ArrayList<>();
        telefones.add(new TelefoneRequestDTO("123", true));
        telefones.add(new TelefoneRequestDTO("456", true));
        telefones.add(new TelefoneRequestDTO("789", false));
        clienteRequestDTO.setTelefones(telefones);

        assertThrows(TelefonePrincipalInvalidoException.class,
                () -> clienteServiceImpl.criar(clienteRequestDTO));
    }

    @Test
    public void deveRetornarUmClienteAtualizado() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));
        clienteServiceImpl.clientePorId(id);
        when(clienteRepository.save(cliente)).thenReturn(cliente);
        clienteValidator.validarClienteDuplicado(cliente);

        Cliente response = clienteRepository.save(cliente);

        verify(clienteRepository, times(1)).save(cliente);
        telefoneAssembler.toEntity(clienteRequestDTO.getTelefones(), cliente);
        verify(telefoneAssembler, times(1)).toEntity(clienteRequestDTO.getTelefones(), cliente);

        assertEquals(clienteRequestDTO.getNome(), response.getNome());
        assertEquals(clienteRequestDTO.getSituacao(), response.getSituacao());
        assertEquals(clienteRequestDTO.getTipoPessoaEnum(), response.getTipoPessoaEnum());
        assertEquals(clienteRequestDTO.getNumeroDocumento(), response.getNumeroDocumento());
        assertEquals(clienteRequestDTO.getNumeroRegistro(), response.getNumeroRegistro());
        assertEquals(clienteRequestDTO.getTelefones().size(), response.getTelefones().size());
        assertEquals(clienteRequestDTO.getTelefones().get(0).getNumero(), response.getTelefones().get(0).getNumero());
        assertEquals(clienteRequestDTO.getTelefones().get(0).getPrincipal(), response.getTelefones().get(0).getPrincipal());
    }

    @Test
    public void deletePorId() {
        UUID id = UUID.randomUUID();
        when(clienteRepository.findById(id)).thenReturn(Optional.of(cliente));

        clienteServiceImpl.deletePorId(id);

        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    public void findByPage() {
        String filter = "nome";
        Pageable pageable = PageRequest.of(0, 2);

        PageImpl<ClienteResponseDTO> clienteResponseDTOS = new PageImpl<>(List.of(clienteResponseDTO), pageable, 2L);
        when(clienteRepository.findByPage(filter, pageable)).thenReturn(clienteResponseDTOS);

        Page<ClienteResponseDTO> response = clienteServiceImpl.findByPage(filter, pageable);

        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getContent().size());
        assertEquals("João da Silva", response.getContent().get(0).getNome());

        verify(clienteRepository, times(1)).findByPage(filter, pageable);
        verifyNoMoreInteractions(clienteRepository);
    }
}