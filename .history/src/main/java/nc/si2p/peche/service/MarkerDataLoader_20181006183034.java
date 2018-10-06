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
}