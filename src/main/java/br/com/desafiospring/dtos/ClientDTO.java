package br.com.desafiospring.dtos;

import br.com.desafiospring.model.Seller;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientDTO {
    private int id;
    private String username;
    private String email;
    private String type = "client";
    private List<Seller> following;
}
