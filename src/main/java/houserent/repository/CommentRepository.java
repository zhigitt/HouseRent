package houserent.repository;

import houserent.dto.response.CommentResponse;
import houserent.entity.Comment;
import houserent.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select new houserent.dto.response.CommentResponse(c.id,c.comment,c.date,c.image,c.mark) from Comment  c")
    List<CommentResponse> getAll();

    @Query("select new houserent.dto.response.CommentResponse(c.id,c.comment,c.date,c.image,c.mark) from Comment c join c.user u where u.id =:id ")
    List<CommentResponse> findCommentsByUserId(Long id);

    @Query("delete from Comment c where c.user.id =:id")
    void deleteComWithUserId(Long id);

    @Query("select new houserent.dto.response.CommentResponse(c.id,c.comment,c.date,c.image,c.mark) from Comment c join c.post p where p.id =:id")
    List<CommentResponse> findCommentsByPostId(Long id);

    @Query("SELECT c FROM Comment c JOIN c.post p WHERE p = :post")
    List<Comment> findByPost(Post post);
}
