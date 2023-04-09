package com.desafio.squad.model;

import com.desafio.squad.enums.SituacaoEnum;
import com.desafio.squad.enums.TipoPessoaEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_cliente")
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String nome;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dataCadastro;

    private String numeroDocumento;

    private String numeroRegistro;

    @Enumerated(EnumType.STRING)
    private SituacaoEnum situacao = SituacaoEnum.ATIVO;

    @Enumerated(EnumType.STRING)
    private TipoPessoaEnum tipoPessoaEnum;


    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Telefone> telefones;


    public Cliente(String nome, LocalDateTime dataCadastro, SituacaoEnum situacao,
                   TipoPessoaEnum tipoPessoaEnum) {
        this.nome = nome;
        this.dataCadastro = dataCadastro;
        this.situacao = situacao;
        this.tipoPessoaEnum = tipoPessoaEnum;
    }

    @PrePersist
    public void prePersist() {
        this.dataCadastro = LocalDateTime.now();
    }
}
