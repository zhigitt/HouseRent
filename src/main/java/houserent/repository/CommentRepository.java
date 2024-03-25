package houserent.repository;

import houserent.dto.response.CommentResponse;
import houserent.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long> {

    @Query("select new houserent.dto.response.CommentResponse(c.id,c.comment,c.date,c.mark) from Comment  c")
    List<CommentResponse> getAll();

    @Query("select new houserent.dto.response.CommentResponse(c.id,c.comment,c.date,c.mark) from Comment c join c.user u where u.id =:id ")
    List<CommentResponse> findCommentsByUserId(Long id);

    @Query("delete from Comment c where c.user.id =:id")
    void deleteComWithUserId(Long id);

    @Query("select new houserent.dto.response.CommentResponse(c.id,c.comment,c.date,c.mark) from Comment c join c.post p where p.id =:id")
    List<CommentResponse> findCommentsByPostId(Long id);

    @Query("delete from Comment c where c.post.id =:id")
    void deleteComWithPostId(Long id);
}
