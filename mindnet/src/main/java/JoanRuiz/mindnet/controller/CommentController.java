package JoanRuiz.mindnet.controller;

import JoanRuiz.mindnet.dto.CommentRequestDTO;
import JoanRuiz.mindnet.entities.Comment;
import JoanRuiz.mindnet.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @GetMapping("/post/{idPost}")
    public ResponseEntity<?> getCommentsByPostId(@PathVariable Integer idPost) {
        if (commentService.getCommentsByPostId(idPost).isPresent()) {
            return ResponseEntity.ok(commentService.getCommentsByPostId(idPost).get());
        } else {
            return ResponseEntity.badRequest().body("Error getting comments by post id");
        }
    }

    @PostMapping
    public ResponseEntity<String> createComment(@RequestBody CommentRequestDTO comment) {
        if (commentService.createComment(comment)) {
            return ResponseEntity.ok("Comment created");
        } else {
            return ResponseEntity.badRequest().body("Error creating comment");
        }
    }
}
