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
import org.springframework.http.HttpStatus;
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

    public ResponseEntity<ClientDTO> createNewClient ( Client client ) {
        Client response = clientRepository.save(client);
        return new ResponseEntity<>(ClientDTO.builder().id(response.getId()).username(response.getUsername()).email(response.getEmail()).build(), HttpStatus.CREATED);
    }

    public ResponseEntity<?> createNewSeller ( Seller seller ) {
        Seller response = sellerRepository.save(seller);

        if (response != null)
            return new ResponseEntity<>(SellerDTO.builder().id(response.getId()).username(response.getUsername()).email(response.getEmail()).build(), HttpStatus.CREATED);

        return new ResponseEntity<>("Error to create a new seller", HttpStatus.BAD_REQUEST);
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

        if ( client != null)
            return ClientDTO.builder().id(client.getId()).username(client.getUsername()).email(client.getEmail()).type("client").build();

        return null;
    }

    public SellerDTO getSeller(int userId) {
        Seller seller = sellerRepository.findById(userId).orElse(null);

        if ( seller != null )
            return SellerDTO.builder().id(seller.getId()).username(seller.getUsername()).email(seller.getEmail()).type("seller").build();

        return null;
    }

    public SellerFollowersCountDTO getFollowersFromSeller(int userId) {
        Seller seller = sellerRepository.findById(userId).orElse(null);

        if ( seller != null ) {
            List<Followers> seguidores = followersRepository.findAll().stream().filter( f -> f.getSeller_id() == seller.getId() ).collect(Collectors.toList());
            return SellerFollowersCountDTO.builder().userId(seller.getId()).userName(seller.getUsername()).followers_count(seguidores.toArray().length).build();
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
                followersDTO.setUserName(clientAux.getUsername());

                followersDTOList.add(followersDTO);
            }

            List<FollowersDTO> sortedFollowersDTOList = new ArrayList<>();

            if (order != null) {
                if ( order.equalsIgnoreCase(String.valueOf(SortOrderEnum.NAME_ASC)) ) {
                    sortedFollowersDTOList = followersDTOList.stream()
                            .sorted(Comparator.comparing(FollowersDTO::getUserName))
                            .collect(Collectors.toList());
                } else {
                    sortedFollowersDTOList = followersDTOList.stream()
                            .sorted(Comparator.comparing(FollowersDTO::getUserName).reversed())
                            .collect(Collectors.toList());
                }
            } else {
                sortedFollowersDTOList = followersDTOList;
            }

            return UserFollowersListDTO.builder()
                    .userId(seller.getId())
                    .userName(seller.getUsername())
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
                followersDTO.setUserName(sellerAux.getUsername());

                followersDTOList.add(followersDTO);
            }

            List<FollowersDTO> sortedFollowersDTOList = new ArrayList<>();

            if (order != null) {
                if ( order.equalsIgnoreCase(String.valueOf(SortOrderEnum.NAME_ASC)) ) {
                    sortedFollowersDTOList = followersDTOList.stream()
                            .sorted(Comparator.comparing(FollowersDTO::getUserName))
                            .collect(Collectors.toList());
                } else {
                    sortedFollowersDTOList = followersDTOList.stream()
                            .sorted(Comparator.comparing(FollowersDTO::getUserName).reversed())
                            .collect(Collectors.toList());
                }
            } else {
                sortedFollowersDTOList = followersDTOList;
            }

            return UserFollowersListDTO.builder()
                    .userId(client.getId())
                    .userName(client.getUsername())
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
