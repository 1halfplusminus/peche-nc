package nc.si2p.peche.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import nc.si2p.peche.domain.Markers;
import nc.si2p.peche.service.dto.MarkersJSON;

public class MarkerMapper {
    public MarkersJSON markerToMarkerJSON(Markers marker) {
        return new MarkersJSON(0, null);
    }
    public Markers markerJSONTomarker(MarkersJSON markersDTO) {
        if (markersDTO == null) {
            return null;
        } else {
            Markers markers = new Markers();
            return markers;
        }
    }
    public List<Markers> markersJSONToMarkers(List<MarkersJSON> markersDTOs) {
        return markersDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::markerJSONTomarker)
            .collect(Collectors.toList());
    }
}