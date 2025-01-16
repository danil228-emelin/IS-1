package itmo.is.repository;

import itmo.is.model.domain.ImportHistory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportHistoryRepository extends JpaRepository<ImportHistory, Long> {
    List<ImportHistory> findByUserId(Long userId);

    List<ImportHistory> findAll();


  // default  <S extends ImportHistory> S save(S entity){
   //    throw new DataAccessException("Simulated Database Failure") {};

   //}
}