package br.com.desafiospring.dtos;

import br.com.desafiospring.model.ProductDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewPostDTO {
    private int userId;
    private int id_post;
    private LocalDate date;
    private ProductDetail detail;
    private int category;
    private double price;
}
