package nc.si2p.peche.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import nc.si2p.peche.repository.MarkersRepository;

public class MarkerDataLoader {
    private MarkersRepository markerRepo;
    private final Logger log = LoggerFactory.getLogger(getClass());

    public MarkerDataLoader(MarkersRepository markerRepo) {
        this.markerRepo = markerRepo;
    }
    
    public void run(ApplicationArguments args) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeFactory typeFactory = objectMapper.getTypeFactory();
        List<DcpJSON> dcpListDTO = objectMapper.readValue(readJson(),
        typeFactory.constructCollectionType(List.class, DcpJSON.class));
        log.info("Import dcp from old db");
        DCPMapper dcpMapper = new DCPMapper();
        List<DCP> dcpList = dcpMapper.dcpDTOsTodcps(dcpListDTO);
        log.info(dcpList.toString());
        dcpRepository.saveAll(dcpList);
    }

    private String readJson() throws IOException {
        File file = ResourceUtils.getFile("classpath:marker.json");
        String data = FileUtils.readFileToString(file, "utf-8");
        return data;
    }
}