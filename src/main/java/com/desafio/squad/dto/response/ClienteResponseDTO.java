package com.desafio.squad.dto.response;

import com.desafio.squad.enums.SituacaoEnum;
import com.desafio.squad.enums.TipoPessoaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteResponseDTO {

    private String nome;
    private LocalDateTime dataCadastro;
    private SituacaoEnum situacao = SituacaoEnum.ATIVO;
    private TipoPessoaEnum tipoPessoa;
    private String numeroDocumento;
    private String numeroRegistro;
    private List<TelefoneResponseDTO> telefones;

    public ClienteResponseDTO(String nome, LocalDateTime dataCadastro, SituacaoEnum situacao, String numeroDocumento, String numeroRegistro) {
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.situacao = situacao;
        this.numeroDocumento = numeroDocumento;
        this.numeroRegistro = numeroRegistro;
    }
}
