package com.ufbra.sistemaescolar.web.rest;

import com.ufbra.sistemaescolar.domain.Curso;
import com.ufbra.sistemaescolar.repository.CursoRepository;
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
 * REST controller for managing {@link com.ufbra.sistemaescolar.domain.Curso}.
 */
@RestController
@RequestMapping("/api/cursos")
@Transactional
public class CursoResource {

    private static final Logger LOG = LoggerFactory.getLogger(CursoResource.class);

    private static final String ENTITY_NAME = "curso";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CursoRepository cursoRepository;

    public CursoResource(CursoRepository cursoRepository) {
        this.cursoRepository = cursoRepository;
    }

    /**
     * {@code POST  /cursos} : Create a new curso.
     *
     * @param curso the curso to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new curso, or with status {@code 400 (Bad Request)} if the curso has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<Curso> createCurso(@Valid @RequestBody Curso curso) throws URISyntaxException {
        LOG.debug("REST request to save Curso : {}", curso);
        if (curso.getId() != null) {
            throw new BadRequestAlertException("A new curso cannot already have an ID", ENTITY_NAME, "idexists");
        }
        curso = cursoRepository.save(curso);
        return ResponseEntity.created(new URI("/api/cursos/" + curso.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, curso.getId().toString()))
            .body(curso);
    }

    /**
     * {@code PUT  /cursos/:id} : Updates an existing curso.
     *
     * @param id the id of the curso to save.
     * @param curso the curso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated curso,
     * or with status {@code 400 (Bad Request)} if the curso is not valid,
     * or with status {@code 500 (Internal Server Error)} if the curso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<Curso> updateCurso(@PathVariable(value = "id", required = false) final Long id, @Valid @RequestBody Curso curso)
        throws URISyntaxException {
        LOG.debug("REST request to update Curso : {}, {}", id, curso);
        if (curso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, curso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        curso = cursoRepository.save(curso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curso.getId().toString()))
            .body(curso);
    }

    /**
     * {@code PATCH  /cursos/:id} : Partial updates given fields of an existing curso, field will ignore if it is null
     *
     * @param id the id of the curso to save.
     * @param curso the curso to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated curso,
     * or with status {@code 400 (Bad Request)} if the curso is not valid,
     * or with status {@code 404 (Not Found)} if the curso is not found,
     * or with status {@code 500 (Internal Server Error)} if the curso couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<Curso> partialUpdateCurso(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody Curso curso
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Curso partially : {}, {}", id, curso);
        if (curso.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, curso.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!cursoRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Curso> result = cursoRepository
            .findById(curso.getId())
            .map(existingCurso -> {
                if (curso.getNome() != null) {
                    existingCurso.setNome(curso.getNome());
                }

                return existingCurso;
            })
            .map(cursoRepository::save);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, curso.getId().toString())
        );
    }

    /**
     * {@code GET  /cursos} : get all the cursos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cursos in body.
     */
    @GetMapping("")
    public List<Curso> getAllCursos(@RequestParam(name = "eagerload", required = false, defaultValue = "true") boolean eagerload) {
        LOG.debug("REST request to get all Cursos");
        if (eagerload) {
            return cursoRepository.findAllWithEagerRelationships();
        } else {
            return cursoRepository.findAll();
        }
    }

    /**
     * {@code GET  /cursos/:id} : get the "id" curso.
     *
     * @param id the id of the curso to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the curso, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<Curso> getCurso(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Curso : {}", id);
        Optional<Curso> curso = cursoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(curso);
    }

    /**
     * {@code DELETE  /cursos/:id} : delete the "id" curso.
     *
     * @param id the id of the curso to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCurso(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Curso : {}", id);
        cursoRepository.deleteById(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
