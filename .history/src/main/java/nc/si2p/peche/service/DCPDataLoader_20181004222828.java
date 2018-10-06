package nc.si2p.peche.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import nc.si2p.peche.domain.DCP;
import nc.si2p.peche.repository.DCPRepository;
import nc.si2p.peche.service.dto.DcpDTO;
import nc.si2p.peche.service.mapper.DCPMapper;

@Component
public class DCPDataLoader implements ApplicationRunner {

    private DCPRepository dcpRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public DCPDataLoader(DCPRepository dcpRepository) {
        this.dcpRepository = dcpRepository;
    }

    public void run(ApplicationArguments args) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<DcpDTO> dcpListDTO = objectMapper.readValue(readJson(),
        typeFactory.constructCollectionType(List.class, DcpDTO.class));
        log.info("Import dcp from old db");
        DCPMapper dcpMapper = new DCPMapper();
        List<DCP> dcpList = dcpMapper.dcpDTOsTodcps(dcpListDTO);
        log.info(dcpList.toString());
        dcpRepository.saveAll(dcpList);
    }

    private String readJson() throws IOException {
        File file = ResourceUtils.getFile("classpath:dcp.json");
        String data = FileUtils.readFileToString(file, "utf-8");
        return data;
    }
}