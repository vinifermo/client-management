package com.desafio.squad.controller;

import com.desafio.squad.dto.request.TelefoneRequestDTO;
import com.desafio.squad.dto.response.TelefoneResponseDTO;
import com.desafio.squad.service.TelefoneServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/telefones")
@RequiredArgsConstructor
public class TelefoneController {

    private final TelefoneServiceImpl telefoneServiceImpl;

    @GetMapping("/{id}")
    @ApiOperation("Buscar telefone por id")
    public ResponseEntity<TelefoneResponseDTO> telefonePorId(@PathVariable UUID id) {
        return ResponseEntity.ok(telefoneServiceImpl.telefonePorId(id));
    }

    @PutMapping("/{id}")
    @ApiOperation("Atualizar telefone por id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void atualizar(@PathVariable UUID id, @RequestBody TelefoneRequestDTO telefoneRequestDTO) {
        telefoneServiceImpl.atualizar(id, telefoneRequestDTO);
    }
}
