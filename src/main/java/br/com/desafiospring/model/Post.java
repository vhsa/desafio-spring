package br.com.desafiospring.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "post")
@SequenceGenerator(name = "table_sequence", initialValue = 1, sequenceName = "sequence_post")
public class Post implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence_post")
    private int id_post;
    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private ProductDetail detail;
    private int category;
    private double price;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Seller seller;

}
