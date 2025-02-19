package JoanRuiz.mindnet.controller;

import JoanRuiz.mindnet.dto.LikeRequest;
import JoanRuiz.mindnet.dto.PostRequestDTO;
import JoanRuiz.mindnet.dto.PostResponseDTO;
import JoanRuiz.mindnet.entities.Post;
import JoanRuiz.mindnet.repositories.NotificationTypeRepository;
import JoanRuiz.mindnet.services.NotificationService;
import JoanRuiz.mindnet.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://midominio.com"}, allowCredentials = "true")
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody PostRequestDTO post) {
        if (postService.createPost(post)) {
            return ResponseEntity.ok("Post created");
        } else {
            return ResponseEntity.badRequest().body("Error creating post");
        }
    }

    @GetMapping
    public ResponseEntity<?> getPosts(@RequestParam String filter, @RequestParam String scope, @RequestParam String iduser) {
        try {
            Integer idUserInteger = Integer.parseInt(iduser); // Intentar parsear el ID
            Optional<List<PostResponseDTO>> posts = postService.getPosts(filter, scope, idUserInteger);

            if (posts.isPresent()) {
                return ResponseEntity.ok(posts.get());
            } else {
                return ResponseEntity.badRequest().body("Error getting posts");
            }
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Invalid user ID format");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }


    @GetMapping("/user/{username}")
    public ResponseEntity<?> getPostsByUserId(@PathVariable String username){
        if (postService.getPostsByUserUsername(username).isPresent()) {
            return ResponseEntity.ok(postService.getPostsByUserUsername(username).get());
        } else {
            return ResponseEntity.badRequest().body("Error getting posts by user id");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Integer id) {
        if (postService.deletePost(id)) {
            return ResponseEntity.ok("Post deleted");
        } else {
            return ResponseEntity.badRequest().body("Error deleting post");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable Integer id, @RequestBody Post post) {
        if (postService.updatePost(id, post)) {
            return ResponseEntity.ok("Post updated");
        } else {
            return ResponseEntity.badRequest().body("Error updating post");
        }
    }

    @PutMapping("/{id}/like")
    public ResponseEntity<String> likePost(@PathVariable Integer id, @RequestBody LikeRequest likeRequest) {
        return postService.reactToPost(id, likeRequest.getIdUser());
    }

    @GetMapping("/{id}/is-liked")
    public ResponseEntity<Boolean> userLikeToPost(@PathVariable Integer id, @RequestParam Integer idUser) {
        return postService.userLikeToPost(id, idUser);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPostById(@PathVariable Integer id) {
        Optional<PostResponseDTO> post = postService.getPostById(id);

        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            return ResponseEntity.badRequest().body("Error getting post by id");
        }
    }
}
