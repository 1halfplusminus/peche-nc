package nc.si2p.peche.repository;

import nc.si2p.peche.domain.PsMarina;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PsMarina entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PsMarinaRepository extends JpaRepository<PsMarina, Long> {

}
