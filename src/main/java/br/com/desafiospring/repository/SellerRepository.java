package br.com.desafiospring.repository;

import br.com.desafiospring.model.SellerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SellerRepository extends JpaRepository<SellerModel, Long> {}
