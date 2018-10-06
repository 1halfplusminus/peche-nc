package nc.si2p.peche.service.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nc.si2p.peche.domain.enumeration.DCPEtat;

/**
 * A DTO representing a DCP
 */
public class DcpDTO  {
    private static final long serialVersionUID = 1L;
    private long id;
    private String name;
    private String position;
    private DCPEtat state;
    private Float lat;
    private Float lng;
    private LocalDate lastUpdateDate;
    private String localisation;
    @JsonCreator
    public DcpDTO(
      @JsonProperty("id") int id, 
      @JsonProperty("nom") String name,
      @JsonProperty("position") String position,
      @JsonProperty("etat") String etat,
      @JsonProperty("lat") String latString,
      @JsonProperty("lng") String lngString,
      @JsonProperty("date_derniere_maj") String date
      @JsonProperty("localisation") String localisation) {
        setId((long) id);
        setName(name);
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
    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    /**
     * @param localDate the lastUpdateDate to set
     */
    public void setLastUpdateDate(LocalDate localDate) {
        this.lastUpdateDate = localDate;
    }

    /**
     * @return the lng
     */
    public Float getLng() {
        return lng;
    }

    /**
     * @param float1 the lng to set
     */
    public void setLng(Float float1) {
        this.lng = float1;
    }

    /**
     * @return the lat
     */
    public Float getLat() {
        return lat;
    }

    /**
     * @param float1 the lat to set
     */
    public void setLat(Float float1) {
        this.lat = float1;
    }

    /**
     * @return the state
     */
    public DCPEtat getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(DCPEtat state) {
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
    public long getId() {
        return id;
    }

    /**
     * @param id2 the id to set
     */
    public void setId(long id2) {
        this.id = id2;
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