package br.com.desafiospring.dtos;

import br.com.desafiospring.model.Client;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SellerDTO {

    private int id;
    private String username;
    private String email;
    private String type = "seller";
    private List<Client> clientList;

}
