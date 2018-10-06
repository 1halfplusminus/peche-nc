package nc.si2p.peche.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import nc.si2p.peche.domain.enumeration.PSStatus;

/**
 * A PsMarina.
 */
@Entity
@Table(name = "ps_marina")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "psmarina")
public class PsMarina implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lng")
    private Float lng;

    @Column(name = "id_marina")
    private String idMarina;

    @Column(name = "jhi_access")
    private String access;

    @Column(name = "amenagee")
    private Boolean amenagee;

    @Column(name = "commune")
    private String commune;

    @Column(name = "lieu_dit")
    private String lieuDit;

    @Column(name = "parking_place")
    private Integer parkingPlace;

    @Column(name = "observation")
    private String observation;

    @Column(name = "parking")
    private Boolean parking;

    @Column(name = "payant")
    private Boolean payant;

    @Column(name = "point_eau")
    private Boolean pointEau;

    @Column(name = "poubelles")
    private Boolean poubelles;

    @Column(name = "toilettes")
    private Boolean toilettes;

    @Column(name = "titre")
    private String titre;

    @Lob
    @Column(name = "photos")
    private byte[] photos;

    @Column(name = "photos_content_type")
    private String photosContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PSStatus status;

    @OneToMany(mappedBy = "marinas")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PsMiseAEau> miseAEaux = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getLat() {
        return lat;
    }

    public PsMarina lat(Float lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public PsMarina lng(Float lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getIdMarina() {
        return idMarina;
    }

    public PsMarina idMarina(String idMarina) {
        this.idMarina = idMarina;
        return this;
    }

    public void setIdMarina(String idMarina) {
        this.idMarina = idMarina;
    }

    public String getAccess() {
        return access;
    }

    public PsMarina access(String access) {
        this.access = access;
        return this;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Boolean isAmenagee() {
        return amenagee;
    }

    public PsMarina amenagee(Boolean amenagee) {
        this.amenagee = amenagee;
        return this;
    }

    public void setAmenagee(Boolean amenagee) {
        this.amenagee = amenagee;
    }

    public String getCommune() {
        return commune;
    }

    public PsMarina commune(String commune) {
        this.commune = commune;
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getLieuDit() {
        return lieuDit;
    }

    public PsMarina lieuDit(String lieuDit) {
        this.lieuDit = lieuDit;
        return this;
    }

    public void setLieuDit(String lieuDit) {
        this.lieuDit = lieuDit;
    }

    public Integer getParkingPlace() {
        return parkingPlace;
    }

    public PsMarina parkingPlace(Integer parkingPlace) {
        this.parkingPlace = parkingPlace;
        return this;
    }

    public void setParkingPlace(Integer parkingPlace) {
        this.parkingPlace = parkingPlace;
    }

    public String getObservation() {
        return observation;
    }

    public PsMarina observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Boolean isParking() {
        return parking;
    }

    public PsMarina parking(Boolean parking) {
        this.parking = parking;
        return this;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean isPayant() {
        return payant;
    }

    public PsMarina payant(Boolean payant) {
        this.payant = payant;
        return this;
    }

    public void setPayant(Boolean payant) {
        this.payant = payant;
    }

    public Boolean isPointEau() {
        return pointEau;
    }

    public PsMarina pointEau(Boolean pointEau) {
        this.pointEau = pointEau;
        return this;
    }

    public void setPointEau(Boolean pointEau) {
        this.pointEau = pointEau;
    }

    public Boolean isPoubelles() {
        return poubelles;
    }

    public PsMarina poubelles(Boolean poubelles) {
        this.poubelles = poubelles;
        return this;
    }

    public void setPoubelles(Boolean poubelles) {
        this.poubelles = poubelles;
    }

    public Boolean isToilettes() {
        return toilettes;
    }

    public PsMarina toilettes(Boolean toilettes) {
        this.toilettes = toilettes;
        return this;
    }

    public void setToilettes(Boolean toilettes) {
        this.toilettes = toilettes;
    }

    public String getTitre() {
        return titre;
    }

    public PsMarina titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public byte[] getPhotos() {
        return photos;
    }

    public PsMarina photos(byte[] photos) {
        this.photos = photos;
        return this;
    }

    public void setPhotos(byte[] photos) {
        this.photos = photos;
    }

    public String getPhotosContentType() {
        return photosContentType;
    }

    public PsMarina photosContentType(String photosContentType) {
        this.photosContentType = photosContentType;
        return this;
    }

    public void setPhotosContentType(String photosContentType) {
        this.photosContentType = photosContentType;
    }

    public PSStatus getStatus() {
        return status;
    }

    public PsMarina status(PSStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PSStatus status) {
        this.status = status;
    }

    public Set<PsMiseAEau> getMiseAEaux() {
        return miseAEaux;
    }

    public PsMarina miseAEaux(Set<PsMiseAEau> psMiseAEaus) {
        this.miseAEaux = psMiseAEaus;
        return this;
    }

    public PsMarina addMiseAEaux(PsMiseAEau psMiseAEau) {
        this.miseAEaux.add(psMiseAEau);
        psMiseAEau.setMarinas(this);
        return this;
    }

    public PsMarina removeMiseAEaux(PsMiseAEau psMiseAEau) {
        this.miseAEaux.remove(psMiseAEau);
        psMiseAEau.setMarinas(null);
        return this;
    }

    public void setMiseAEaux(Set<PsMiseAEau> psMiseAEaus) {
        this.miseAEaux = psMiseAEaus;
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
        PsMarina psMarina = (PsMarina) o;
        if (psMarina.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), psMarina.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PsMarina{" +
            "id=" + getId() +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", idMarina='" + getIdMarina() + "'" +
            ", access='" + getAccess() + "'" +
            ", amenagee='" + isAmenagee() + "'" +
            ", commune='" + getCommune() + "'" +
            ", lieuDit='" + getLieuDit() + "'" +
            ", parkingPlace=" + getParkingPlace() +
            ", observation='" + getObservation() + "'" +
            ", parking='" + isParking() + "'" +
            ", payant='" + isPayant() + "'" +
            ", pointEau='" + isPointEau() + "'" +
            ", poubelles='" + isPoubelles() + "'" +
            ", toilettes='" + isToilettes() + "'" +
            ", titre='" + getTitre() + "'" +
            ", photos='" + getPhotos() + "'" +
            ", photosContentType='" + getPhotosContentType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
