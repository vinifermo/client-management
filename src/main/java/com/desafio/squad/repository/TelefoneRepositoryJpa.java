package com.desafio.squad.repository;

import com.desafio.squad.model.Telefone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TelefoneRepositoryJpa extends JpaRepository<Telefone, UUID> {
}
