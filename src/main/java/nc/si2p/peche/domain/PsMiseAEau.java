package nc.si2p.peche.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import nc.si2p.peche.domain.enumeration.PSStatus;

/**
 * A PsMiseAEau.
 */
@Entity
@Table(name = "ps_mise_a_eau")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "psmiseaeau")
public class PsMiseAEau implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lng")
    private Float lng;

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

    @ManyToOne
    @JsonIgnoreProperties("miseAEaux")
    private PsMarina marinas;

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

    public PsMiseAEau lat(Float lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public PsMiseAEau lng(Float lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getAccess() {
        return access;
    }

    public PsMiseAEau access(String access) {
        this.access = access;
        return this;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public Boolean isAmenagee() {
        return amenagee;
    }

    public PsMiseAEau amenagee(Boolean amenagee) {
        this.amenagee = amenagee;
        return this;
    }

    public void setAmenagee(Boolean amenagee) {
        this.amenagee = amenagee;
    }

    public String getCommune() {
        return commune;
    }

    public PsMiseAEau commune(String commune) {
        this.commune = commune;
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getLieuDit() {
        return lieuDit;
    }

    public PsMiseAEau lieuDit(String lieuDit) {
        this.lieuDit = lieuDit;
        return this;
    }

    public void setLieuDit(String lieuDit) {
        this.lieuDit = lieuDit;
    }

    public Integer getParkingPlace() {
        return parkingPlace;
    }

    public PsMiseAEau parkingPlace(Integer parkingPlace) {
        this.parkingPlace = parkingPlace;
        return this;
    }

    public void setParkingPlace(Integer parkingPlace) {
        this.parkingPlace = parkingPlace;
    }

    public String getObservation() {
        return observation;
    }

    public PsMiseAEau observation(String observation) {
        this.observation = observation;
        return this;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public Boolean isParking() {
        return parking;
    }

    public PsMiseAEau parking(Boolean parking) {
        this.parking = parking;
        return this;
    }

    public void setParking(Boolean parking) {
        this.parking = parking;
    }

    public Boolean isPayant() {
        return payant;
    }

    public PsMiseAEau payant(Boolean payant) {
        this.payant = payant;
        return this;
    }

    public void setPayant(Boolean payant) {
        this.payant = payant;
    }

    public Boolean isPointEau() {
        return pointEau;
    }

    public PsMiseAEau pointEau(Boolean pointEau) {
        this.pointEau = pointEau;
        return this;
    }

    public void setPointEau(Boolean pointEau) {
        this.pointEau = pointEau;
    }

    public Boolean isPoubelles() {
        return poubelles;
    }

    public PsMiseAEau poubelles(Boolean poubelles) {
        this.poubelles = poubelles;
        return this;
    }

    public void setPoubelles(Boolean poubelles) {
        this.poubelles = poubelles;
    }

    public Boolean isToilettes() {
        return toilettes;
    }

    public PsMiseAEau toilettes(Boolean toilettes) {
        this.toilettes = toilettes;
        return this;
    }

    public void setToilettes(Boolean toilettes) {
        this.toilettes = toilettes;
    }

    public String getTitre() {
        return titre;
    }

    public PsMiseAEau titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public byte[] getPhotos() {
        return photos;
    }

    public PsMiseAEau photos(byte[] photos) {
        this.photos = photos;
        return this;
    }

    public void setPhotos(byte[] photos) {
        this.photos = photos;
    }

    public String getPhotosContentType() {
        return photosContentType;
    }

    public PsMiseAEau photosContentType(String photosContentType) {
        this.photosContentType = photosContentType;
        return this;
    }

    public void setPhotosContentType(String photosContentType) {
        this.photosContentType = photosContentType;
    }

    public PSStatus getStatus() {
        return status;
    }

    public PsMiseAEau status(PSStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PSStatus status) {
        this.status = status;
    }

    public PsMarina getMarinas() {
        return marinas;
    }

    public PsMiseAEau marinas(PsMarina psMarina) {
        this.marinas = psMarina;
        return this;
    }

    public void setMarinas(PsMarina psMarina) {
        this.marinas = psMarina;
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
        PsMiseAEau psMiseAEau = (PsMiseAEau) o;
        if (psMiseAEau.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), psMiseAEau.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PsMiseAEau{" +
            "id=" + getId() +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
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
