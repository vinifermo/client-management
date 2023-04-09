package com.desafio.squad.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TelefoneRequestDTO {

    private String numero;
    private Boolean principal;

}
