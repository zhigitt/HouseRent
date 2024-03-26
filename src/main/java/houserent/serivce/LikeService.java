package houserent.serivce;

public interface LikeService {

    void addLikes(Long commentId);
    void disLikes(Long commentId);
}
