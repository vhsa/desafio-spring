package br.com.desafiospring.service;

import br.com.desafiospring.dtos.*;
import br.com.desafiospring.model.Client;
import br.com.desafiospring.model.Followers;
import br.com.desafiospring.model.FollowersPK;
import br.com.desafiospring.model.Seller;
import br.com.desafiospring.model.enums.SortOrderEnum;
import br.com.desafiospring.repository.ClientRepository;
import br.com.desafiospring.repository.FollowersRepository;
import br.com.desafiospring.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    FollowersRepository followersRepository;

    public ClientDTO createNewClient ( Client client ) {
        Client response = clientRepository.save(client);
        return ClientDTO.builder().id(response.getId()).username(response.getUsername()).email(response.getEmail()).build();
    }

    public SellerDTO createNewSeller ( Seller seller ) {
        Seller response = sellerRepository.save(seller);
        return SellerDTO.builder().id(response.getId()).username(response.getUsername()).email(response.getEmail()).build();
    }

    public Followers clientFollowSeller ( int userId, int userIdToFollow ) {
        Client client = clientRepository.findById(userId).orElse(null);
        Seller seller = sellerRepository.findById(userIdToFollow).orElse(null);

        if ( client != null && seller != null ) {
            return followersRepository.save(new Followers(client.getId(), seller.getId()));
        }

        return null;
    }

    public ClientDTO getClient(int userId) {
        Client client = clientRepository.findById(userId).orElse(null);
        return ClientDTO.builder().id(client.getId()).username(client.getUsername()).email(client.getEmail()).build();
    }

    public Seller getSeller(int userId) {
        Seller byId = sellerRepository.findById(userId).orElse(null);
        return byId;
    }

    public SellerFollowersCountDTO getFollowersFromSeller(int userId) {
        Seller seller = sellerRepository.findById(userId).orElse(null);

        if ( seller != null ) {
            List<Followers> seguidores = followersRepository.findAll().stream().filter( f -> f.getSeller_id() == seller.getId() ).collect(Collectors.toList());
            return SellerFollowersCountDTO.builder().userid(seller.getId()).username(seller.getUsername()).followers_count(seguidores.toArray().length).build();
        }
        return null;
    }

    public UserFollowersListDTO getAllClientsFollowersSeller(int userId, String type, String order) {

        Seller seller = sellerRepository.findById(userId).orElse(null);
        Client client = clientRepository.findById(userId).orElse(null);


        if ( seller != null && type.equals("seller")) {

            List<FollowersDTO> followersDTOList = new ArrayList<>();


            // find follow sellers
            List<Followers> followersList = followersRepository
                                                    .findAll()
                                                    .stream()
                                                    .filter(f -> f.getSeller_id() == seller.getId())
                                                    .collect(Collectors.toList());

            for ( Followers f : followersList ) {
               Client clientAux = clientRepository.findAll().stream().filter( c -> c.getId() == f.getClient_id()).findFirst().orElseThrow(null);

                FollowersDTO followersDTO = new FollowersDTO();

                followersDTO.setUserId(clientAux.getId());
                followersDTO.setUsername(clientAux.getUsername());

                followersDTOList.add(followersDTO);
            }

            List<FollowersDTO> sortedFollowersDTOList = new ArrayList<>();

            if (order != null) {
                if ( order.equalsIgnoreCase(String.valueOf(SortOrderEnum.NAME_ASC)) ) {
                    sortedFollowersDTOList = followersDTOList.stream()
                            .sorted(Comparator.comparing(FollowersDTO::getUsername))
                            .collect(Collectors.toList());
                } else {
                    sortedFollowersDTOList = followersDTOList.stream()
                            .sorted(Comparator.comparing(FollowersDTO::getUsername).reversed())
                            .collect(Collectors.toList());
                }
            } else {
                sortedFollowersDTOList = followersDTOList;
            }

            return UserFollowersListDTO.builder()
                    .userId(seller.getId())
                    .username(seller.getUsername())
                    .followers(sortedFollowersDTOList)
                    .build();

        } else if ( client != null && type.equals("client")) {

            List<FollowersDTO> followersDTOList = new ArrayList<>();

            // find follow sellers
            List<Followers> followersList = followersRepository.findAll().stream().filter(f -> f.getClient_id() == client.getId()).collect(Collectors.toList());

            for ( Followers f : followersList ) {

                Seller sellerAux = sellerRepository.findAll().stream().filter( s -> s.getId() == f.getSeller_id()).findFirst().orElseThrow(null);

                FollowersDTO followersDTO = new FollowersDTO();

                followersDTO.setUserId(sellerAux.getId());
                followersDTO.setUsername(sellerAux.getUsername());

                followersDTOList.add(followersDTO);
            }

            List<FollowersDTO> sortedFollowersDTOList = new ArrayList<>();

            if (order != null) {
                if ( order.equalsIgnoreCase(String.valueOf(SortOrderEnum.NAME_ASC)) ) {
                    sortedFollowersDTOList = followersDTOList.stream()
                            .sorted(Comparator.comparing(FollowersDTO::getUsername))
                            .collect(Collectors.toList());
                } else {
                    sortedFollowersDTOList = followersDTOList.stream()
                            .sorted(Comparator.comparing(FollowersDTO::getUsername).reversed())
                            .collect(Collectors.toList());
                }
            } else {
                sortedFollowersDTOList = followersDTOList;
            }

            return UserFollowersListDTO.builder()
                    .userId(client.getId())
                    .username(client.getUsername())
                    .followed(sortedFollowersDTOList)
                    .build();

        }
        return null;
    }

    public void unfollow(int userId, int userIdToUnfollow) {

        Client client = clientRepository.findById(userId).orElse(null);
        Seller seller = sellerRepository.findById(userIdToUnfollow).orElse(null);

        if ( client != null & seller != null ) {
            followersRepository.delete(new Followers(userId, userIdToUnfollow));
//            return ResponseEntity.ok().body("Unfollow succeed");
        }

//        return ResponseEntity.badRequest().body("Failed to unfollow the user");
    }
}
