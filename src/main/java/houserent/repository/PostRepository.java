package houserent.repository;

import houserent.dto.response.PostResponseAlls;
import houserent.dto.response.PostResponseOne;
import houserent.entity.Address;
import houserent.entity.Post;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;
import houserent.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
   default Post getByIds(Long postId){
       return findById(postId).orElseThrow(() -> new NotFoundException("Нет такой id post " + postId));
   }


    @Query("SELECT new houserent.dto.response.PostResponseAlls(p.title,p.image,p.price, p.description, p.persons, c.mark, a.city, a.region, a.street, p.favorite, p.book) " +
            "FROM Post p LEFT JOIN p.comments c JOIN p.address a")
    List<PostResponseAlls> getAll();

    @Query("select new houserent.dto.response.PostResponseOne(p.title, p.image, p.hometype, p.persons, a.city,a.region,a.street, p.description, u.name, u.email, r, p.favorite, p.book, c) from Post p join p.address a left join p.comments c  join p.users u left join u.rentInfo r where p.id =:postId")
    PostResponseOne findPostId(Long postId);


    @Query("SELECT new houserent.dto.response.PostResponseAlls(p.title,p.image,p.price, p.description, p.persons, c.mark, a.city, a.region, a.street, p.favorite, p.book) " +
            "FROM Post p LEFT JOIN p.comments c JOIN p.address a where LOWER(p.title) like LOWER(concat('%',:word,'%')) order by p.title")

    PostResponseAlls search(String word);
    @Query("SELECT new houserent.dto.response.PostResponseAlls(p.title,p.image,p.price ,p.description, p.persons, c.mark, a.city, a.region, a.street, p.favorite, p.book) " +
            "FROM Post p LEFT JOIN p.comments c JOIN p.address a where a.region =:region")
    List<PostResponseAlls> sortReqion(Region region);

    @Query("SELECT new houserent.dto.response.PostResponseAlls(p.title, p.image,p.price, p.description, p.persons, c.mark, a.city, a.region, a.street, p.favorite, p.book) " +
            "FROM Post p LEFT JOIN p.comments c JOIN p.address a where p.hometype = :homeType")
    List<PostResponseAlls> filterHouseAndApartment(HomeType homeType);

    @Query("select new houserent.dto.response.PostResponseAlls(p.title, p.image,p.price, p.description, p.persons, c.mark, a.city, a.region, a.street, p.favorite, p.book) FROM Post p LEFT JOIN p.comments c JOIN p.address a order by      case when :word ='asc' then p.price end asc ,case when :word ='desc' then p.price end desc")
    List<PostResponseAlls> priceFilter(String word);
@Query("select p from Post p")
    Page<Post> getAllPage(Pageable pageable);


//    @Query("SELECT new java12.dto.response.MenuSearchResponse(s, m.name, m.price, m.description, m.isVegetarian) FROM MenuItem m JOIN m.subcategories s ORDER BY CASE WHEN :word = 'asc' THEN m.price END ASC, CASE WHEN :word = 'desc' THEN m.price END DESC")
//    List<MenuSearchResponse> sort(String word);
}
