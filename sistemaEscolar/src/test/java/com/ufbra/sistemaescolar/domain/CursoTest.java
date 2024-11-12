package com.ufbra.sistemaescolar.domain;

import static com.ufbra.sistemaescolar.domain.CursoTestSamples.*;
import static com.ufbra.sistemaescolar.domain.DisciplinaTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ufbra.sistemaescolar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class CursoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Curso.class);
        Curso curso1 = getCursoSample1();
        Curso curso2 = new Curso();
        assertThat(curso1).isNotEqualTo(curso2);

        curso2.setId(curso1.getId());
        assertThat(curso1).isEqualTo(curso2);

        curso2 = getCursoSample2();
        assertThat(curso1).isNotEqualTo(curso2);
    }

    @Test
    void disciplinasTest() {
        Curso curso = getCursoRandomSampleGenerator();
        Disciplina disciplinaBack = getDisciplinaRandomSampleGenerator();

        curso.addDisciplinas(disciplinaBack);
        assertThat(curso.getDisciplinas()).containsOnly(disciplinaBack);

        curso.removeDisciplinas(disciplinaBack);
        assertThat(curso.getDisciplinas()).doesNotContain(disciplinaBack);

        curso.disciplinas(new HashSet<>(Set.of(disciplinaBack)));
        assertThat(curso.getDisciplinas()).containsOnly(disciplinaBack);

        curso.setDisciplinas(new HashSet<>());
        assertThat(curso.getDisciplinas()).doesNotContain(disciplinaBack);
    }
}
