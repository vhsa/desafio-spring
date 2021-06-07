package br.com.desafiospring.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "client")
public class Client implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;

    @Column(unique = true)
    private String email;

    @ManyToMany()
    @JoinTable(name = "client_follow_seller", joinColumns = @JoinColumn(name = "client_id"), inverseJoinColumns = @JoinColumn(name = "seller_id"))
    private List<Seller> seller;

}
