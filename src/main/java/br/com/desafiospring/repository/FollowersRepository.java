package br.com.desafiospring.repository;

import br.com.desafiospring.model.Followers;
import br.com.desafiospring.model.FollowersPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FollowersRepository extends JpaRepository<Followers, FollowersPK> {}
