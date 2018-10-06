package nc.si2p.peche.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import nc.si2p.peche.domain.DCP;
import nc.si2p.peche.repository.DCPRepository;
import nc.si2p.peche.service.dto.DcpDTO;

@Component
public class DataLoader implements ApplicationRunner {

    private DCPRepository dcpRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    public DataLoader(DCPRepository dcpRepository) {
        this.dcpRepository = dcpRepository;
    }

    public void run(ApplicationArguments args) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        String dcpList = objectMapper.readValue(readJson(),
        typeFactory.constructCollectionType(List.class, DcpDTO.class));
        log.info("Import dcp from old db");
        log.debug(dcpList);
    }

    private String readJson() throws IOException {
        ClassPathResource res = new ClassPathResource("dcp.json"); 
        File file = new File(res.getPath());
        String data = FileUtils.readFileToString(file, "UTF-8");
        return data;
    }
}