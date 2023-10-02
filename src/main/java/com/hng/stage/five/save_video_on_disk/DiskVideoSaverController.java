package com.hng.stage.five.save_video_on_disk;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/api/video/")
@CrossOrigin(origins = "*")
public class DiskVideoSaverController {

    @Autowired
    private DiskVideoSaverService diskVideoSaverService;

    @Value("${awsBucketName}")
    private String bucketName;


    @Autowired
    private AmazonS3 amazonS3;

    @PostMapping("upload")
    public ResponseEntity<ApiResponse> uploadVideoToDisk(@RequestParam("file")MultipartFile file) throws IOException {
        return ResponseEntity.ok(diskVideoSaverService.uploadFile(file));
    }

    @GetMapping()
    public void renderVideo(@RequestParam("objectKey") String objectKey, HttpServletResponse response)  {
        diskVideoSaverService.renderUploadedVideo(objectKey,response);
    }

}
