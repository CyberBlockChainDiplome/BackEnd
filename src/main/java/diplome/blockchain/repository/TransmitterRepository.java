package diplome.blockchain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import diplome.blockchain.model.Transmitter;
public interface TransmitterRepository extends JpaRepository<Transmitter,Long> {
    Transmitter findById(long id);

}
