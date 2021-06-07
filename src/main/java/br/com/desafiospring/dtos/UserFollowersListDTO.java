package br.com.desafiospring.dtos;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class UserFollowersListDTO implements Serializable {
    private int userId;
    private String username;
    private List<FollowersDTO> followers;
}
