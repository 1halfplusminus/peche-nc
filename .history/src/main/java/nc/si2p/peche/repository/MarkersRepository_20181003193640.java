package nc.si2p.peche.repository;

import nc.si2p.peche.domain.Markers;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Markers entity.
 */
@SuppressWarnings("unused")
@Repository
public interface MarkersRepository extends JpaRepository<Markers, Long> {

}
