package br.com.desafiospring.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Entity
@Table(name = "product")
@SequenceGenerator(name = "table_sequence", initialValue = 1,sequenceName = "sequence_product")
public class ProductDetail implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "sequence_product")
    private int product_id;
    private String productName;
    private String type;
    private String brand;
    private String color;
    private String notes;

//    @OneToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "post_id")
//    private Post post;

}
