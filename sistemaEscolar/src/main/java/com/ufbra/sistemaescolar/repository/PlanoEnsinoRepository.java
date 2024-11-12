package com.ufbra.sistemaescolar.repository;

import com.ufbra.sistemaescolar.domain.PlanoEnsino;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlanoEnsino entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlanoEnsinoRepository extends JpaRepository<PlanoEnsino, Long> {}
