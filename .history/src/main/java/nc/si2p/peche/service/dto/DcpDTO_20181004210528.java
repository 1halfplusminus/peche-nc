package nc.si2p.peche.service.dto;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import nc.si2p.peche.domain.DCP;

/**
 * A DTO representing a DCP
 */
public class DcpDTO extends DCP {
    @JsonCreator
    public DcpDTO(
      @JsonProperty("id") int id, 
      @JsonProperty("nom") String name,
      @JsonProperty("position") String position,
      @JsonProperty("lat") String lat,
      @JsonProperty("lng") String lng,
      @JsonProperty("date_derniere_maj") String date) {
        setId((long) id);
        setNom(name);
        setPosition(position);
        setLat(lat);
        setLng(lng);
        try {
            setDateDerniereMaj(parseDate(date));
        } catch(ParseException e) {}
       
    }

    private LocalDate parseDate(String date) throws ParseException {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return new java.sql.Date( simpleDateFormat.parse(date).getTime() ).toLocalDate();
    }
}