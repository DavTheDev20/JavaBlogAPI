package com.example.blog.services;

import com.example.blog.MainController;
import com.example.blog.PostRepository;
import com.example.blog.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;


    public Boolean createPost(String title, String content) {
        try {
            Post newPost = new Post();
            newPost.setTitle(title);
            newPost.setContent(content);
            postRepository.save(newPost);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    public Iterable<Post> getPosts() {
     return postRepository.findAll();
    }

    public Boolean updatePost(int postId, String title, String content) {
        if (postRepository.existsById(postId)) {
            Post post = postRepository.findById(postId).get();
            post.setTitle(title);
            post.setContent(content);
            postRepository.save(post);
            return true;
        }
        return false;
    }

    public Boolean deletePost(int postId) {
        if (postRepository.existsById(postId)) {
            postRepository.deleteById(postId);
            return true;
        }
        return false;
    }

}
