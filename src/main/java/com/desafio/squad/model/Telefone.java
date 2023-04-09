package com.desafio.squad.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_telefone")
public class Telefone {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @NotBlank
    private String numero;

    @NotNull
    private Boolean principal;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public Telefone(String numero, Boolean principal, Cliente cliente) {
        this.numero = numero;
        this.principal = principal;
        this.cliente = cliente;
    }
}
