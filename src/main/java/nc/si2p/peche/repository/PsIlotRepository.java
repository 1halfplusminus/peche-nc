package nc.si2p.peche.repository;

import nc.si2p.peche.domain.PsIlot;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PsIlot entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PsIlotRepository extends JpaRepository<PsIlot, Long> {

}
