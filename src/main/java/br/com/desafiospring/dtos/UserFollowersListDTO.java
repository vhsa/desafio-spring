package br.com.desafiospring.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserFollowersListDTO implements Serializable {
    private int userId;
    private String username;
    private List<FollowersDTO> followers;
    private List<FollowersDTO> followed;
}
