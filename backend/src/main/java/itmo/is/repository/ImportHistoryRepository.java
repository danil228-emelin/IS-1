package itmo.is.repository;

import itmo.is.model.domain.ImportHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImportHistoryRepository extends JpaRepository<ImportHistory, Long> {
    List<ImportHistory> findByUserId(Long userId); // Для получения только операций текущего пользователя

    List<ImportHistory> findAll();  // Для получения всех операций (доступно только администраторам)
}