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
    private int userid;
    private String username;
    private int followers_count;
}
