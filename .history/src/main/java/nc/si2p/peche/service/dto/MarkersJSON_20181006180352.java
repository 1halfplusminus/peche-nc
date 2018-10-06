package nc.si2p.peche.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarkersJSON {
    public MarkersJSON(
        @JsonProperty("id") int id,
        @JsonProperty("icon") String icon
    ) {
      
    }
}