package nc.si2p.peche.repository;

import nc.si2p.peche.domain.PsMiseAEau;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PsMiseAEau entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PsMiseAEauRepository extends JpaRepository<PsMiseAEau, Long> {

}
