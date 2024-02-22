package diplome.blockchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import diplome.blockchain.model.Receiver;

public interface ReceiverRepository extends JpaRepository<Receiver,Long>{

    Receiver findById(long id);
}
