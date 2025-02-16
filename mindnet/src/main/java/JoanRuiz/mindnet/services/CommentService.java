package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.dto.CommentResponseDTO;
import JoanRuiz.mindnet.entities.Comment;
import JoanRuiz.mindnet.repositories.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public Optional<List<CommentResponseDTO>> getCommentsByPostId(Integer idPost) {
        try {
            List<Comment> comments = commentRepository.findByPostId(idPost);
            return Optional.of(comments.stream().map(comment -> new CommentResponseDTO(comment)).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean createComment(Comment comment) {
        try {
            Comment newComment = commentRepository.save(comment);
            return newComment.getId() != null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
