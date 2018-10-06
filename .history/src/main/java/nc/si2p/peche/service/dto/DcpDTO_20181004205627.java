package nc.si2p.peche.service.dto;

import java.text.SimpleDateFormat;
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
      @JsonProperty("lat") Float lat,
      @JsonProperty("lng") Float lng,
      @JsonProperty("date_derniere_maj") String date) {
        this.setId((long) id);
        this.setNom(name);
        this.setPosition(position);
        this.setLat(lat);
        this.setLng(lng);
        this.setDateDerniereMaj(dateDerniereMaj);
    }

    private Date parseDate(String date) {
        String pattern = "MM-dd-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        return simpleDateFormat.parse(date);
    }
}