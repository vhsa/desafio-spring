package br.com.desafiospring.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowedPostsListDTO {
    private int userId;
    private List<NewPostDTO> posts;
}
