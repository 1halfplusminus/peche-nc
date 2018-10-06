package nc.si2p.peche.service.dto;

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
    }
}