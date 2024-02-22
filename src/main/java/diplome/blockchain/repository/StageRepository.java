package diplome.blockchain.repository;

import diplome.blockchain.model.Stage;
import diplome.blockchain.model.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage,Long> {
    Stage findById(long id);

}
