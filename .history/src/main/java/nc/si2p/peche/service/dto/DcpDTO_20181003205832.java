package nc.si2p.peche.service.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A DTO representing a DCP
 */
public class DcpDTO {
    public int id;
    public String name;
 
    @JsonCreator
    public DcpDTO(
      @JsonProperty("id") int id, 
      @JsonProperty("theName") String name) {
        this.id = id;
        this.name = name;
    }
}