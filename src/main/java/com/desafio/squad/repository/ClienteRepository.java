package com.desafio.squad.repository;

import com.desafio.squad.dto.response.ClienteResponseDTO;
import com.desafio.squad.model.Cliente;
import jakarta.persistence.OrderBy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {

    List<Cliente> findAllByNumeroDocumento(String numeroDocumento);

    @OrderBy("telefones.principal DESC")
    List<Cliente> findAll();

    @Query("SELECT new com.desafio.squad.dto.response.ClienteResponseDTO(c.nome, c.dataCadastro, c.situacao, c.numeroDocumento, c.numeroRegistro) FROM Cliente c " +
            "WHERE UPPER(c.nome) LIKE %:filter% " +
            "OR UPPER(c.numeroDocumento) LIKE %:filter% " +
            "OR UPPER(c.numeroRegistro) LIKE %:filter%")
    Page<ClienteResponseDTO> findByPage(@Param("filter") String filter, Pageable pageable);

}



