package itmo.is.service.domain;

import itmo.is.model.domain.ImportHistory;
import itmo.is.repository.ImportHistoryRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportHistoryService {

    @Autowired
    private ImportHistoryRepository importHistoryRepository;

    @Transactional
    public void recordImportHistory(Long userId, String status, int addedCount,String name,String fileName) {
        ImportHistory history = new ImportHistory();
        history.setUserId(userId);
        history.setStatus(status);
        history.setAddedCount(addedCount);
        history.setUserName(name);
        history.setFileName(fileName);
        importHistoryRepository.save(history);
    }

    public List<ImportHistory> getImportHistoryForUser(Long userId) {
        return importHistoryRepository.findByUserId(userId);
    }

    public List<ImportHistory> getAllImportHistory() {
        return importHistoryRepository.findAll();
    }
}
