package br.com.desafiospring.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostRequest {
    private int id_post;
    private int category;
    private LocalDate date;
    private double price;
    private int product_id;
    private int user_id;
}
