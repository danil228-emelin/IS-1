package itmo.is.service.domain;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import itmo.is.model.domain.ImportHistory;
import itmo.is.repository.ImportHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class MinioService {
    @Autowired
    private MinioClient minioClient;
    @Autowired
    private ImportHistoryRepository importHistoryRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void importFile(Long userId, int addedCount, String name, File file, String fileName) throws Exception {

        try {
            uploadFile(file, fileName);
            saveFileMetadataAsync(userId, addedCount, name, fileName, "COMMITTED");
        } catch (Exception e) {
            saveFileMetadataAsync(userId, addedCount, name, fileName, "FAILED MINIO");
            throw e;
        }
    }

    @Transactional
    public void saveFileMetadataAsync(Long userId, int addedCount, String name, String fileName, String status) {
        log.info("saveFileMetadataAsync start");
        try {

            ImportHistory history = new ImportHistory();
            history.setUserId(userId);
            history.setStatus(status);
            history.setAddedCount(addedCount);
            history.setUserName(name);
            history.setFileName(fileName);
            importHistoryRepository.save(history);
            log.info("saveFileMetadataAsync finish");
        } catch (Exception e) {
            ImportHistory history = new ImportHistory();
            history.setUserId(userId);
            history.setStatus("FAILED BD");
            history.setAddedCount(addedCount);
            history.setUserName(name);
            history.setFileName(fileName);
            importHistoryRepository.save(history);
            log.info("saveFileMetadataAsync finish with error");
        }
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void uploadFile(File file, String fileName) throws Exception {
        log.info("uploadFile " + fileName + " to Minio start");
        String bucketName = "your-bucket-name";
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket("your-bucket-name").build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("your-bucket-name").build());
            log.info("uploadFile Minio create bucket");

        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(fileInputStream, file.length(), -1)
                    .build());

            log.info("uploadFile MinIO finished successfully");
        } catch (IOException e) {
            log.error("Error while uploading the file to Minio: {}", e.getMessage());
            throw e;
        }
    }

    private void importRollback(ImportHistory importedFile) {
        importedFile.setStatus("ABORTED");
        importHistoryRepository.save(importedFile);
    }


}
