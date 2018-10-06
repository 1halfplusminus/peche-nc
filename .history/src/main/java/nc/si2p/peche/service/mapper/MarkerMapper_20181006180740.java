package nc.si2p.peche.service.mapper;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import nc.si2p.peche.domain.Marker;
import nc.si2p.peche.service.dto.MarkersJSON;

public class MarkerMapper {
    public MarkersJSON markerToMarkerJSON(Marker marker) {
        return new MarkersJSON(0, null);
    }
    public Marker markerJSONTomarker(MarkersJSON markersDTO) {
        if (markersDTO == null) {
            return null;
        } else {
            Marker markers = new Marker();
            return markers;
        }
    }
    public List<Marker> markersJSONToMarkers(List<MarkersJSON> markersDTOs) {
        return markersDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::markerJSONTomarker)
            .collect(Collectors.toList());
    }
}