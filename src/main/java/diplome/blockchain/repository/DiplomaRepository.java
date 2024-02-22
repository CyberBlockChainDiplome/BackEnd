package diplome.blockchain.repository;

import diplome.blockchain.model.Diploma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiplomaRepository extends JpaRepository<Diploma,Long>{
    Diploma findById(long id);

}
