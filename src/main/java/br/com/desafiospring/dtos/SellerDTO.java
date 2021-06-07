package br.com.desafiospring.dtos;

import br.com.desafiospring.model.Client;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerDTO {

    private int id;
    private String username;
    private String email;
    private String type = "seller";
    private List<Client> clientList;

}
