package nc.si2p.peche.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import nc.si2p.peche.domain.DCP;
import nc.si2p.peche.repository.DCPRepository;

@Component
public class DataLoader implements ApplicationRunner {

    private DCPRepository dcpRepository;
    private final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    public DataLoader(DCPRepository dcpRepository) {
        this.dcpRepository = dcpRepository;
    }

    public void run(ApplicationArguments args) {
        log.info("running Dataloader");
        DCP dcp = new DCP();
        dcpRepository.save(dcp);
       
    }
}