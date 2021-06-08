package br.com.desafiospring.dtos;

import br.com.desafiospring.model.ProductDetail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
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

    // promo post attributes
    private boolean hasPromo;
    private double discount;

    public NewPostDTO(int id, int id_post, LocalDate date, ProductDetail detail, int category, double price) {
        this.userId = id;
        this.id_post = id_post;
        this.date = date;
        this.detail = detail;
        this.category = category;
        this.price = price;
    }
}
