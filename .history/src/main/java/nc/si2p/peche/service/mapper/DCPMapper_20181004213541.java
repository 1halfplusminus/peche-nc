package nc.si2p.peche.service.mapper;

import nc.si2p.peche.domain.DCP;
import nc.si2p.peche.service.dto.DcpDTO;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity User and its DTO called UserDTO.
 *
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class DCPMapper {
    public DcpDTO dcpToDCPDTO(DCP dcp) {
        return new DcpDTO(dcp);
    }
    public DCP dcpDTOTodcp(DcpDTO dcpDTO) {
        if (dcpDTO == null) {
            return null;
        } else {
            DCP dcp = new DCP();
            
            return dcp;
        }
    }
}