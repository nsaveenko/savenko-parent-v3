package com.netcracker.savenko.backend.controller;

import com.netcracker.savenko.backend.entity.PostEntity;
import com.netcracker.savenko.backend.entity.UserEntity;
import com.netcracker.savenko.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @RequestMapping(value = "complaint/{postId}/{complaintId}", method = RequestMethod.GET)
    public PostEntity getPostById(@PathVariable(name = "postId") int postId, @PathVariable(name = "complaintId") int complaintId){
        return postService.getPostByPostIdAndComplaintId(postId, complaintId);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<PostEntity> getPostById(@PathVariable(name = "id") Integer id) {
        Optional<PostEntity> post = postService.getPostById(id);
        if (post.isPresent()) {
            return ResponseEntity.ok(post.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "/api/user/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserEntity> getUserById(@PathVariable(name = "id") Integer id) {
        Optional<UserEntity> user = postService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    public Iterable<PostEntity> getAllPost() {
        return postService.getAllPost();
    }

    @RequestMapping(value = "/followers/{id}", method = RequestMethod.GET)
    public List<PostEntity> getPostBySub(@PathVariable(name = "id") int userId) {
        return postService.getPostBySub(userId);
    }

    @RequestMapping(value = "/currUser/{id}", method = RequestMethod.GET)
    public List<PostEntity> getPostByCurrUser(@PathVariable(name = "id") int userId) {
        return postService.getPostByCurrUser(userId);
    }

    @RequestMapping(value = "/count/user/{id}", method = RequestMethod.GET)
    public Integer countPostByUserId(@PathVariable(name = "id") int userId) {
        return postService.countPostByUserId(userId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public PostEntity savePost(@RequestBody PostEntity post) {
        return postService.savePost(post);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deletePost(@PathVariable(name = "id") Integer id) {
        postService.deletePost(id);
    }
}
