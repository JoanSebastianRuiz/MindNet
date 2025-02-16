package JoanRuiz.mindnet.repositories;

import JoanRuiz.mindnet.entities.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Integer> {
    List<Comment> findByPostId(Integer idPost);
}
