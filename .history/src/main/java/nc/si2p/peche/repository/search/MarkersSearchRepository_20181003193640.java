package nc.si2p.peche.repository.search;

import nc.si2p.peche.domain.Markers;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Markers entity.
 */
public interface MarkersSearchRepository extends ElasticsearchRepository<Markers, Long> {
}
