package com.ufbra.sistemaescolar.domain;

import static com.ufbra.sistemaescolar.domain.DisciplinaTestSamples.*;
import static com.ufbra.sistemaescolar.domain.PlanoEnsinoTestSamples.*;
import static com.ufbra.sistemaescolar.domain.ProfessorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ufbra.sistemaescolar.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlanoEnsinoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlanoEnsino.class);
        PlanoEnsino planoEnsino1 = getPlanoEnsinoSample1();
        PlanoEnsino planoEnsino2 = new PlanoEnsino();
        assertThat(planoEnsino1).isNotEqualTo(planoEnsino2);

        planoEnsino2.setId(planoEnsino1.getId());
        assertThat(planoEnsino1).isEqualTo(planoEnsino2);

        planoEnsino2 = getPlanoEnsinoSample2();
        assertThat(planoEnsino1).isNotEqualTo(planoEnsino2);
    }

    @Test
    void disciplinaTest() {
        PlanoEnsino planoEnsino = getPlanoEnsinoRandomSampleGenerator();
        Disciplina disciplinaBack = getDisciplinaRandomSampleGenerator();

        planoEnsino.setDisciplina(disciplinaBack);
        assertThat(planoEnsino.getDisciplina()).isEqualTo(disciplinaBack);

        planoEnsino.disciplina(null);
        assertThat(planoEnsino.getDisciplina()).isNull();
    }

    @Test
    void professorResponsavelTest() {
        PlanoEnsino planoEnsino = getPlanoEnsinoRandomSampleGenerator();
        Professor professorBack = getProfessorRandomSampleGenerator();

        planoEnsino.setProfessorResponsavel(professorBack);
        assertThat(planoEnsino.getProfessorResponsavel()).isEqualTo(professorBack);

        planoEnsino.professorResponsavel(null);
        assertThat(planoEnsino.getProfessorResponsavel()).isNull();
    }
}
