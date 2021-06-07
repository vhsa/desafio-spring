package br.com.desafiospring.model;

import br.com.desafiospring.serializer.CustomClientSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "seller")
public class Seller implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;
    public String username;

    @Column(unique = true)
    public String email;

    @JsonSerialize(using = CustomClientSerializer.class)
    @ManyToMany(mappedBy = "seller")
    private List<Client> client;

    // fazer um one to many p acessar todos os posts do vendedor

//    @JsonIgnore
//    public List<Client> getClient() {
//        return client;
//    }
}
