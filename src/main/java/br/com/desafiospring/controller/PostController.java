package br.com.desafiospring.controller;

import br.com.desafiospring.dtos.FollowedPostsListDTO;
import br.com.desafiospring.dtos.NewPostDTO;
import br.com.desafiospring.dtos.PromoCountProductsDTO;
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

    // create a promo post
    @PostMapping("/newpromopost")
    public ResponseEntity<NewPostDTO> createNewPromoPost(@RequestBody Post post) {
        return postService.createNewPromoPost(post);
    }

    @GetMapping("/{userId}/countPromo")
    public ResponseEntity<PromoCountProductsDTO> getPromoCountProducts (@PathVariable int userId) {
        return postService.getPromoCountProducts(userId);
    }

    @GetMapping("/{userId}/list")
    public ResponseEntity<FollowedPostsListDTO> allPromoProductsFromSpecificSeller (@PathVariable int userId) {
        return postService.allPromoProductsFromSpecificSeller(userId);
    }
}
