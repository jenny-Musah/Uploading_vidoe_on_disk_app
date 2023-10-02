package com.hng.stage.five.save_video_on_disk;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Service
public class DiskVideoSaverServiceImpl implements  DiskVideoSaverService {

    @Autowired
    private AmazonS3 amazonS3;
    @Value("${awsBucketName}")
    private String bucketName;
    @Value("${awsObjectKey}")
    private String objectKey;

    public ApiResponse uploadFile(MultipartFile file) throws IOException {

        objectKey += file.getOriginalFilename();
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());

        try (InputStream inputStream = file.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucketName, objectKey, inputStream, metadata));
        }
        return okResponse("ObjectKey:" + objectKey);
    }

    @Override public void renderUploadedVideo(String objectKey, HttpServletResponse response) {

        S3Object s3Object = amazonS3.getObject(bucketName, objectKey);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();

        response.setContentType(s3Object.getObjectMetadata().getContentType());
        response.setHeader("Content-Disposition", "attachment; filename=\"" + objectKey + "\"");
        try {

            // Stream the object content to the response output stream
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                response.getOutputStream().write(buffer, 0, bytesRead);
            }
            inputStream.close();
            response.getOutputStream().flush();
        } catch (IOException ioException) {
            throw new RuntimeException(ioException.getMessage());

        }
    }


    private ApiResponse okResponse(Object data) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccessful(true);
        apiResponse.setData(data);
    return apiResponse;
    }

    public static ApiResponse badResponse(Object data){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setSuccessful(false);
        apiResponse.setData(data);
        return apiResponse;
    }

}
