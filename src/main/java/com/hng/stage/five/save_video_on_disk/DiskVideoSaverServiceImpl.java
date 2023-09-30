package com.hng.stage.five.save_video_on_disk;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

@Service
public class DiskVideoSaverServiceImpl implements DiskVideoSaverService{

    @Value("${cloudinary.cloud-name}")
    private String cloudName;
    @Value("${cloudinary.api-key}")
    private String apiKey;
    @Value("${cloudinary.api-secret}")
    private String apiSecret;

   private final Map uploadingDetails = ObjectUtils.asMap( "resource_type", "video");




    @Override public ApiResponse uploadFile(MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
        Map result ;
    try {
         result= cloudinary.uploader().upload(file.getBytes(), uploadingDetails);

    }catch (IOException ioException){
         return badResponse(ioException.getMessage());
    }
       return okResponse(result.get("url").toString());
    }

    @Override
    public InputStream renderUploadedVideo(String videoId) {

        Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
                "cloud_name", cloudName,
                "api_key", apiKey,
                "api_secret", apiSecret
        ));
        String videoUrl = cloudinary.url().resourceType("video").generate(videoId);
        try {
            // Open a connection to the Cloudinary URL
            URL url = new URL(videoUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Get the input stream for the video
            InputStream videoStream = new BufferedInputStream(connection.getInputStream());
            return videoStream;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private ApiResponse okResponse(Object date){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(date); apiResponse.setSuccessful(true);
        return apiResponse;
    }
 private ApiResponse badResponse(Object date){
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(date); apiResponse.setSuccessful(false);
        return apiResponse;
    }
}
