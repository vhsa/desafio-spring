package br.com.desafiospring.controller;

import br.com.desafiospring.dtos.FollowedPostsListDTO;
import br.com.desafiospring.dtos.NewPostDTO;
import br.com.desafiospring.dtos.Response;
import br.com.desafiospring.model.Post;
import br.com.desafiospring.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class PostController {

    @Autowired
    PostService postService;

    @PostMapping("/newpost")
    public ResponseEntity<Response<NewPostDTO>> createNewPost(@RequestBody Post post ) {

        if (postService.createNewPost(post) != null)
            return postService.createNewPost(post);
        else
            return ResponseEntity.badRequest().body(new Response<>("Something went wrong"));
    }

    // getting post for a followed seller
    @GetMapping("/followed/{userId}/list")
    public FollowedPostsListDTO getPostsForFollowedSeller (@PathVariable int userId, @RequestParam(required = false) String order ) {
        return postService.getPostsForFollowedSeller(userId, order);
    }
}
