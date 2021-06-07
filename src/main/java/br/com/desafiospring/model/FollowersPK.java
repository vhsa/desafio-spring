package br.com.desafiospring.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
public class FollowersPK implements Serializable {
    private static final long serialVersionUID = 1L;
    private int client_id;
    private int seller_id;
}
