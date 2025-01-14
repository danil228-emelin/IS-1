package itmo.is.controller;

import io.minio.*;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;


@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @Autowired
    private MinioClient minioClient;


    @GetMapping("/download/{filename}")
    public void downloadFile(@PathVariable String filename, HttpServletResponse response) throws Exception {
        InputStream stream = minioClient.getObject(GetObjectArgs.builder()
                .bucket("your-bucket-name")
                .object(filename)
                .build());

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");
        IOUtils.copy(stream, response.getOutputStream());
        response.flushBuffer();
    }

}


