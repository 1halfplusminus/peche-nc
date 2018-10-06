package nc.si2p.peche.web.rest;

import com.codahale.metrics.annotation.Timed;
import nc.si2p.peche.domain.Markers;
import nc.si2p.peche.repository.MarkersRepository;
import nc.si2p.peche.repository.search.MarkersSearchRepository;
import nc.si2p.peche.web.rest.errors.BadRequestAlertException;
import nc.si2p.peche.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Markers.
 */
@RestController
@RequestMapping("/api")
public class MarkersResource {

    private final Logger log = LoggerFactory.getLogger(MarkersResource.class);

    private static final String ENTITY_NAME = "markers";

    private final MarkersRepository markersRepository;

    private final MarkersSearchRepository markersSearchRepository;

    public MarkersResource(MarkersRepository markersRepository, MarkersSearchRepository markersSearchRepository) {
        this.markersRepository = markersRepository;
        this.markersSearchRepository = markersSearchRepository;
    }

    /**
     * POST  /markers : Create a new markers.
     *
     * @param markers the markers to create
     * @return the ResponseEntity with status 201 (Created) and with body the new markers, or with status 400 (Bad Request) if the markers has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/markers")
    @Timed
    public ResponseEntity<Markers> createMarkers(@RequestBody Markers markers) throws URISyntaxException {
        log.debug("REST request to save Markers : {}", markers);
        if (markers.getId() != null) {
            throw new BadRequestAlertException("A new markers cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Markers result = markersRepository.save(markers);
        markersSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/markers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /markers : Updates an existing markers.
     *
     * @param markers the markers to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated markers,
     * or with status 400 (Bad Request) if the markers is not valid,
     * or with status 500 (Internal Server Error) if the markers couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/markers")
    @Timed
    public ResponseEntity<Markers> updateMarkers(@RequestBody Markers markers) throws URISyntaxException {
        log.debug("REST request to update Markers : {}", markers);
        if (markers.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Markers result = markersRepository.save(markers);
        markersSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, markers.getId().toString()))
            .body(result);
    }

    /**
     * GET  /markers : get all the markers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of markers in body
     */
    @GetMapping("/markers")
    @Timed
    public List<Markers> getAllMarkers() {
        log.debug("REST request to get all Markers");
        return markersRepository.findAll();
    }

    /**
     * GET  /markers/:id : get the "id" markers.
     *
     * @param id the id of the markers to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the markers, or with status 404 (Not Found)
     */
    @GetMapping("/markers/{id}")
    @Timed
    public ResponseEntity<Markers> getMarkers(@PathVariable Long id) {
        log.debug("REST request to get Markers : {}", id);
        Optional<Markers> markers = markersRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(markers);
    }

    /**
     * DELETE  /markers/:id : delete the "id" markers.
     *
     * @param id the id of the markers to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/markers/{id}")
    @Timed
    public ResponseEntity<Void> deleteMarkers(@PathVariable Long id) {
        log.debug("REST request to delete Markers : {}", id);

        markersRepository.deleteById(id);
        markersSearchRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/markers?query=:query : search for the markers corresponding
     * to the query.
     *
     * @param query the query of the markers search
     * @return the result of the search
     */
    @GetMapping("/_search/markers")
    @Timed
    public List<Markers> searchMarkers(@RequestParam String query) {
        log.debug("REST request to search Markers for query {}", query);
        return StreamSupport
            .stream(markersSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

}
