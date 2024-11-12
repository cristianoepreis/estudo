package com.ufbra.sistemaescolar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Disciplina.
 */
@Entity
@Table(name = "disciplina")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Disciplina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "nome", length = 100, nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    private Professor professorResponsavel;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "disciplinas")
    @JsonIgnoreProperties(value = { "disciplinas" }, allowSetters = true)
    private Set<Curso> cursos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Disciplina id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public Disciplina nome(String nome) {
        this.setNome(nome);
        return this;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Professor getProfessorResponsavel() {
        return this.professorResponsavel;
    }

    public void setProfessorResponsavel(Professor professor) {
        this.professorResponsavel = professor;
    }

    public Disciplina professorResponsavel(Professor professor) {
        this.setProfessorResponsavel(professor);
        return this;
    }

    public Set<Curso> getCursos() {
        return this.cursos;
    }

    public void setCursos(Set<Curso> cursos) {
        if (this.cursos != null) {
            this.cursos.forEach(i -> i.removeDisciplinas(this));
        }
        if (cursos != null) {
            cursos.forEach(i -> i.addDisciplinas(this));
        }
        this.cursos = cursos;
    }

    public Disciplina cursos(Set<Curso> cursos) {
        this.setCursos(cursos);
        return this;
    }

    public Disciplina addCurso(Curso curso) {
        this.cursos.add(curso);
        curso.getDisciplinas().add(this);
        return this;
    }

    public Disciplina removeCurso(Curso curso) {
        this.cursos.remove(curso);
        curso.getDisciplinas().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Disciplina)) {
            return false;
        }
        return getId() != null && getId().equals(((Disciplina) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Disciplina{" +
            "id=" + getId() +
            ", nome='" + getNome() + "'" +
            "}";
    }
}
