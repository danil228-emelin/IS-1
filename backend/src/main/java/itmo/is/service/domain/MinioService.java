package itmo.is.service.domain;

import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@Slf4j
public class MinioService {
    @Autowired
    private MinioClient minioClient;

    public void uploadFile(File file,String fileName) throws Exception {
        log.info("uploadFile Minio  started");
        String bucketName = "your-bucket-name";
        if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket("your-bucket-name").build())) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("your-bucket-name").build());
            log.info("uploadFile Minio create bucket");

        }
        try (FileInputStream fileInputStream = new FileInputStream(file)) {
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName) // Using file.getName() for the object key
                    .stream(fileInputStream, file.length(), -1) // Use the InputStream
                    .build());

            log.info("uploadFile MinIO finished successfully");
        } catch (IOException e) {
            log.error("Error occurred while uploading the file: {}", e.getMessage());
            throw e; // Re-throw or handle as needed
        }
    }
}
