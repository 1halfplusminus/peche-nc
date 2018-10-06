package nc.si2p.peche.repository.search;

import nc.si2p.peche.domain.PsMiseAEau;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PsMiseAEau entity.
 */
public interface PsMiseAEauSearchRepository extends ElasticsearchRepository<PsMiseAEau, Long> {
}
