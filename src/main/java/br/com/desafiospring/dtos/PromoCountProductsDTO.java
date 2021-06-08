package br.com.desafiospring.dtos;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PromoCountProductsDTO {
    private int userId;
    private String userName;
    private int promoproducts_count;
}
