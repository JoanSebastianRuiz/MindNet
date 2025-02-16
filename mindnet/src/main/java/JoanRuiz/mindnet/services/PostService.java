package JoanRuiz.mindnet.services;

import JoanRuiz.mindnet.dto.PostResponseDTO;
import JoanRuiz.mindnet.entities.Post;
import JoanRuiz.mindnet.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Boolean createPost(Post post) {
        try{
            Post newPost = postRepository.save(post);
            return newPost.getId() != null;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public Optional<List<PostResponseDTO>> getPosts() {
        try {
            List<Post> posts = (List<Post>) postRepository.findAll();
            return Optional.of(posts.stream().map(post -> {
                Integer likesCount = postRepository.countReactionsByPostId(post.getId());
                Integer commentsCount = postRepository.countCommentsByPostId(post.getId());
                return new PostResponseDTO(post, likesCount, commentsCount);
            }).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<List<PostResponseDTO>> getPostsByUserUsername(String username) {
        try {
            List<Post> posts = postRepository.findByUserUsername(username);
            return Optional.of(posts.stream().map(post -> {
                Integer likesCount = postRepository.countReactionsByPostId(post.getId());
                Integer commentsCount = postRepository.countCommentsByPostId(post.getId());
                return new PostResponseDTO(post, likesCount, commentsCount);
            }).collect(Collectors.toList()));
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Boolean deletePost(Integer id) {
        try {
            postRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public Boolean updatePost(Integer id, Post post) {
        try {
            Post postToUpdate = postRepository.findById(id).get();
            postToUpdate.setBody(post.getBody());
            postToUpdate.setImageUrl(post.getImageUrl());
            postRepository.save(postToUpdate);
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

}
