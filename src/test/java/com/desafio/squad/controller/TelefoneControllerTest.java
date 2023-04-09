package com.desafio.squad.controller;

import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;
import com.desafio.squad.enums.SituacaoEnum;
import com.desafio.squad.model.Cliente;
import com.desafio.squad.model.Telefone;
import com.desafio.squad.service.TelefoneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import com.desafio.squad.util.ResourceUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static com.desafio.squad.enums.TipoPessoaEnum.PESSOA_FISICA;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TelefoneControllerTest extends AbstractControllerTest {

    private static final String URI = "/telefones";

    @Mock(name = "telefoneService")
    private TelefoneServiceImpl telefoneServiceImpl;

    @InjectMocks
    private TelefoneController telefoneController;

    private Telefone telefone;
    private TelefoneResponseDTO telefoneResponseDTO;
    private TelefoneRequestDTO telefoneRequestDTO;
    private Cliente cliente;
    private String telefoneJson;

    @BeforeEach
    public void setUp() {
        super.setUp();
        this.telefoneController = new TelefoneController(telefoneServiceImpl);
        this.registerController(this.telefoneController);

        cliente = new Cliente(UUID.randomUUID(),"João da Silva", LocalDateTime.now(), "667.217.700-08", "25.955.736-5", SituacaoEnum.ATIVO, PESSOA_FISICA,
                List.of(new Telefone("123",true,cliente)));

        telefone = new Telefone(UUID.fromString("9de5354e-17fc-4cf8-ab1a-17d0a15ac57e"), "321-1234",true,cliente);
        telefoneRequestDTO = new TelefoneRequestDTO("321-1234",true);
        telefoneResponseDTO = new TelefoneResponseDTO(UUID.fromString("9de5354e-17fc-4cf8-ab1a-17d0a15ac57e"),"321-1234",true);

        telefoneJson = ResourceUtils.getContentFromResource("/json/atualizar-telefone.json");
    }

    @Test
    public void deveRetornar200OkQuandoBuscarTelefoneePorId() throws Exception {
        String uri = String.format("%s/%s", URI, "9de5354e-17fc-4cf8-ab1a-17d0a15ac57e");

        when(telefoneServiceImpl.telefonePorId(any(UUID.class))).thenReturn(telefoneResponseDTO);

        mockMvc.perform(get(uri))
                .andExpect(jsonPath("$.id", is("9de5354e-17fc-4cf8-ab1a-17d0a15ac57e")))
                .andExpect(jsonPath("$.numero", is("321-1234")))
                .andExpect(jsonPath("$.principal", is(true)))
                .andExpect(status().isOk());

        verify(telefoneServiceImpl, times(1)).telefonePorId(any(UUID.class));
        verifyNoMoreInteractions(telefoneServiceImpl);
    }

    @Test
    public void deveAtualizarComSucessoRetornando204() throws Exception {
        String uri = String.format("%s/%s", URI, "9de5354e-17fc-4cf8-ab1a-17d0a15ac57e");

        String payload = telefoneJson
                .replace("{{id}}", "9de5354e-17fc-4cf8-ab1a-17d0a15ac57e")
                .replace("{{numero}}", "321-1234")
                .replace("{{principal}}", "true");

        mockMvc.perform(put(uri)
                        .contentType(contentType)
                        .content(payload))
                .andExpect(status().isNoContent());

        verify(telefoneServiceImpl, times(1)).atualizar(any(UUID.class), any(TelefoneRequestDTO.class));
        verifyNoMoreInteractions(telefoneServiceImpl);
    }
}