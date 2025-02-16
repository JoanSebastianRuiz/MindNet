package JoanRuiz.mindnet.repositories;

import JoanRuiz.mindnet.entities.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
}
