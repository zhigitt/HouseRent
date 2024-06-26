package houserent.repository;

import houserent.dto.response.*;
import houserent.entity.Post;
import houserent.entity.enums.HomeType;
import houserent.entity.enums.Region;
import houserent.exception.NotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    default Post getByIds(Long postId) {
        return findById(postId).orElseThrow(() -> new NotFoundException("Нет такой id post " + postId));
    }


    @Query("SELECT new houserent.dto.response.PostResponseAlls(p.id,p.title,p.price,p.description, p.persons,MAX(c.mark), a.city, a.region, a.street, p.favorite, p.book) " +
            "FROM Post p LEFT JOIN p.comments c JOIN p.address a group by p.id, p.title, p.price, p.description, p.persons, a.city, a.region, a.street, p.favorite, p.book")
    List<PostResponseAlls> getAll();

//    @Query("select new houserent.dto.response.PostResponseOne(p.title, p.image, p.hometype, p.persons, a.city, a.region, a.street, p.description, u.name, u.email, r, p.favorite, p.book) from Post p join p.address a left join p.comments c left join p.users u left join u.rentInfo r where p.id = :postId")
//    PostResponseOne findPostId(Long postId);


    @Query("SELECT new houserent.dto.response.PostResponseAlls(p.id, p.title, p.price, p.description, p.persons, MAX(c.mark), a.city, a.region, a.street, p.favorite, p.book) " +
            "FROM Post p " +
            "LEFT JOIN p.comments c " +
            "JOIN p.address a " +
            "WHERE LOWER(p.title) LIKE LOWER(concat('%', :word, '%')) " +
            "OR LOWER(a.city) LIKE LOWER(concat('%', :word, '%')) " +
            "OR LOWER(a.region) LIKE LOWER(concat('%', :word, '%')) " +
            "GROUP BY p.id, p.title, p.price, p.description, p.persons, a.city, a.region, a.street, p.favorite, p.book " +
            "ORDER BY p.title")
    List<PostResponseAlls> search(String word);

    @Query("SELECT new houserent.dto.response.PostResponseAlls(p.id,p.title,p.price,p.description, p.persons,MAX(c.mark), a.city, a.region, a.street, p.favorite, p.book) " +
            "FROM Post p LEFT JOIN p.comments c JOIN p.address a where a.region =:region group by p.id, p.title, p.price, p.description, p.persons, a.city, a.region, a.street, p.favorite, p.book")
    List<PostResponseAlls> sortReqion(Region region);

    @Query("SELECT new houserent.dto.response.PostResponseAlls(p.id, p.title, p.price, p.description, p.persons, MAX(c.mark), a.city, a.region, a.street, p.favorite, p.book) " +
            "FROM Post p " +
            "LEFT JOIN p.comments c " +
            "JOIN p.address a " +
            "WHERE p.hometype = :homeType " +
            "GROUP BY p.id, p.title, p.price, p.description, p.persons, a.city, a.region, a.street, p.favorite, p.book")
    List<PostResponseAlls> filterHouseAndApartment(HomeType homeType);

    @Query("select new houserent.dto.response.PostResponseAlls(p.id,p.title,p.price, p.description, p.persons, MAX(c.mark), a.city, a.region, a.street, p.favorite, p.book)" +
            " FROM Post p LEFT JOIN p.comments c JOIN p.address a group by p.id,p.title,p.price, p.description, p.persons,a.city, a.region, a.street, p.favorite, p.book order by  case when :word ='asc' then p.price end asc ,case when :word ='desc' then p.price end desc")
    List<PostResponseAlls> priceFilter(String word);

    @Query("select p from Post p")
    Page<Post> getAllPage(Pageable pageable);

    @Query("select new houserent.dto.response.PostVendorAll(p.id, u.name, u.email, p.title, p.price, p.description, p.persons, MAX (c.mark), a.city, a.region, a.street, r.chekin, r.chekOut) " +
            "from Post p " +
            "join p.address a " +
            "left join p.comments c " +
            "join p.users u " +
            " left join u.rentInfos r where p.id =:postId group by p.id, u.name, u.email, p.title, p.price, p.description, p.persons,a.city, a.region, a.street, r.chekin, r.chekOut ")
    PostVendorAll vendorAllPost(Long postId);




    @Query("select new houserent.dto.response.PostAnnouncementAll(p.id, u.name, u.email, p.title, p.price, p.description, p.persons, MAX(c.mark), a.city, a.region, a.street) " +
            "from Post p " +
            "join p.users u " +
            "join p.address a " +
            "left join p.comments c " +
            "group by p.id, u.name, u.email, p.title, p.price, p.description, p.persons, a.city, a.region, a.street")
    List<PostAnnouncementAll> announcement();

    @Query("""
            select new houserent.dto.response.FavoritePost(
            p.id,
            p.title,
            p.hometype,
            p.persons,
            a.city,
            a.region,
            a.street,
            p.description)
            from Post p join p.address a
            where p.id = :postId
            """)
    FavoritePost favoriteVendor(Long postId);

    @Query("select p from Post p where p.id=:postId")
    Post findPostId(Long postId);

    @Query(value = "select i.images from post_images i where i.post_id =:id",nativeQuery = true)
    List<String> findImage(Long id);

@Modifying
@Transactional
@Query(value = "delete from in_favorites_posts where posts_id =:postId",nativeQuery = true)
    void deleteInfavorite(Long postId);

    @Modifying
    @Transactional
    @Query("delete from RentInfo r where r.post.id =:postId")
    void deleteRent(Long postId);
    @Modifying
    @Transactional
    @Query(value = "delete from users_booking where booking_id =:postId",nativeQuery = true)
    void deleteBook(Long postId);
    @Modifying
    @Transactional
    @Query(value = "delete from users_favorite_basket where favorite_basket_id =:postId",nativeQuery = true)
    void deleteBasket(Long postId);
}
