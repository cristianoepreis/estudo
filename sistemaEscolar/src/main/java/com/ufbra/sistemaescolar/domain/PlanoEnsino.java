package com.ufbra.sistemaescolar.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;

/**
 * A PlanoEnsino.
 */
@Entity
@Table(name = "plano_ensino")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PlanoEnsino implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 2000)
    @Column(name = "ementa", length = 2000, nullable = false)
    private String ementa;

    @NotNull
    @Size(max = 2000)
    @Column(name = "bibliografia_basica", length = 2000, nullable = false)
    private String bibliografiaBasica;

    @NotNull
    @Size(max = 2000)
    @Column(name = "bibliografia_complementar", length = 2000, nullable = false)
    private String bibliografiaComplementar;

    @NotNull
    @Size(max = 2000)
    @Column(name = "pratica_laboratorial", length = 2000, nullable = false)
    private String praticaLaboratorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "professorResponsavel", "cursos" }, allowSetters = true)
    private Disciplina disciplina;

    @ManyToOne(fetch = FetchType.LAZY)
    private Professor professorResponsavel;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PlanoEnsino id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmenta() {
        return this.ementa;
    }

    public PlanoEnsino ementa(String ementa) {
        this.setEmenta(ementa);
        return this;
    }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }

    public String getBibliografiaBasica() {
        return this.bibliografiaBasica;
    }

    public PlanoEnsino bibliografiaBasica(String bibliografiaBasica) {
        this.setBibliografiaBasica(bibliografiaBasica);
        return this;
    }

    public void setBibliografiaBasica(String bibliografiaBasica) {
        this.bibliografiaBasica = bibliografiaBasica;
    }

    public String getBibliografiaComplementar() {
        return this.bibliografiaComplementar;
    }

    public PlanoEnsino bibliografiaComplementar(String bibliografiaComplementar) {
        this.setBibliografiaComplementar(bibliografiaComplementar);
        return this;
    }

    public void setBibliografiaComplementar(String bibliografiaComplementar) {
        this.bibliografiaComplementar = bibliografiaComplementar;
    }

    public String getPraticaLaboratorial() {
        return this.praticaLaboratorial;
    }

    public PlanoEnsino praticaLaboratorial(String praticaLaboratorial) {
        this.setPraticaLaboratorial(praticaLaboratorial);
        return this;
    }

    public void setPraticaLaboratorial(String praticaLaboratorial) {
        this.praticaLaboratorial = praticaLaboratorial;
    }

    public Disciplina getDisciplina() {
        return this.disciplina;
    }

    public void setDisciplina(Disciplina disciplina) {
        this.disciplina = disciplina;
    }

    public PlanoEnsino disciplina(Disciplina disciplina) {
        this.setDisciplina(disciplina);
        return this;
    }

    public Professor getProfessorResponsavel() {
        return this.professorResponsavel;
    }

    public void setProfessorResponsavel(Professor professor) {
        this.professorResponsavel = professor;
    }

    public PlanoEnsino professorResponsavel(Professor professor) {
        this.setProfessorResponsavel(professor);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PlanoEnsino)) {
            return false;
        }
        return getId() != null && getId().equals(((PlanoEnsino) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PlanoEnsino{" +
            "id=" + getId() +
            ", ementa='" + getEmenta() + "'" +
            ", bibliografiaBasica='" + getBibliografiaBasica() + "'" +
            ", bibliografiaComplementar='" + getBibliografiaComplementar() + "'" +
            ", praticaLaboratorial='" + getPraticaLaboratorial() + "'" +
            "}";
    }
}
