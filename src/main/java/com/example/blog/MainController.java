package com.example.blog;

import com.example.blog.models.Post;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {
    @Autowired
    private PostRepository postRepository;

    @PostMapping(path = "/api/posts/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String addPost(@RequestParam String title, @RequestParam String content, HttpServletResponse response) {
        try {
            Post newPost = new Post();
            newPost.setTitle(title);
            newPost.setContent(content);
            postRepository.save(newPost);
            return "{\"success\": 1, \"response\": \"Post was successfully saved\"}";
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "{\"success\": 0}";
        }

    }

    @GetMapping(path = "/api/posts")
    public @ResponseBody Iterable<Post> getAllPosts(HttpServletResponse response) throws Exception {
        try {
            return postRepository.findAll();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            throw new Exception(e);
        }
    }

    @PutMapping(path = "/api/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String updatePost(@PathVariable(value = "postId") int id, @RequestParam String title, @RequestParam String content, HttpServletResponse response) {
        try {
            if (postRepository.existsById(id)) {
                Post post = postRepository.findById(id).get();
                post.setTitle(title);
                post.setContent(content);
                postRepository.save(post);
                return "{\"success\": 1}";
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "{\"success\": 0, \"error\": \" No post exists with that id\"}";

        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "{\"success\": 0}";
        }
    }

    @DeleteMapping(path = "/api/posts/{postId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody String deletePost(@PathVariable(value = "postId") int id, HttpServletResponse response) {
        try {
            if (postRepository.existsById(id)) {
                postRepository.deleteById(id);
                return "{\"succeess\": 1}";
            }
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "{\"success\": 0, \"error\": \" No post exists with that id\"}";

        } catch (Exception e) {
            System.out.println(e);
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return "{\"success\": 0}";
        }
    }
}
