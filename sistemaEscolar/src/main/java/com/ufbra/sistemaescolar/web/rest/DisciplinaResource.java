package com.ufbra.sistemaescolar.web.rest;

import com.ufbra.sistemaescolar.domain.Disciplina;
import com.ufbra.sistemaescolar.repository.DisciplinaRepository;
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
 * REST controller for managing {@link com.ufbra.sistemaescolar.domain.Disciplina}.
 */
@RestController
@RequestMapping("/api/disciplinas")
@Transactional
public class DisciplinaResource {

    private static final Logger LOG = LoggerFactory.getLogger(DisciplinaResource.class);

    private static final String ENTITY_NAME = "disciplina";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DisciplinaRepository disciplinaRepository;

    public DisciplinaResource(DisciplinaRepository disciplinaRepository) {
        this.disciplinaRepository = disciplinaRepository;
    }

    /**
     * {@code POST  /disciplinas} : Create a new disciplina.
     *
     * @param disciplina the disciplina to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new disciplina, or with status {@code 400 (Bad Request)} if the disciplina has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Disciplina> createDisciplina(@Valid @RequestBody Disciplina disciplina) throws URISyntaxException {
        LOG.debug("REST request to save Disciplina : {}", disciplina);
        if (disciplina.getId() != null) {
            throw new BadRequestAlertException("A new disciplina cannot already have an ID", ENTITY_NAME, "idexists");
        }
        disciplina = disciplinaRepository.save(disciplina);
        return ResponseEntity.created(new URI("/api/disciplinas/" + disciplina.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, disciplina.getId().toString()))
            .body(disciplina);
    }

    /**
     * {@code PUT  /disciplinas/:id} : Updates an existing disciplina.
     *
     * @param id the id of the disciplina to save.
     * @param disciplina the disciplina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplina,
     * or with status {@code 400 (Bad Request)} if the disciplina is not valid,
     * or with status {@code 500 (Internal Server Error)} if the disciplina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Disciplina> updateDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Disciplina disciplina
    ) throws URISyntaxException {
        LOG.debug("REST request to update Disciplina : {}, {}", id, disciplina);
        if (disciplina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        disciplina = disciplinaRepository.save(disciplina);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplina.getId().toString()))
            .body(disciplina);
    }

    /**
     * {@code PATCH  /disciplinas/:id} : Partial updates given fields of an existing disciplina, field will ignore if it is null
     *
     * @param id the id of the disciplina to save.
     * @param disciplina the disciplina to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated disciplina,
     * or with status {@code 400 (Bad Request)} if the disciplina is not valid,
     * or with status {@code 404 (Not Found)} if the disciplina is not found,
     * or with status {@code 500 (Internal Server Error)} if the disciplina couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Disciplina> partialUpdateDisciplina(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Disciplina disciplina
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Disciplina partially : {}, {}", id, disciplina);
        if (disciplina.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, disciplina.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!disciplinaRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Disciplina> result = disciplinaRepository
            .findById(disciplina.getId())
            .map(existingDisciplina -> {
                if (disciplina.getNome() != null) {
                    existingDisciplina.setNome(disciplina.getNome());
                }

                return existingDisciplina;
            })
            .map(disciplinaRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, disciplina.getId().toString())
        );
    }

    /**
     * {@code GET  /disciplinas} : get all the disciplinas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of disciplinas in body.
     */
    @GetMapping("")
    public List<Disciplina> getAllDisciplinas() {
        LOG.debug("REST request to get all Disciplinas");
        return disciplinaRepository.findAll();
    }

    /**
     * {@code GET  /disciplinas/:id} : get the "id" disciplina.
     *
     * @param id the id of the disciplina to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the disciplina, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Disciplina> getDisciplina(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Disciplina : {}", id);
        Optional<Disciplina> disciplina = disciplinaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(disciplina);
    }

    /**
     * {@code DELETE  /disciplinas/:id} : delete the "id" disciplina.
     *
     * @param id the id of the disciplina to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDisciplina(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Disciplina : {}", id);
        disciplinaRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
