package nc.si2p.peche.repository.search;

import nc.si2p.peche.domain.PsIlot;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the PsIlot entity.
 */
public interface PsIlotSearchRepository extends ElasticsearchRepository<PsIlot, Long> {
}
