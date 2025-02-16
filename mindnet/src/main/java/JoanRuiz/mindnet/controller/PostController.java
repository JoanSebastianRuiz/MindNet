package JoanRuiz.mindnet.controller;

import JoanRuiz.mindnet.entities.Post;
import JoanRuiz.mindnet.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://midominio.com"}, allowCredentials = "true")
@RequestMapping("/api/posts")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping
    public ResponseEntity<String> createPost(@RequestBody Post post) {
        if (postService.createPost(post)) {
            return ResponseEntity.ok("Post created");
        } else {
            return ResponseEntity.badRequest().body("Error creating post");
        }
    }

    @GetMapping
    public ResponseEntity<?> getPosts() {
        if (postService.getPosts().isPresent()) {
            return ResponseEntity.ok(postService.getPosts().get());
        } else {
            return ResponseEntity.badRequest().body("Error getting posts");
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
}
