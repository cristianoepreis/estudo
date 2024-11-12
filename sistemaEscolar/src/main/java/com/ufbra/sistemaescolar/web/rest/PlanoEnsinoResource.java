package com.ufbra.sistemaescolar.web.rest;

import com.ufbra.sistemaescolar.domain.PlanoEnsino;
import com.ufbra.sistemaescolar.repository.PlanoEnsinoRepository;
import com.ufbra.sistemaescolar.web.rest.errors.BadRequestAlertException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.ufbra.sistemaescolar.domain.PlanoEnsino}.
 */
@RestController
@RequestMapping("/api/plano-ensinos")
@Transactional
public class PlanoEnsinoResource {

    private static final Logger LOG = LoggerFactory.getLogger(PlanoEnsinoResource.class);

    private static final String ENTITY_NAME = "planoEnsino";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PlanoEnsinoRepository planoEnsinoRepository;

    public PlanoEnsinoResource(PlanoEnsinoRepository planoEnsinoRepository) {
        this.planoEnsinoRepository = planoEnsinoRepository;
    }

    /**
     * {@code POST  /plano-ensinos} : Create a new planoEnsino.
     *
     * @param planoEnsino the planoEnsino to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new planoEnsino, or with status {@code 400 (Bad Request)} if the planoEnsino has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<PlanoEnsino> createPlanoEnsino(@Valid @RequestBody PlanoEnsino planoEnsino) throws URISyntaxException {
        LOG.debug("REST request to save PlanoEnsino : {}", planoEnsino);
        if (planoEnsino.getId() != null) {
            throw new BadRequestAlertException("A new planoEnsino cannot already have an ID", ENTITY_NAME, "idexists");
        }
        planoEnsino = planoEnsinoRepository.save(planoEnsino);
        return ResponseEntity.created(new URI("/api/plano-ensinos/" + planoEnsino.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, planoEnsino.getId().toString()))
            .body(planoEnsino);
    }

    /**
     * {@code PUT  /plano-ensinos/:id} : Updates an existing planoEnsino.
     *
     * @param id the id of the planoEnsino to save.
     * @param planoEnsino the planoEnsino to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoEnsino,
     * or with status {@code 400 (Bad Request)} if the planoEnsino is not valid,
     * or with status {@code 500 (Internal Server Error)} if the planoEnsino couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<PlanoEnsino> updatePlanoEnsino(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PlanoEnsino planoEnsino
    ) throws URISyntaxException {
        LOG.debug("REST request to update PlanoEnsino : {}, {}", id, planoEnsino);
        if (planoEnsino.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoEnsino.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoEnsinoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        planoEnsino = planoEnsinoRepository.save(planoEnsino);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoEnsino.getId().toString()))
            .body(planoEnsino);
    }

    /**
     * {@code PATCH  /plano-ensinos/:id} : Partial updates given fields of an existing planoEnsino, field will ignore if it is null
     *
     * @param id the id of the planoEnsino to save.
     * @param planoEnsino the planoEnsino to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated planoEnsino,
     * or with status {@code 400 (Bad Request)} if the planoEnsino is not valid,
     * or with status {@code 404 (Not Found)} if the planoEnsino is not found,
     * or with status {@code 500 (Internal Server Error)} if the planoEnsino couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PlanoEnsino> partialUpdatePlanoEnsino(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PlanoEnsino planoEnsino
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update PlanoEnsino partially : {}, {}", id, planoEnsino);
        if (planoEnsino.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, planoEnsino.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!planoEnsinoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PlanoEnsino> result = planoEnsinoRepository
            .findById(planoEnsino.getId())
            .map(existingPlanoEnsino -> {
                if (planoEnsino.getEmenta() != null) {
                    existingPlanoEnsino.setEmenta(planoEnsino.getEmenta());
                }
                if (planoEnsino.getBibliografiaBasica() != null) {
                    existingPlanoEnsino.setBibliografiaBasica(planoEnsino.getBibliografiaBasica());
                }
                if (planoEnsino.getBibliografiaComplementar() != null) {
                    existingPlanoEnsino.setBibliografiaComplementar(planoEnsino.getBibliografiaComplementar());
                }
                if (planoEnsino.getPraticaLaboratorial() != null) {
                    existingPlanoEnsino.setPraticaLaboratorial(planoEnsino.getPraticaLaboratorial());
                }

                return existingPlanoEnsino;
            })
            .map(planoEnsinoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, planoEnsino.getId().toString())
        );
    }

    /**
     * {@code GET  /plano-ensinos} : get all the planoEnsinos.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of planoEnsinos in body.
     */
    @GetMapping("")
    public List<PlanoEnsino> getAllPlanoEnsinos() {
        LOG.debug("REST request to get all PlanoEnsinos");
        return planoEnsinoRepository.findAll();
    }

    /**
     * {@code GET  /plano-ensinos/:id} : get the "id" planoEnsino.
     *
     * @param id the id of the planoEnsino to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the planoEnsino, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<PlanoEnsino> getPlanoEnsino(@PathVariable("id") Long id) {
        LOG.debug("REST request to get PlanoEnsino : {}", id);
        Optional<PlanoEnsino> planoEnsino = planoEnsinoRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(planoEnsino);
    }

    /**
     * {@code DELETE  /plano-ensinos/:id} : delete the "id" planoEnsino.
     *
     * @param id the id of the planoEnsino to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanoEnsino(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete PlanoEnsino : {}", id);
        planoEnsinoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
