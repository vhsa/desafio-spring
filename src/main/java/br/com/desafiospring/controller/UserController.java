package br.com.desafiospring.controller;

import br.com.desafiospring.dtos.*;
import br.com.desafiospring.model.Client;
import br.com.desafiospring.model.Seller;
import br.com.desafiospring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    // CREATE A NEW CLIENT
    @PostMapping("/client")
    public ResponseEntity<Response<ClientDTO>> createNewClient (@RequestBody Client client ) {

        if ( userService.createNewClient( client ) != null ) {
            return ResponseEntity.ok().body(new Response<>(userService.createNewClient( client )));
        }
        return ResponseEntity.badRequest().body(new Response<>("Something went wrong to create a new client"));
    }

    // CREATE A NEW SELLER
    @PostMapping("/seller")
    public ResponseEntity<Response<SellerDTO>> createNewClient (@RequestBody Seller seller ) {
        return ResponseEntity.ok(new Response<SellerDTO>(userService.createNewSeller( seller )));
    }

    // CLIENT FOLLOW SELLER
    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> follow (@PathVariable int userId, @PathVariable int userIdToFollow ) {

            if ( userService.clientFollowSeller(userId, userIdToFollow) != null ) {
                return ResponseEntity.ok().body(new Response<>("Followed success"));
            }
            return ResponseEntity.badRequest().body(new Response<>("Something went wrong to follow the user"));
    }

    @GetMapping("/client/{userId}")
    public ResponseEntity<Response<ClientDTO>> getClient (@PathVariable int userId) {
        return ResponseEntity.ok().body(new Response<ClientDTO>(userService.getClient(userId)));
    }

    @GetMapping("/seller/{userId}")
    public ResponseEntity<Response<Seller>> getSeller (@PathVariable int userId) {
        return ResponseEntity.ok().body(new Response<Seller>(userService.getSeller(userId)));
    }

    // GET NUMBER OF FOLLOWERS THAT FOLLOWING SOME SELLER
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<Response<SellerFollowersCountDTO>> getFollowersFromSeller (@PathVariable int userId) {
        return ResponseEntity.ok().body(new Response<SellerFollowersCountDTO>( userService.getFollowersFromSeller(userId)));
    }

    // GET LIST OF ALL CLIENTS THAT FOLLOWS SOME SELLER
    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<Response<UserFollowersListDTO>> getAllClientsFollowersSeller (@PathVariable int userId, @RequestParam String type, @RequestParam(required = false) String order) {

        if ( userService.getAllClientsFollowersSeller(userId, type, order) != null ) {
            return ResponseEntity.ok().body(new Response<>( userService.getAllClientsFollowersSeller(userId, type, order) ));
        }

        return ResponseEntity.badRequest().body(new Response<>("Seller not found"));
    }

    // unfollow some seller
    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public void unfollow (@PathVariable int userId, @PathVariable int userIdToUnfollow) {
        userService.unfollow(userId, userIdToUnfollow);
    }

















    // CREATE A NEW SELLER
//    public ResponseEntity<Response<Seller>> createNewSeller ( Seller )



































//    @Autowired
//    UserService userService;
//
//    @Autowired
//    FollowsService followsService;
//
//    @PostMapping
//    public ResponseEntity<Response<UserDTO>> createNewUser(@RequestBody User user) {
//
//        if ( userService.createNewUser(user) != null ) {
//            return ResponseEntity.ok(new Response<UserDTO>(userService.createNewUser(user)));
//        }
//        return ResponseEntity.badRequest().body(new Response<>("Something went wrong"));
//    }
//
//    @PostMapping("/{userId}/follow/{userIdToFollow}")
//    public ResponseEntity<Response<Follows>> follow (@PathVariable Long userId, @PathVariable Long userIdToFollow ) {
//
//        if ( followsService.followUser(userId, userIdToFollow) != null ) {
//            return ResponseEntity.ok(new Response<Follows>(followsService.followUser(userId, userIdToFollow)));
//        }
//        return ResponseEntity.badRequest().body(new Response<Follows>("Something went wrong to follow the user"));
//    }

}
