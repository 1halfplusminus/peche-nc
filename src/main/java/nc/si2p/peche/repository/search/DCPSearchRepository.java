package nc.si2p.peche.repository.search;

import nc.si2p.peche.domain.DCP;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DCP entity.
 */
public interface DCPSearchRepository extends ElasticsearchRepository<DCP, Long> {
}
