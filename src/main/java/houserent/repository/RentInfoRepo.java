package houserent.repository;

import houserent.entity.RentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentInfoRepo extends JpaRepository<RentInfo, Long> {
}
