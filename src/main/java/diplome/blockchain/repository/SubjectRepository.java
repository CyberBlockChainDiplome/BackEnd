package diplome.blockchain.repository;

import diplome.blockchain.model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubjectRepository extends JpaRepository<Subject,Long>{
    Subject findById(long id);

}
