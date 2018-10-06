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
import org.springframework.util.ResourceUtils;

import nc.si2p.peche.domain.DCP;
import nc.si2p.peche.domain.Marker;
import nc.si2p.peche.repository.MarkersRepository;
import nc.si2p.peche.service.dto.MarkerJSON;
import nc.si2p.peche.service.mapper.MarkerMapper;

public class MarkerDataLoader {
    private MarkersRepository repo;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public MarkerDataLoader(MarkersRepository repo) {
        this.repo = repo;
    }
    
    public void run(ApplicationArguments args) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<MarkerJSON> listJSON = objectMapper.readValue(readJson(),
        typeFactory.constructCollectionType(List.class, MarkerJSON.class));
        log.info("Import marker from old db");
        MarkerMapper mapper = new MarkerMapper();
        List<Marker> list = mapper.markersJSONToMarkers(listJSON);
        log.info("Imported marker...");
        log.info(list.toString());
        repo.saveAll(list);
    }

    private String readJson() throws IOException {
        File file = ResourceUtils.getFile("classpath:marker.json");
        String data = FileUtils.readFileToString(file, "utf-8");
        return data;
    }
}