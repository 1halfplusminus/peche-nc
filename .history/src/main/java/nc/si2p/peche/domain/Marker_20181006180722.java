package nc.si2p.peche.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Markers.
 */
@Entity
@Table(name = "markers")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "markers")
public class Markers implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "icon")
    private byte[] icon;

    @Column(name = "icon_content_type")
    private String iconContentType;

    @Column(name = "titre")
    private String titre;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "pj")
    private byte[] pj;

    @Column(name = "pj_content_type")
    private String pjContentType;

    @Column(name = "ip")
    private String ip;

    @Column(name = "lat")
    private Float lat;

    @Column(name = "lng")
    private Float lng;

    @Column(name = "jhi_date")
    private LocalDate date;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getIcon() {
        return icon;
    }

    public Markers icon(byte[] icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
    }

    public String getIconContentType() {
        return iconContentType;
    }

    public Markers iconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
        return this;
    }

    public void setIconContentType(String iconContentType) {
        this.iconContentType = iconContentType;
    }

    public String getTitre() {
        return titre;
    }

    public Markers titre(String titre) {
        this.titre = titre;
        return this;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public Markers description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getEmail() {
        return email;
    }

    public Markers email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPj() {
        return pj;
    }

    public Markers pj(byte[] pj) {
        this.pj = pj;
        return this;
    }

    public void setPj(byte[] pj) {
        this.pj = pj;
    }

    public String getPjContentType() {
        return pjContentType;
    }

    public Markers pjContentType(String pjContentType) {
        this.pjContentType = pjContentType;
        return this;
    }

    public void setPjContentType(String pjContentType) {
        this.pjContentType = pjContentType;
    }

    public String getIp() {
        return ip;
    }

    public Markers ip(String ip) {
        this.ip = ip;
        return this;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Float getLat() {
        return lat;
    }

    public Markers lat(Float lat) {
        this.lat = lat;
        return this;
    }

    public void setLat(Float lat) {
        this.lat = lat;
    }

    public Float getLng() {
        return lng;
    }

    public Markers lng(Float lng) {
        this.lng = lng;
        return this;
    }

    public void setLng(Float lng) {
        this.lng = lng;
    }

    public LocalDate getDate() {
        return date;
    }

    public Markers date(LocalDate date) {
        this.date = date;
        return this;
    }

    public void setDate(LocalDate date) {
        this.date = date;
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
        Markers markers = (Markers) o;
        if (markers.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), markers.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Markers{" +
            "id=" + getId() +
            ", icon='" + getIcon() + "'" +
            ", iconContentType='" + getIconContentType() + "'" +
            ", titre='" + getTitre() + "'" +
            ", description='" + getDescription() + "'" +
            ", email='" + getEmail() + "'" +
            ", pj='" + getPj() + "'" +
            ", pjContentType='" + getPjContentType() + "'" +
            ", ip='" + getIp() + "'" +
            ", lat=" + getLat() +
            ", lng=" + getLng() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
