package com.desafio.squad.dto.request;

import com.desafio.squad.annotation.IE;
import com.desafio.squad.annotation.RG;
import com.desafio.squad.enums.SituacaoEnum;
import com.desafio.squad.enums.TipoPessoaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;

import static com.desafio.squad.enums.TipoPessoaEnum.PESSOA_JURIDICA;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClienteRequestDTO {

    @NotBlank
    private String nome;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataCadastro;

    @CPF(groups = ClienteRequestDTO.PessoaFisica.class)
    @CNPJ(groups = ClienteRequestDTO.PessoaJuridica.class)
    private String numeroDocumento;

    @IE(groups = ClienteRequestDTO.PessoaJuridica.class)
    @RG(groups = ClienteRequestDTO.PessoaFisica.class)
    private String numeroRegistro;

    @Enumerated(EnumType.STRING)
    private SituacaoEnum situacao = SituacaoEnum.ATIVO;

    @Enumerated(EnumType.STRING)
    private TipoPessoaEnum tipoPessoaEnum;

    private List<TelefoneRequestDTO> telefones;

    public Object getInterface() {
        return PESSOA_JURIDICA.equals(tipoPessoaEnum) ?
                PessoaJuridica.class
                : PessoaFisica.class;
    }

    public interface PessoaJuridica {
    }

    public interface PessoaFisica {
    }
}
