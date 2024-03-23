package houserent.repository;

import houserent.dto.response.PostResponseAll;
import houserent.dto.response.PostResponseOne;
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
    @Query("select new houserent.dto.response.PostResponseAll(p.image, p.description, p.persons, c.mark, a, p.favorite, p.book) " +
            "from Post p join p.address a join p.comments c")
    List<PostResponseAll> getAll();

    @Query("select new houserent.dto.response.PostResponseOne(p.title, p.image, p.hometype, p.persons, a, p.description, u.name, u.email, r, p.favorite, p.book, c) from Post p join p.address a join p.comments c join p.users u join u.rentInfo r where p.id =:postId")
    PostResponseOne findPostId(Long postId);
}
