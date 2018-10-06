package nc.si2p.peche.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import nc.si2p.peche.domain.Marker;
import nc.si2p.peche.service.dto.MarkerJSON;

public class MarkerMapper {
    public MarkerJSON markerToMarkerJSON(Marker marker) {
        return new MarkerJSON(0, null);
    }
    public Marker markerJSONToMarker(MarkerJSON markersDTO) {
        if (markersDTO == null) {
            return null;
        } else {
            Marker markers = new Marker();
            return markers;
        }
    }
    public List<Marker> markersJSONToMarkers(List<MarkerJSON> markersDTOs) {
        return markersDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::markerJSONToMarker)
            .collect(Collectors.toList());
    }
}