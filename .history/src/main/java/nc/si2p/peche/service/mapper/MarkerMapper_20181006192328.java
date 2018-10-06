package nc.si2p.peche.service.mapper;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import nc.si2p.peche.domain.Marker;
import nc.si2p.peche.service.dto.MarkerJSON;

public class MarkerMapper {
    public MarkerJSON markerToMarkerJSON(Marker marker) throws IOException, ParseException {
        return new MarkerJSON(
            marker.getId().intValue()
            ,null, null, null, null, null, null, null, null
        );
    }
    public Marker markerJSONToMarker(MarkerJSON markerDTO) {
        if (markerDTO == null) {
            return null;
        } else {
            Marker marker = new Marker();
            marker.setId(markerDTO.getId());
            marker
            .pj(markerDTO.getPj())
            .icon(markerDTO.getIcon())
            .date(markerDTO.getDate())
            .email(markerDTO.getEmail())
            .description(markerDTO.getDescription())
            .lat(markerDTO.getLat())
            .lng(markerDTO.getLng())
            .ip(markerDTO.getIp())
            .titre(markerDTO.getTitre());
            return marker;
        }
    }
    public List<Marker> markersJSONToMarkers(List<MarkerJSON> markersDTOs) {
        return markersDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::markerJSONToMarker)
            .collect(Collectors.toList());
    }
}