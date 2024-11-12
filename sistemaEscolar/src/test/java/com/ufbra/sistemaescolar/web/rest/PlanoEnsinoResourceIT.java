package com.ufbra.sistemaescolar.web.rest;

import static com.ufbra.sistemaescolar.domain.PlanoEnsinoAsserts.*;
import static com.ufbra.sistemaescolar.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufbra.sistemaescolar.IntegrationTest;
import com.ufbra.sistemaescolar.domain.PlanoEnsino;
import com.ufbra.sistemaescolar.repository.PlanoEnsinoRepository;
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
 * Integration tests for the {@link PlanoEnsinoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlanoEnsinoResourceIT {

    private static final String DEFAULT_EMENTA = "AAAAAAAAAA";
    private static final String UPDATED_EMENTA = "BBBBBBBBBB";

    private static final String DEFAULT_BIBLIOGRAFIA_BASICA = "AAAAAAAAAA";
    private static final String UPDATED_BIBLIOGRAFIA_BASICA = "BBBBBBBBBB";

    private static final String DEFAULT_BIBLIOGRAFIA_COMPLEMENTAR = "AAAAAAAAAA";
    private static final String UPDATED_BIBLIOGRAFIA_COMPLEMENTAR = "BBBBBBBBBB";

    private static final String DEFAULT_PRATICA_LABORATORIAL = "AAAAAAAAAA";
    private static final String UPDATED_PRATICA_LABORATORIAL = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/plano-ensinos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlanoEnsinoRepository planoEnsinoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlanoEnsinoMockMvc;

    private PlanoEnsino planoEnsino;

    private PlanoEnsino insertedPlanoEnsino;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoEnsino createEntity() {
        return new PlanoEnsino()
            .ementa(DEFAULT_EMENTA)
            .bibliografiaBasica(DEFAULT_BIBLIOGRAFIA_BASICA)
            .bibliografiaComplementar(DEFAULT_BIBLIOGRAFIA_COMPLEMENTAR)
            .praticaLaboratorial(DEFAULT_PRATICA_LABORATORIAL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlanoEnsino createUpdatedEntity() {
        return new PlanoEnsino()
            .ementa(UPDATED_EMENTA)
            .bibliografiaBasica(UPDATED_BIBLIOGRAFIA_BASICA)
            .bibliografiaComplementar(UPDATED_BIBLIOGRAFIA_COMPLEMENTAR)
            .praticaLaboratorial(UPDATED_PRATICA_LABORATORIAL);
    }

    @BeforeEach
    public void initTest() {
        planoEnsino = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedPlanoEnsino != null) {
            planoEnsinoRepository.delete(insertedPlanoEnsino);
            insertedPlanoEnsino = null;
        }
    }

    @Test
    @Transactional
    void createPlanoEnsino() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PlanoEnsino
        var returnedPlanoEnsino = om.readValue(
            restPlanoEnsinoMockMvc
                .perform(
                    post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoEnsino))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlanoEnsino.class
        );

        // Validate the PlanoEnsino in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlanoEnsinoUpdatableFieldsEquals(returnedPlanoEnsino, getPersistedPlanoEnsino(returnedPlanoEnsino));

        insertedPlanoEnsino = returnedPlanoEnsino;
    }

    @Test
    @Transactional
    void createPlanoEnsinoWithExistingId() throws Exception {
        // Create the PlanoEnsino with an existing ID
        planoEnsino.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlanoEnsinoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoEnsino)))
            .andExpect(status().isBadRequest());

        // Validate the PlanoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkEmentaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        planoEnsino.setEmenta(null);

        // Create the PlanoEnsino, which fails.

        restPlanoEnsinoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoEnsino)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBibliografiaBasicaIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        planoEnsino.setBibliografiaBasica(null);

        // Create the PlanoEnsino, which fails.

        restPlanoEnsinoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoEnsino)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkBibliografiaComplementarIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        planoEnsino.setBibliografiaComplementar(null);

        // Create the PlanoEnsino, which fails.

        restPlanoEnsinoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoEnsino)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPraticaLaboratorialIsRequired() throws Exception {
        long databaseSizeBeforeTest = getRepositoryCount();
        // set the field null
        planoEnsino.setPraticaLaboratorial(null);

        // Create the PlanoEnsino, which fails.

        restPlanoEnsinoMockMvc
            .perform(post(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoEnsino)))
            .andExpect(status().isBadRequest());

        assertSameRepositoryCount(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPlanoEnsinos() throws Exception {
        // Initialize the database
        insertedPlanoEnsino = planoEnsinoRepository.saveAndFlush(planoEnsino);

        // Get all the planoEnsinoList
        restPlanoEnsinoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(planoEnsino.getId().intValue())))
            .andExpect(jsonPath("$.[*].ementa").value(hasItem(DEFAULT_EMENTA)))
            .andExpect(jsonPath("$.[*].bibliografiaBasica").value(hasItem(DEFAULT_BIBLIOGRAFIA_BASICA)))
            .andExpect(jsonPath("$.[*].bibliografiaComplementar").value(hasItem(DEFAULT_BIBLIOGRAFIA_COMPLEMENTAR)))
            .andExpect(jsonPath("$.[*].praticaLaboratorial").value(hasItem(DEFAULT_PRATICA_LABORATORIAL)));
    }

    @Test
    @Transactional
    void getPlanoEnsino() throws Exception {
        // Initialize the database
        insertedPlanoEnsino = planoEnsinoRepository.saveAndFlush(planoEnsino);

        // Get the planoEnsino
        restPlanoEnsinoMockMvc
            .perform(get(ENTITY_API_URL_ID, planoEnsino.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(planoEnsino.getId().intValue()))
            .andExpect(jsonPath("$.ementa").value(DEFAULT_EMENTA))
            .andExpect(jsonPath("$.bibliografiaBasica").value(DEFAULT_BIBLIOGRAFIA_BASICA))
            .andExpect(jsonPath("$.bibliografiaComplementar").value(DEFAULT_BIBLIOGRAFIA_COMPLEMENTAR))
            .andExpect(jsonPath("$.praticaLaboratorial").value(DEFAULT_PRATICA_LABORATORIAL));
    }

    @Test
    @Transactional
    void getNonExistingPlanoEnsino() throws Exception {
        // Get the planoEnsino
        restPlanoEnsinoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlanoEnsino() throws Exception {
        // Initialize the database
        insertedPlanoEnsino = planoEnsinoRepository.saveAndFlush(planoEnsino);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoEnsino
        PlanoEnsino updatedPlanoEnsino = planoEnsinoRepository.findById(planoEnsino.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlanoEnsino are not directly saved in db
        em.detach(updatedPlanoEnsino);
        updatedPlanoEnsino
            .ementa(UPDATED_EMENTA)
            .bibliografiaBasica(UPDATED_BIBLIOGRAFIA_BASICA)
            .bibliografiaComplementar(UPDATED_BIBLIOGRAFIA_COMPLEMENTAR)
            .praticaLaboratorial(UPDATED_PRATICA_LABORATORIAL);

        restPlanoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlanoEnsino.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlanoEnsino))
            )
            .andExpect(status().isOk());

        // Validate the PlanoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlanoEnsinoToMatchAllProperties(updatedPlanoEnsino);
    }

    @Test
    @Transactional
    void putNonExistingPlanoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoEnsino.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, planoEnsino.getId())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoEnsino))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlanoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoEnsino.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoEnsinoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(planoEnsino))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlanoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoEnsino.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoEnsinoMockMvc
            .perform(put(ENTITY_API_URL).with(csrf()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(planoEnsino)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlanoEnsinoWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoEnsino = planoEnsinoRepository.saveAndFlush(planoEnsino);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoEnsino using partial update
        PlanoEnsino partialUpdatedPlanoEnsino = new PlanoEnsino();
        partialUpdatedPlanoEnsino.setId(planoEnsino.getId());

        partialUpdatedPlanoEnsino
            .ementa(UPDATED_EMENTA)
            .bibliografiaBasica(UPDATED_BIBLIOGRAFIA_BASICA)
            .praticaLaboratorial(UPDATED_PRATICA_LABORATORIAL);

        restPlanoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoEnsino.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoEnsino))
            )
            .andExpect(status().isOk());

        // Validate the PlanoEnsino in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoEnsinoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlanoEnsino, planoEnsino),
            getPersistedPlanoEnsino(planoEnsino)
        );
    }

    @Test
    @Transactional
    void fullUpdatePlanoEnsinoWithPatch() throws Exception {
        // Initialize the database
        insertedPlanoEnsino = planoEnsinoRepository.saveAndFlush(planoEnsino);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the planoEnsino using partial update
        PlanoEnsino partialUpdatedPlanoEnsino = new PlanoEnsino();
        partialUpdatedPlanoEnsino.setId(planoEnsino.getId());

        partialUpdatedPlanoEnsino
            .ementa(UPDATED_EMENTA)
            .bibliografiaBasica(UPDATED_BIBLIOGRAFIA_BASICA)
            .bibliografiaComplementar(UPDATED_BIBLIOGRAFIA_COMPLEMENTAR)
            .praticaLaboratorial(UPDATED_PRATICA_LABORATORIAL);

        restPlanoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlanoEnsino.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlanoEnsino))
            )
            .andExpect(status().isOk());

        // Validate the PlanoEnsino in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlanoEnsinoUpdatableFieldsEquals(partialUpdatedPlanoEnsino, getPersistedPlanoEnsino(partialUpdatedPlanoEnsino));
    }

    @Test
    @Transactional
    void patchNonExistingPlanoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoEnsino.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlanoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, planoEnsino.getId())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoEnsino))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlanoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoEnsino.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .with(csrf())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(planoEnsino))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlanoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlanoEnsino() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        planoEnsino.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlanoEnsinoMockMvc
            .perform(
                patch(ENTITY_API_URL).with(csrf()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(planoEnsino))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlanoEnsino in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlanoEnsino() throws Exception {
        // Initialize the database
        insertedPlanoEnsino = planoEnsinoRepository.saveAndFlush(planoEnsino);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the planoEnsino
        restPlanoEnsinoMockMvc
            .perform(delete(ENTITY_API_URL_ID, planoEnsino.getId()).with(csrf()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return planoEnsinoRepository.count();
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

    protected PlanoEnsino getPersistedPlanoEnsino(PlanoEnsino planoEnsino) {
        return planoEnsinoRepository.findById(planoEnsino.getId()).orElseThrow();
    }

    protected void assertPersistedPlanoEnsinoToMatchAllProperties(PlanoEnsino expectedPlanoEnsino) {
        assertPlanoEnsinoAllPropertiesEquals(expectedPlanoEnsino, getPersistedPlanoEnsino(expectedPlanoEnsino));
    }

    protected void assertPersistedPlanoEnsinoToMatchUpdatableProperties(PlanoEnsino expectedPlanoEnsino) {
        assertPlanoEnsinoAllUpdatablePropertiesEquals(expectedPlanoEnsino, getPersistedPlanoEnsino(expectedPlanoEnsino));
    }
}
