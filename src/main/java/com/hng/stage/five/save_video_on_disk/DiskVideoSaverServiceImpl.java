package com.hng.stage.five.save_video_on_disk;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class DiskVideoSaverServiceImpl implements  DiskVideoSaverService{


    @Autowired
    private AmazonS3 amazonS3;
    @Value("${awsBucketName}")
    private String bucketName;
    @Value("${awsObjectKey}")
    private String objectKey;
    public ApiResponse uploadFile( MultipartFile file) throws IOException {

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketName, objectKey +   file.getOriginalFilename(), inputStream, metadata));
        }

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccessful(true);
        apiResponse.setData("File uploaded successfully!");
        return apiResponse;
    }

    @Override public InputStream renderUploadedVideo(String videoId) {
        return null;
    }
}
