package com.example.blog;

import com.example.blog.models.Post;
import com.example.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    public static class PostData {
        public String title;
        public String content;
    }


    @GetMapping(path = "/")
    public String index(Model model) {
        return "index";
    }

    @PostMapping(path = "/api/posts/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> addPost(@RequestBody PostData postData) {
        if (postData.title == null || postData.content == null) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", "Please include title and content in request body.");
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }

        Boolean result = postService.createPost(postData.title, postData.content);

        if (!result) {
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Map<String, Boolean> successResponse = new HashMap<>();
        successResponse.put("success", true);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);

    }

    @GetMapping(path = "/api/posts")
    public @ResponseBody ResponseEntity<Object> getAllPosts() {
        try {
            Map<String, Iterable<Post>> successResponse = new HashMap<>();
            successResponse.put("posts", postService.getPosts());
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        } catch (Exception e) {
            Map<String, Boolean> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(path = "/api/posts/update/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> updatePost(@PathVariable(value = "postId") int id,
                                                           @RequestBody PostData postData) {
        if (postRepository.existsById(id)) {
            boolean result = postService.updatePost(id, postData.title, postData.content);
            if (!result) {
                Map<String, Boolean> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Map<String, Boolean> successResponse = new HashMap<>();
            successResponse.put("success", true);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        Map<String, String> noPostResponse = new HashMap<>();
        noPostResponse.put("success", "false");
        noPostResponse.put("error", "No post exists with that id");
        return new ResponseEntity<>(noPostResponse, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping(path = "/api/posts/delete/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<Object> deletePost(@PathVariable(value = "postId") int id) {
        if (postRepository.existsById(id)) {
            boolean result = postService.deletePost(id);
            if (!result) {
                Map<String, Boolean> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            Map<String, Boolean> successResponse = new HashMap<>();
            successResponse.put("success", true);
            return new ResponseEntity<>(successResponse, HttpStatus.OK);
        }
        Map<String, String> noPostResponse = new HashMap<>();
        noPostResponse.put("success", "false");
        noPostResponse.put("error", "No posts exists with that id");
        return new ResponseEntity<>(noPostResponse, HttpStatus.BAD_REQUEST);

    }
}
