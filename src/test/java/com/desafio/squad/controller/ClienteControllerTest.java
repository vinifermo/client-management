package com.desafio.squad.controller;

import com.desafio.squad.dto.request.ClienteRequestDTO;
import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.ClienteResponseDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;
import com.desafio.squad.enums.SituacaoEnum;
import com.desafio.squad.model.Cliente;
import com.desafio.squad.model.Telefone;
import com.desafio.squad.service.ClienteService;
import com.desafio.squad.util.FilterPageable;
import com.desafio.squad.util.ResourceUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.SmartValidator;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static com.desafio.squad.enums.TipoPessoaEnum.PESSOA_FISICA;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ClienteControllerTest extends AbstractControllerTest {

    private static final String URI = "/clientes";
    private static final String PAGE = URI + "/pageable";

    @Mock(name = "clienteService")
    private ClienteService clienteServiceMock;

    @Mock(name = "smartValidator")
    private SmartValidator smartValidator;

    @InjectMocks
    private ClienteController clienteController;

    private Cliente cliente;
    private ClienteResponseDTO clienteResponseDTO;
    private ClienteRequestDTO clienteRequestDTO;
    private String clienteJson;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        this.clienteController = new ClienteController(clienteServiceMock, smartValidator);
        this.registerController(this.clienteController);

        cliente = new Cliente(UUID.randomUUID(), "João da Silva", LocalDateTime.now(), "667.217.700-08", "25.955.736-5", SituacaoEnum.ATIVO, PESSOA_FISICA,
                List.of(new Telefone("123", true, cliente)));

        clienteRequestDTO = new ClienteRequestDTO("João da Silva", LocalDateTime.now(), "667.217.700-08", "25.955.736-5", SituacaoEnum.ATIVO, PESSOA_FISICA,
                List.of(new TelefoneRequestDTO("123", true)));

        clienteResponseDTO = new ClienteResponseDTO("João da Silva", LocalDateTime.now(), SituacaoEnum.ATIVO, PESSOA_FISICA, "667.217.700-08", "25.955.736-5",
                List.of(new TelefoneResponseDTO(UUID.randomUUID(), "321-1234", true)));

        clienteJson = ResourceUtils.getContentFromResource("/json/criar-cliente.json");
    }

    @Test
    public void deveRetornarOkQuandoBuscarClientePorId() throws Exception {
        String uri = String.format("%s/%s", URI, "9de5354e-17fc-4cf8-ab1a-17d0a15ac57e");

        when(clienteServiceMock.clientePorId(any(UUID.class))).thenReturn(clienteResponseDTO);

        mockMvc.perform(get(uri))
                .andExpect(jsonPath("$.nome", is("João da Silva")))
                .andExpect(jsonPath("$.numeroDocumento", is("667.217.700-08")))
                .andExpect(jsonPath("$.numeroRegistro", is("25.955.736-5")))
                .andExpect(jsonPath("$.tipoPessoa", is("PESSOA_FISICA")))
                .andExpect(jsonPath("$.telefones", hasSize(1)))
                .andExpect(jsonPath("$.telefones[0].numero", is("321-1234")))
                .andExpect(jsonPath("$.telefones[0].principal", is(true)))
                .andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).clientePorId(any(UUID.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void deveRetornarOkQuandoPesquisarPorPagina() throws Exception {
        List<ClienteResponseDTO> clientes = Arrays.asList(
                new ClienteResponseDTO("João da Silva", LocalDateTime.now(), SituacaoEnum.ATIVO, PESSOA_FISICA, "667.217.700-08", "25.955.736-5",
                        List.of(new TelefoneResponseDTO(UUID.randomUUID(), "123", true))));

        FilterPageable filterPageable = new FilterPageable(0, 2, "nome", "ASC");
        Pageable pageable = filterPageable.listByPage();
        Page<ClienteResponseDTO> clientePage = new PageImpl<>(clientes, pageable, 3);

        when(clienteServiceMock.findByPage(anyString(), any(Pageable.class))).thenReturn(clientePage);

        mockMvc.perform(get(PAGE)
                        .contentType(contentType)
                        .param("direction", "ASC"))
                .andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).findByPage(anyString(), any(Pageable.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void deveRetornarOkQuandoListarClientes() throws Exception {
        when(clienteServiceMock.listar()).thenReturn(List.of(clienteResponseDTO));
        mockMvc.perform(get(URI))
                .andExpect(status().isOk());

        verify(clienteServiceMock, times(1)).listar();
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void deveRetornarCreatedQuandoCriarUmCliente() throws Exception {
        String payload = clienteJson
                .replace("{{nome}}", "João da Silva")
                .replace("{{situacao}}", "ATIVO")
                .replace("{{numeroDocumento}}", "333.798.700-13")
                .replace("{{numeroRegistro}}", "38.974.487-6")
                .replace("{{tipoPessoaEnum}}", "PESSOA_FISICA")
                .replace("{{numero}}", "321-1234")
                .replace("{{principal}}", "true");

        when(clienteServiceMock.criar(any(ClienteRequestDTO.class))).thenReturn(cliente);

        mockMvc.perform(post(URI)
                        .contentType(contentType)
                        .content(payload))
                .andExpect(status().isCreated());

        verify(clienteServiceMock, times(1)).criar(any(ClienteRequestDTO.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void deveRetornarNoContentQuandoAtualizarUmCliente() throws Exception {
        String uri = String.format("%s/%s", URI, "9de5354e-17fc-4cf8-ab1a-17d0a15ac57e");

        String payload = clienteJson
                .replace("{{nome}}", "Vinicius")
                .replace("{{situacao}}", "ATIVO")
                .replace("{{numeroDocumento}}", "333.798.700-13")
                .replace("{{numeroRegistro}}", "38.974.487-6")
                .replace("{{tipoPessoaEnum}}", "PESSOA_FISICA")
                .replace("{{numero}}", "123-4567")
                .replace("{{principal}}", "true");

        mockMvc.perform(put(uri)
                        .contentType(contentType)
                        .content(payload))
                .andExpect(status().isNoContent());

        verify(clienteServiceMock, times(1)).atualizar(any(UUID.class), any(ClienteRequestDTO.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void deveRetornarNoContentQuandoDeletarUmCliente() throws Exception {
        String uri = String.format("%s/%s", URI, "9de5354e-17fc-4cf8-ab1a-17d0a15ac57e");

        mockMvc.perform(delete(uri))
                .andExpect(status().isNoContent());

        verify(clienteServiceMock, times(1)).deletePorId(any(UUID.class));
        verifyNoMoreInteractions(clienteServiceMock);
    }

    @Test
    public void deveRetornarBadRequestQuandoDeletarUmClienteInexistente() throws Exception {
        String uri = String.format("%s/%s", URI, "433c-8971-4a3913762e7e");

        mockMvc.perform(delete(uri))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deveRetornarBadRequestQuandoPassarUmIdInvalido() throws Exception {
        String uri = String.format("%s/%s", URI, "433c-8971-4a3913762e7e");

        mockMvc.perform(get(uri))
                .andExpect(status().isBadRequest());
    }

}