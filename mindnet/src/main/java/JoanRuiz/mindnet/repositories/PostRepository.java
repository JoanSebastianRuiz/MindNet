package JoanRuiz.mindnet.repositories;

import JoanRuiz.mindnet.entities.Post;
import JoanRuiz.mindnet.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<Post, Integer> {
    List<Post> findByUserUsername(String username);

    @Query(value = "SELECT COUNT(r) FROM reaction r WHERE id_post=:postId", nativeQuery = true)
    Integer countReactionsByPostId(@Param("postId") Integer postId);

}
