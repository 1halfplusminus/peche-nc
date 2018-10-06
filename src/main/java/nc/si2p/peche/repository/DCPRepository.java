package nc.si2p.peche.repository;

import nc.si2p.peche.domain.DCP;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DCP entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DCPRepository extends JpaRepository<DCP, Long> {

}
