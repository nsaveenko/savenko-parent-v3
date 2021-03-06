package com.netcracker.savenko.fapi.service.impl;

import com.netcracker.savenko.fapi.models.Post;
import com.netcracker.savenko.fapi.models.User;
import com.netcracker.savenko.fapi.service.PostService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.support.PageableExecutionUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class PostServiceImpl implements PostService {
    @Value("${backend.server.url}")
    private String backendServerUrl;

    @Override
    public List<Post> getAll() {
        RestTemplate restTemplate = new RestTemplate();
        Post[] postResponse = restTemplate.getForObject(backendServerUrl + "/api/post/", Post[].class);
        return postResponse == null ? Collections.emptyList() : Arrays.asList(postResponse);
    }

    @Override
    public List<Post> getPostBySub(int userId) {
        RestTemplate restTemplate = new RestTemplate();
        Post[] postResponse = restTemplate.getForObject(backendServerUrl + "/api/post/followers/" + userId, Post[].class);
        return postResponse == null ? Collections.emptyList() : Arrays.asList(postResponse);
    }

    @Override
    public List<Post> getPostByCurrUser(int userId) {
        RestTemplate restTemplate = new RestTemplate();
        Post[] postResponse = restTemplate.getForObject(backendServerUrl + "/api/post/currUser/" + userId, Post[].class);
        return postResponse == null ? Collections.emptyList() : Arrays.asList(postResponse);
    }

    @Override
    public Post getPostById(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/post/" + id, Post.class);
    }

    @Override
    public Integer getCountPostByUserId(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/post/count/user/" + id, Integer.class);
    }

    @Override
    public Post savePost(Post post) {
        RestTemplate restTemplate = new RestTemplate();
        if (post.getUserByIdUser() == null) {
            User user = restTemplate.getForObject(backendServerUrl + "/api/user/" + post.getIdUser(), User.class);
            post.setUserByIdUser(user);
        }
        return restTemplate.postForEntity(backendServerUrl + "/api/post", post, Post.class).getBody();
    }

    @Override
    public void deletePost(Integer id) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(backendServerUrl + "/api/post/" + id);
    }

    @Override
    public Post getPostByPostIdAndComplaintId(int postId, int complaintId){
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(backendServerUrl + "/api/post/complaint/" + postId + "/" + complaintId, Post.class);
    }

    private Pageable createPageable(Integer page, Integer size) {
        Pageable pageable;
        pageable = PageRequest.of(page, size);
        return pageable;
    }
}