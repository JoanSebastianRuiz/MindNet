package JoanRuiz.mindnet.entities;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String  tag;

    @ManyToMany(mappedBy = "tags", fetch = FetchType.LAZY)
    private List<Post> posts;

    public Tag() {
    }

    public Tag(Integer id, String tag, List<Post> posts) {
        this.id = id;
        this.tag = tag;
        this.posts = posts;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
