package nc.si2p.peche.service.mapper;

import nc.si2p.peche.domain.DCP;
import nc.si2p.peche.service.dto.DcpDTO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class DCPMapper {
    public DcpDTO dcpToDCPDTO(DCP dcp) {
        return new DcpDTO();
    }
    public DCP dcpDTOTodcp(DcpDTO dcpDTO) {
        if (dcpDTO == null) {
            return null;
        } else {
            DCP dcp = new DCP();
            dcp.setDateDerniereMaj(dcpDTO.getLastUpdateDate());
            dcp.setEtat(dcpDTO.getState());
            dcp.setId(dcpDTO.getId());
            dcp.setLocalisation(dcpDTO.getLocalisation());
            dcp.nom(dcpDTO.getName);
            return dcp;
        }
    }
    public List<DCP> dcpDTOsTodcps(List<DcpDTO> dcpDTOs) {
        return dcpDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::dcpDTOTodcp)
            .collect(Collectors.toList());
    }
}