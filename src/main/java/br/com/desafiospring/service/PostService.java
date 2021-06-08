package br.com.desafiospring.service;

import br.com.desafiospring.dtos.FollowedPostsListDTO;
import br.com.desafiospring.dtos.NewPostDTO;
import br.com.desafiospring.dtos.PromoCountProductsDTO;
import br.com.desafiospring.dtos.Response;
import br.com.desafiospring.model.Client;
import br.com.desafiospring.model.Post;
import br.com.desafiospring.model.Seller;
import br.com.desafiospring.model.enums.SortOrderEnum;
import br.com.desafiospring.repository.ClientRepository;
import br.com.desafiospring.repository.PostRepository;
import br.com.desafiospring.repository.ProductDetailRepository;
import br.com.desafiospring.repository.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostService {

    @Autowired
    PostRepository postRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    ClientRepository clientRepository;

    public ResponseEntity<NewPostDTO> createNewPost(Post post) {

        // verify if seller exists
        int seller_id = post.getSeller().getId();

        Seller seller = sellerRepository.findById(seller_id).orElse(null);

        if ( seller != null ) {
            // save a new post
            Post newPost = postRepository.save(post);

            return new ResponseEntity(NewPostDTO.builder()
                    .userId(newPost.getSeller().getId())
                    .id_post(newPost.getId_post())
                    .date(newPost.getDate())
                    .detail(newPost.getDetail())
                    .category(newPost.getCategory())
                    .price(newPost.getPrice())
                    .build(), HttpStatus.OK);
        }
        return new ResponseEntity("Seller not exists", HttpStatus.BAD_REQUEST);
    }

    public FollowedPostsListDTO getPostsForFollowedSeller(int userId, String order) {

        Client client = clientRepository.findById(userId).orElse(null);

        if ( client != null ) {

            List<Seller> sellerFollowed = client.getSeller();
            List<Post> listPostsFromSeller = new ArrayList<>();

            NewPostDTO newPost = null;
            List<NewPostDTO> listNewPost = new ArrayList<>();
            List<NewPostDTO> sortedListNewPost = new ArrayList<>();

            for ( Seller sl : sellerFollowed) {
                listPostsFromSeller.addAll(postRepository.findAll()
                        .stream().filter(s -> s.getSeller().getId() == sl.getId())
                        .sorted(Comparator.comparing(Post::getDate).reversed())
                        .collect(Collectors.toList()));
            }

            for ( Post post : sortTwoWeeksList(listPostsFromSeller) ) {
                newPost = new NewPostDTO(
                        null,
                        post.getId_post(),
                        post.getDate(),
                        post.getDetail(),
                        post.getCategory(),
                        post.getPrice()
                    );

                listNewPost.add(newPost);
            }

            // desc
            if ( order != null )
                if ( order.equalsIgnoreCase(String.valueOf(SortOrderEnum.DATE_ASC)))
                    sortedListNewPost = listNewPost.stream().sorted(Comparator.comparing(NewPostDTO::getDate)).collect(Collectors.toList());
                else
                    sortedListNewPost = listNewPost.stream().sorted(Comparator.comparing(NewPostDTO::getDate).reversed()).collect(Collectors.toList());
            else
                sortedListNewPost = listNewPost;

            return FollowedPostsListDTO.builder()
                    .userId(userId)
                    .posts(sortedListNewPost)
                    .build();
        }
        return null;
    }

    protected List<Post> sortTwoWeeksList (List<Post> postList) {
        return postList
                .stream()
                .filter(p -> p.getDate().plusWeeks(2).isAfter(LocalDate.now()) || p.getDate().plusWeeks(2).isEqual(LocalDate.now())).collect(Collectors.toList());
    }

    public ResponseEntity<NewPostDTO> createNewPromoPost(Post post) {

        int seller_id = post.getSeller().getId();

        Seller seller = sellerRepository.findById(seller_id).orElse(null);

        if ( seller != null ) {
            Post newPromoPost = postRepository.save(post);

            return new ResponseEntity(NewPostDTO.builder()
                    .userId(newPromoPost.getSeller().getId())
                    .id_post(newPromoPost.getId_post())
                    .date(newPromoPost.getDate())
                    .detail(newPromoPost.getDetail())
                    .category(newPromoPost.getCategory())
                    .price(newPromoPost.getPrice())
                    .hasPromo(newPromoPost.isHasPromo())
                    .discount(newPromoPost.getDiscount())
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity("Seller not found", HttpStatus.BAD_REQUEST);

    }

    public ResponseEntity getPromoCountProducts(int userId) {

        Seller seller = sellerRepository.findById(userId).orElse(null);

        if ( seller != null ) {

            // find a products from seller
            List<Post> postList = postRepository.findAll().stream().filter( p -> p.getSeller().getId() == seller.getId() && p.isHasPromo()).collect(Collectors.toList());

            return new ResponseEntity(PromoCountProductsDTO.builder()
                    .userId(userId)
                    .userName(seller.getUsername())
                    .promoproducts_count(postList.size())
                    .build(), HttpStatus.OK);
        }

        return new ResponseEntity("Seller not found", HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity allPromoProductsFromSpecificSeller (int userId) {

        Seller seller = sellerRepository.findById(userId).orElse(null);

        if ( seller != null ) {
            List<Post> postList = postRepository.findAll().stream().filter( p -> p.getSeller().getId() == seller.getId() && p.isHasPromo()).collect(Collectors.toList());

            NewPostDTO newPostDTO = null;

            List<NewPostDTO> newPostDTOList = new ArrayList<>();

            for (Post p : postList) {
                newPostDTO = new NewPostDTO(
                        null,
                        p.getId_post(),
                        p.getDate(),
                        p.getDetail(),
                        p.getCategory(),
                        p.getPrice(),
                        p.isHasPromo(),
                        p.getDiscount()
                );

                newPostDTOList.add(newPostDTO);
            }

            return new ResponseEntity(
                    FollowedPostsListDTO.builder()
                    .userId(userId)
                    .userName(seller.getUsername())
                    .posts(newPostDTOList)
                    .build()
                    ,HttpStatus.OK);
        }
        return new ResponseEntity("Seller not found", HttpStatus.BAD_REQUEST);
    }
}
