package br.com.desafiospring.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerFollowersCountDTO {
    private int userId;
    private String userName;
    private int followers_count;
}
