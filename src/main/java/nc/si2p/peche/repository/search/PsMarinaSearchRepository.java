package nc.si2p.peche.repository.search;

import nc.si2p.peche.domain.PsMarina;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PsMarina entity.
 */
public interface PsMarinaSearchRepository extends ElasticsearchRepository<PsMarina, Long> {
}
