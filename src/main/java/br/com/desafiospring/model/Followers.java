package br.com.desafiospring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "client_follow_seller")
@IdClass(value = FollowersPK.class)
public class Followers implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private int client_id;
    @Id
    private int seller_id;
}
