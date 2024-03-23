package houserent.repository;

import houserent.dto.response.PostResponseAlls;
import houserent.dto.response.PostResponseOne;
import houserent.entity.Address;
import houserent.entity.Post;
import houserent.exception.NotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {
   default Post getByIds(Long postId){
       return findById(postId).orElseThrow(() -> new NotFoundException("Нет такой id post " + postId));
   }


    @Query("SELECT new houserent.dto.response.PostResponseAlls(p.image, p.description, p.persons, c.mark, a.city, a.region, a.street, p.favorite, p.book) " +
            "FROM Post p LEFT JOIN p.comments c JOIN p.address a")
    List<PostResponseAlls> getAll();

    @Query("select new houserent.dto.response.PostResponseOne(p.title, p.image, p.hometype, p.persons, a.city,a.region,a.street, p.description, u.name, u.email, r, p.favorite, p.book, c) from Post p join p.address a left join p.comments c  join p.users u left join u.rentInfo r where p.id =:postId")
    PostResponseOne findPostId(Long postId);
}
