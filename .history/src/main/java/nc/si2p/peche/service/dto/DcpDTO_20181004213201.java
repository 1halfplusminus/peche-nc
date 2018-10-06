package nc.si2p.peche.service.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.github.jhipster.config.JHipsterDefaults.Cache.Infinispan.Local;
import nc.si2p.peche.domain.DCP;
import nc.si2p.peche.domain.enumeration.DCPEtat;

/**
 * A DTO representing a DCP
 */
public class DcpDTO  {
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private String position;
    private String state;
    private String lat;
    private String lng;
    private LocalDate lastUpdateDate;

    @JsonCreator
    public DcpDTO(
      @JsonProperty("id") int id, 
      @JsonProperty("nom") String name,
      @JsonProperty("position") String position,
      @JsonProperty("etat") String etat,
      @JsonProperty("lat") String latString,
      @JsonProperty("lng") String lngString,
      @JsonProperty("date_derniere_maj") String date) {
        setId((long) id);
        setNom(name);
        setPosition(position);
        setLat(parseFloat(latString));
        setLng(parseFloat(lngString));
        try {
            setLastUpdateDate(parseDate(date));
        } catch(ParseException e) {}
       
    }
    
    /**
     * @return the lastUpdateDate
     */
    public String getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param lastUpdateDate the lastUpdateDate to set
     */
    public void setLastUpdateDate(String lastUpdateDate) {
        this.lastUpdateDate = lastUpdateDate;
    }

    /**
     * @return the lng
     */
    public String getLng() {
        return lng;
    }

    /**
     * @param lng the lng to set
     */
    public void setLng(String lng) {
        this.lng = lng;
    }

    /**
     * @return the lat
     */
    public String getLat() {
        return lat;
    }

    /**
     * @param lat the lat to set
     */
    public void setLat(String lat) {
        this.lat = lat;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    private LocalDate parseDate(String date) throws ParseException {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return new java.sql.Date( simpleDateFormat.parse(date).getTime() ).toLocalDate();
    }
    private Float parseFloat(String string) {
        if(string.trim().isEmpty()) {
            return (float) 0;
        }
        return Float.parseFloat(string);
    }
}