package br.com.desafiospring.controller;

import br.com.desafiospring.dtos.*;
import br.com.desafiospring.model.Client;
import br.com.desafiospring.model.Seller;
import br.com.desafiospring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    // CREATE A NEW CLIENT
    @PostMapping("/client")
    public ResponseEntity<ClientDTO> createNewClient (@RequestBody Client client ) {

        if ( userService.createNewClient( client ) != null ) {
            return new ResponseEntity(userService.createNewClient( client ), HttpStatus.CREATED);
        }
        return new ResponseEntity("Something went wrong to create a new client", HttpStatus.BAD_REQUEST);
    }

    // CREATE A NEW SELLER
    @PostMapping("/seller")
    public ResponseEntity<SellerDTO> createNewSeller (@RequestBody Seller seller ) {

        if ( userService.createNewSeller( seller ) != null )
            return new ResponseEntity<>(userService.createNewSeller( seller ), HttpStatus.CREATED).getBody();

        return new ResponseEntity("Something went wrong to create a new seller", HttpStatus.BAD_REQUEST);
    }

    // CLIENT FOLLOW SELLER
    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<?> follow (@PathVariable int userId, @PathVariable int userIdToFollow ) {

            if ( userService.clientFollowSeller(userId, userIdToFollow) != null ) {
                return ResponseEntity.ok().body("Followed success");
            }
            return ResponseEntity.badRequest().body("Something went wrong to follow the user");
    }

    @GetMapping("/client/{userId}")
    public ResponseEntity<ClientDTO> getClient (@PathVariable int userId) {

        if ( userService.getClient(userId) != null )
            return new ResponseEntity(userService.getClient(userId), HttpStatus.OK);

        return new ResponseEntity("Client not found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/seller/{userId}")
    public ResponseEntity<Seller> getSeller (@PathVariable int userId) {

        if ( userService.getSeller(userId) != null )
            return new ResponseEntity(userService.getSeller(userId), HttpStatus.OK);

        return new ResponseEntity("Seller not found", HttpStatus.BAD_REQUEST);
    }

    // GET NUMBER OF FOLLOWERS THAT FOLLOWING SOME SELLER
    @GetMapping("/{userId}/followers/count")
    public ResponseEntity<SellerFollowersCountDTO> getFollowersFromSeller (@PathVariable int userId) {

        if ( userService.getFollowersFromSeller(userId) != null )
            return new ResponseEntity(userService.getFollowersFromSeller(userId), HttpStatus.OK);

        return new ResponseEntity("Seller not found", HttpStatus.BAD_REQUEST);
    }

    // GET LIST OF ALL CLIENTS THAT FOLLOWS SOME SELLER
    @GetMapping("/{userId}/followers/list")
    public ResponseEntity<UserFollowersListDTO> getAllClientsFollowersSeller (@PathVariable int userId, @RequestParam String type, @RequestParam(required = false) String order) {

        if ( userService.getAllClientsFollowersSeller(userId, type, order) != null ) {
            return new ResponseEntity(userService.getAllClientsFollowersSeller(userId, type, order), HttpStatus.OK);
        }

        return new ResponseEntity("Seller not found", HttpStatus.BAD_REQUEST);
    }

    // unfollow some seller
    @PostMapping("/{userId}/unfollow/{userIdToUnfollow}")
    public void unfollow (@PathVariable int userId, @PathVariable int userIdToUnfollow) {
        userService.unfollow(userId, userIdToUnfollow);
    }

































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
