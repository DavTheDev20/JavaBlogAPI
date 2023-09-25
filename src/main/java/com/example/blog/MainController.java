package com.example.blog;

import com.example.blog.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private PostRepository postRepository;

    @PostMapping(path = "/api/posts/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> addPost(@RequestParam String title, @RequestParam String content) {
        try {
            Post newPost = new Post();
            newPost.setTitle(title);
            newPost.setContent(content);
            postRepository.save(newPost);
            Map<String, Boolean> successResponse = new HashMap<>();
            successResponse.put("success", true);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping(path = "/api/posts")
    public @ResponseBody ResponseEntity<Object> getAllPosts() {
        try {
            Map<String, Iterable<Post>> successResponse = new HashMap<>();
            successResponse.put("posts", postRepository.findAll());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/api/posts/update/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> updatePost(@PathVariable(value = "postId") int id, @RequestParam String title,
            @RequestParam String content) {
        try {
            if (postRepository.existsById(id)) {
                Post post = postRepository.findById(id).get();
                post.setTitle(title);
                post.setContent(content);
                postRepository.save(post);
                Map<String, Boolean> successResponse = new HashMap<>();
                successResponse.put("success", true);
                return new ResponseEntity<>(successResponse, HttpStatus.OK);
            }
            Map<String, String> noPostResponse = new HashMap<>();
            noPostResponse.put("success", "false");
            noPostResponse.put("error", "No post exists with that id");
            return new ResponseEntity<>(noPostResponse, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            System.out.println(e);
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping(path = "/api/posts/delete/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> deletePost(@PathVariable(value = "postId") int id) {
        try {
            if (postRepository.existsById(id)) {
                postRepository.deleteById(id);
                Map<String, Boolean> successResponse = new HashMap<>();
                successResponse.put("success", true);
                return new ResponseEntity<>(successResponse, HttpStatus.OK);
            }
            Map<String, String> noPostResponse = new HashMap<>();
            noPostResponse.put("success", "false");
            noPostResponse.put("error", "No posts exists with that id");
            return new ResponseEntity<>(noPostResponse, HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            System.out.println(e);
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
