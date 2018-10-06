package nc.si2p.peche.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import nc.si2p.peche.domain.enumeration.DCPEtat;
import nc.si2p.peche.service.mapper.DCPEtatDeserializer;

/**
 * A DCP.
 */
@Entity
@Table(name = "dcp")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "dcp")
public class DCP implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "position")
    private String position;

    @Column(name = "lat")
    private String lat;

    @Column(name = "lng")
    private String lng;

    @Column(name = "date_derniere_maj")
    private LocalDate dateDerniereMaj;

    @Enumerated(EnumType.STRING)
    @JsonDeserialize(using = DCPEtatDeserializer.class)
    @Column(name = "etat")
    private DCPEtat etat;

    @Column(name = "localisation")
    private String localisation;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public DCP nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPosition() {
        return position;
    }

    public DCP position(String position) {
        this.position = position;
        return this;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLat() {
        return lat;
    }

    public DCP lat(String lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public DCP lng(String lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public LocalDate getDateDerniereMaj() {
        return dateDerniereMaj;
    }

    public DCP dateDerniereMaj(LocalDate dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
        return this;
    }

    public void setDateDerniereMaj(LocalDate dateDerniereMaj) {
        this.dateDerniereMaj = dateDerniereMaj;
    }

    public DCPEtat getEtat() {
        return etat;
    }

    public DCP etat(DCPEtat etat) {
        this.etat = etat;
        return this;
    }

    public void setEtat(DCPEtat etat) {
        this.etat = etat;
    }

    public String getLocalisation() {
        return localisation;
    }

    public DCP localisation(String localisation) {
        this.localisation = localisation;
        return this;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DCP dCP = (DCP) o;
        if (dCP.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dCP.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DCP{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", position='" + getPosition() + "'" +
            ", lat='" + getLat() + "'" +
            ", lng='" + getLng() + "'" +
            ", dateDerniereMaj='" + getDateDerniereMaj() + "'" +
            ", etat='" + getEtat() + "'" +
            ", localisation='" + getLocalisation() + "'" +
            "}";
    }
}
