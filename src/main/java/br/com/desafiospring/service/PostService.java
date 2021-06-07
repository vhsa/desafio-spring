package br.com.desafiospring.service;

import br.com.desafiospring.dtos.FollowedPostsListDTO;
import br.com.desafiospring.dtos.NewPostDTO;
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

    public ResponseEntity<Response<NewPostDTO>> createNewPost(Post post) {

        // verify if seller exists
        int seller_id = post.getSeller().getId();

        Seller seller = sellerRepository.findById(seller_id).orElse(null);

        // seller exists
        if ( seller != null ) {

            // already exists a product, so we need update
//            if ( post.getDetail().getProduct_id() != 0 && post.getId_post() != 0 ) {
//
//                ProductDetail productDetail = productDetailRepository.findById(post.getDetail().getProduct_id()).orElse(null );
//
//                if ( productDetail != null ) {
//
//                    ProductDetail newProduct = productDetailRepository.save(post.getDetail());
//
//                    return NewPostDTO.builder()
//                            .userId(post.getSeller().getId())
//                            .id_post(post.getId_post())
//                            .date(post.getDate())
//                            .detail(newProduct)
//                            .category(post.getCategory())
//                            .price(post.getPrice())
//                            .build();
//                }
//            }
//
//            if ( post.getId_post() != 0 ) { // already exits a post
//                Post updatingPost = postRepository.findById(post.getId_post()).orElse(null);
//
//                if (updatingPost != null) {
//                    Post updatedPost = postRepository.save(post);
//
//                    return NewPostDTO.builder()
//                            .userId(updatedPost.getSeller().getId())
//                            .id_post(updatedPost.getId_post())
//                            .date(updatedPost.getDate())
//                            .detail(updatedPost.getDetail())
//                            .category(updatedPost.getCategory())
//                            .price(updatedPost.getPrice())
//                            .build();
//                }
//            }

            // save a new post
            Post newPost = postRepository.save(post);

            return ResponseEntity.ok().body(new Response<>(NewPostDTO.builder()
                    .userId(newPost.getSeller().getId())
                    .id_post(newPost.getId_post())
                    .date(newPost.getDate())
                    .detail(newPost.getDetail())
                    .category(newPost.getCategory())
                    .price(newPost.getPrice())
                    .build()));

//            } else {
                // response with "error to save a new product"
//            }
        }
        return ResponseEntity.badRequest().body(new Response<>("Seller not exists"));
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
                        post.getSeller().getId(),
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
}
