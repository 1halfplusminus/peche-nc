package nc.si2p.peche.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

import nc.si2p.peche.domain.enumeration.PSStatus;

/**
 * A PsIlot.
 */
@Entity
@Table(name = "ps_ilot")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "psilot")
public class PsIlot implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lng")
    private Float lng;

    @Column(name = "id_ilot")
    private String idIlot;

    @Column(name = "calendrier")
    private String calendrier;

    @Column(name = "commune")
    private String commune;

    @Column(name = "posehelico")
    private String posehelico;

    @Column(name = "titre")
    private String titre;

    @Column(name = "copyright")
    private String copyright;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @Column(name = "photo_content_type")
    private String photoContentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PSStatus status;

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

    public PsIlot lat(Float lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public PsIlot lng(Float lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public String getIdIlot() {
        return idIlot;
    }

    public PsIlot idIlot(String idIlot) {
        this.idIlot = idIlot;
        return this;
    }

    public void setIdIlot(String idIlot) {
        this.idIlot = idIlot;
    }

    public String getCalendrier() {
        return calendrier;
    }

    public PsIlot calendrier(String calendrier) {
        this.calendrier = calendrier;
        return this;
    }

    public void setCalendrier(String calendrier) {
        this.calendrier = calendrier;
    }

    public String getCommune() {
        return commune;
    }

    public PsIlot commune(String commune) {
        this.commune = commune;
        return this;
    }

    public void setCommune(String commune) {
        this.commune = commune;
    }

    public String getPosehelico() {
        return posehelico;
    }

    public PsIlot posehelico(String posehelico) {
        this.posehelico = posehelico;
        return this;
    }

    public void setPosehelico(String posehelico) {
        this.posehelico = posehelico;
    }

    public String getTitre() {
        return titre;
    }

    public PsIlot titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getCopyright() {
        return copyright;
    }

    public PsIlot copyright(String copyright) {
        this.copyright = copyright;
        return this;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public PsIlot photo(byte[] photo) {
        this.photo = photo;
        return this;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getPhotoContentType() {
        return photoContentType;
    }

    public PsIlot photoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
        return this;
    }

    public void setPhotoContentType(String photoContentType) {
        this.photoContentType = photoContentType;
    }

    public PSStatus getStatus() {
        return status;
    }

    public PsIlot status(PSStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(PSStatus status) {
        this.status = status;
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
        PsIlot psIlot = (PsIlot) o;
        if (psIlot.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), psIlot.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PsIlot{" +
            "id=" + getId() +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", idIlot='" + getIdIlot() + "'" +
            ", calendrier='" + getCalendrier() + "'" +
            ", commune='" + getCommune() + "'" +
            ", posehelico='" + getPosehelico() + "'" +
            ", titre='" + getTitre() + "'" +
            ", copyright='" + getCopyright() + "'" +
            ", photo='" + getPhoto() + "'" +
            ", photoContentType='" + getPhotoContentType() + "'" +
            ", status='" + getStatus() + "'" +
            "}";
    }
}
