package com.desafio.squad.controller;

import com.desafio.squad.dto.request.ClienteRequestDTO;
import com.desafio.squad.dto.response.ClienteResponseDTO;
import com.desafio.squad.exception.TipoDocumentoInvalidoException;
import com.desafio.squad.model.Cliente;
import com.desafio.squad.service.ClienteServiceImpl;
import io.swagger.annotations.ApiOperation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import com.desafio.squad.util.FilterPageable;

import java.lang.reflect.Method;
import java.net.URI;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteServiceImpl clienteServiceImpl;

    private final SmartValidator validator;

    @PostMapping
    @ApiOperation(value = "Criar um cliente")
    public ResponseEntity<ClienteResponseDTO> criar(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) throws TipoDocumentoInvalidoException {
        extracted(clienteRequestDTO);
        Cliente cliente = clienteServiceImpl.criar(clienteRequestDTO);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(cliente.getId())
                .toUri();
        log.info("Criado novo cliente com id: {}", cliente.getId());

        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation("Atualizar cliente por id")
    public void atualizar(@PathVariable UUID id, @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) throws TipoDocumentoInvalidoException {
        extracted(clienteRequestDTO);
        clienteServiceImpl.atualizar(id, clienteRequestDTO);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("Deletar um cliente por id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPorId(@PathVariable UUID id){
        clienteServiceImpl.deletePorId(id);
    }

    @GetMapping("/{id}")
    @ApiOperation("Busca e retorna cliente por id")
    public ResponseEntity<ClienteResponseDTO> clientePorId(@PathVariable UUID id) {
        return ResponseEntity.ok(clienteServiceImpl.clientePorId(id));
    }

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listar() {
        List<ClienteResponseDTO> body = clienteServiceImpl.listar();
        return ResponseEntity.ok(body);
    }

    @GetMapping("/pageable")
    @ApiOperation("Paginação de todos os clientes")
    public Page<ClienteResponseDTO> findByPage(@RequestParam(value = "filter", defaultValue = "") String filter,
                                               FilterPageable filterPageable) {
        log.info("Busca paginada efetuada");

        return clienteServiceImpl.findByPage(filter.toUpperCase(), filterPageable.listByPage());
    }

    private void extracted(ClienteRequestDTO clienteRequestDTO) throws TipoDocumentoInvalidoException {
        Method currentMethod = new Object() {
        }.getClass().getEnclosingMethod();
        validateModel(clienteRequestDTO, clienteRequestDTO.getInterface(), currentMethod);
    }

    private void validateModel(ClienteRequestDTO clienteRequestDTO, Object validationGroup, Method currentMethod) throws
            TipoDocumentoInvalidoException {
        BeanPropertyBindingResult erros = new BeanPropertyBindingResult(clienteRequestDTO, clienteRequestDTO.getClass().getName());
        validator.validate(clienteRequestDTO, erros, validationGroup);
        if (!erros.getAllErrors().isEmpty()) {
            throw new TipoDocumentoInvalidoException(new MethodParameter(currentMethod, 0), erros);
        }
    }

}

