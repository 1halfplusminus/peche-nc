package nc.si2p.peche.service.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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
    private String lastUpdateDate;

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
            setDateDerniereMaj(parseDate(date));
        } catch(ParseException e) {}
       
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