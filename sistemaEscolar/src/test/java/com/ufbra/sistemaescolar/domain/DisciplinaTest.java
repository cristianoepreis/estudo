package com.ufbra.sistemaescolar.domain;

import static com.ufbra.sistemaescolar.domain.CursoTestSamples.*;
import static com.ufbra.sistemaescolar.domain.DisciplinaTestSamples.*;
import static com.ufbra.sistemaescolar.domain.ProfessorTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ufbra.sistemaescolar.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class DisciplinaTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Disciplina.class);
        Disciplina disciplina1 = getDisciplinaSample1();
        Disciplina disciplina2 = new Disciplina();
        assertThat(disciplina1).isNotEqualTo(disciplina2);

        disciplina2.setId(disciplina1.getId());
        assertThat(disciplina1).isEqualTo(disciplina2);

        disciplina2 = getDisciplinaSample2();
        assertThat(disciplina1).isNotEqualTo(disciplina2);
    }

    @Test
    void professorResponsavelTest() {
        Disciplina disciplina = getDisciplinaRandomSampleGenerator();
        Professor professorBack = getProfessorRandomSampleGenerator();

        disciplina.setProfessorResponsavel(professorBack);
        assertThat(disciplina.getProfessorResponsavel()).isEqualTo(professorBack);

        disciplina.professorResponsavel(null);
        assertThat(disciplina.getProfessorResponsavel()).isNull();
    }

    @Test
    void cursoTest() {
        Disciplina disciplina = getDisciplinaRandomSampleGenerator();
        Curso cursoBack = getCursoRandomSampleGenerator();

        disciplina.addCurso(cursoBack);
        assertThat(disciplina.getCursos()).containsOnly(cursoBack);
        assertThat(cursoBack.getDisciplinas()).containsOnly(disciplina);

        disciplina.removeCurso(cursoBack);
        assertThat(disciplina.getCursos()).doesNotContain(cursoBack);
        assertThat(cursoBack.getDisciplinas()).doesNotContain(disciplina);

        disciplina.cursos(new HashSet<>(Set.of(cursoBack)));
        assertThat(disciplina.getCursos()).containsOnly(cursoBack);
        assertThat(cursoBack.getDisciplinas()).containsOnly(disciplina);

        disciplina.setCursos(new HashSet<>());
        assertThat(disciplina.getCursos()).doesNotContain(cursoBack);
        assertThat(cursoBack.getDisciplinas()).doesNotContain(disciplina);
    }
}
