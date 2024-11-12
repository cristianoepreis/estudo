package com.ufbra.sistemaescolar.web.rest;

import static com.ufbra.sistemaescolar.domain.ProfessorAsserts.*;
import static com.ufbra.sistemaescolar.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufbra.sistemaescolar.IntegrationTest;
import com.ufbra.sistemaescolar.domain.Professor;
import com.ufbra.sistemaescolar.repository.ProfessorRepository;
import jakarta.persistence.EntityManager;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ProfessorResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ProfessorResourceIT {

    private static final String DEFAULT_NOME = "AAAAAAAAAA";
    private static final String UPDATED_NOME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/professors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProfessorMockMvc;

    private Professor professor;

    private Professor insertedProfessor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professor createEntity() {
        return new Professor().nome(DEFAULT_NOME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Professor createUpdatedEntity() {
        return new Professor().nome(UPDATED_NOME);
    }

    @BeforeEach
    public void initTest() {
        professor = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedProfessor != null) {
            professorRepository.delete(insertedProfessor);
            insertedProfessor = null;
        }
    }

    @Test
    @Transactional
    void createProfessor() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Professor
        var returnedProfessor = om.readValue(
            restProfessorMockMvc
                .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professor)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Professor.class
        );

        // Validate the Professor in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertProfessorUpdatableFieldsEquals(returnedProfessor, getPersistedProfessor(returnedProfessor));

        insertedProfessor = returnedProfessor;
    }

    @Test
    @Transactional
    void createProfessorWithExistingId() throws Exception {
        // Create the Professor with an existing ID
        professor.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProfessorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professor)))
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkNomeIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        professor.setNome(null);

        // Create the Professor, which fails.

        restProfessorMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professor)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllProfessors() throws Exception {
        // Initialize the database
        insertedProfessor = professorRepository.saveAndFlush(professor);

        // Get all the professorList
        restProfessorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(professor.getId().intValue())))
            .andExpect(jsonPath("$.[*].nome").value(hasItem(DEFAULT_NOME)));
    }

    @Test
    @Transactional
    void getProfessor() throws Exception {
        // Initialize the database
        insertedProfessor = professorRepository.saveAndFlush(professor);

        // Get the professor
        restProfessorMockMvc
            .perform(get(ENTITY_API_URL_ID, professor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(professor.getId().intValue()))
            .andExpect(jsonPath("$.nome").value(DEFAULT_NOME));
    }

    @Test
    @Transactional
    void getNonExistingProfessor() throws Exception {
        // Get the professor
        restProfessorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingProfessor() throws Exception {
        // Initialize the database
        insertedProfessor = professorRepository.saveAndFlush(professor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the professor
        Professor updatedProfessor = professorRepository.findById(professor.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedProfessor are not directly saved in db
        em.detach(updatedProfessor);
        updatedProfessor.nome(UPDATED_NOME);

        restProfessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedProfessor.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedProfessor))
            )
            .andExpect(status().isOk());

        // Validate the Professor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedProfessorToMatchAllProperties(updatedProfessor);
    }

    @Test
    @Transactional
    void putNonExistingProfessor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, professor.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(professor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProfessor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(professor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProfessor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(professor)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Professor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProfessorWithPatch() throws Exception {
        // Initialize the database
        insertedProfessor = professorRepository.saveAndFlush(professor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the professor using partial update
        Professor partialUpdatedProfessor = new Professor();
        partialUpdatedProfessor.setId(professor.getId());

        partialUpdatedProfessor.nome(UPDATED_NOME);

        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfessor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfessor))
            )
            .andExpect(status().isOk());

        // Validate the Professor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfessorUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedProfessor, professor),
            getPersistedProfessor(professor)
        );
    }

    @Test
    @Transactional
    void fullUpdateProfessorWithPatch() throws Exception {
        // Initialize the database
        insertedProfessor = professorRepository.saveAndFlush(professor);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the professor using partial update
        Professor partialUpdatedProfessor = new Professor();
        partialUpdatedProfessor.setId(professor.getId());

        partialUpdatedProfessor.nome(UPDATED_NOME);

        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProfessor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedProfessor))
            )
            .andExpect(status().isOk());

        // Validate the Professor in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertProfessorUpdatableFieldsEquals(partialUpdatedProfessor, getPersistedProfessor(partialUpdatedProfessor));
    }

    @Test
    @Transactional
    void patchNonExistingProfessor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professor.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, professor.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(professor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProfessor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(professor))
            )
            .andExpect(status().isBadRequest());

        // Validate the Professor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProfessor() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        professor.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProfessorMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(professor))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Professor in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProfessor() throws Exception {
        // Initialize the database
        insertedProfessor = professorRepository.saveAndFlush(professor);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the professor
        restProfessorMockMvc
            .perform(delete(ENTITY_API_URL_ID, professor.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return professorRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Professor getPersistedProfessor(Professor professor) {
        return professorRepository.findById(professor.getId()).orElseThrow();
    }

    protected void assertPersistedProfessorToMatchAllProperties(Professor expectedProfessor) {
        assertProfessorAllPropertiesEquals(expectedProfessor, getPersistedProfessor(expectedProfessor));
    }

    protected void assertPersistedProfessorToMatchUpdatableProperties(Professor expectedProfessor) {
        assertProfessorAllUpdatablePropertiesEquals(expectedProfessor, getPersistedProfessor(expectedProfessor));
    }
}
