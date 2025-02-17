package JoanRuiz.mindnet.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String  name;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Post> posts;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Comment> comments;

    public Tag() {
    }

    public Tag(String name) {
        this.name = name;
    }

    public Tag(Integer id, String name, List<Post> posts, List<Comment> comments) {
        this.id = id;
        this.name = name;
        this.posts = posts;
        this.comments = comments;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
