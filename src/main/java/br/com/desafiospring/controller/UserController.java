package br.com.desafiospring.controller;

import br.com.desafiospring.erro.UserFollowException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController {

    @PostMapping("/{userId}/follow/{userIdToFollow}")
    public ResponseEntity<String> follow(@RequestBody int userId, int userIdToFollow) {

            return new ResponseEntity<String>("User followed", HttpStatus.OK);

//            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);

    }
}
