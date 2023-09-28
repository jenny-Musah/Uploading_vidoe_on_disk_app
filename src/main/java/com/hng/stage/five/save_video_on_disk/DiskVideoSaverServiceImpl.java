package com.hng.stage.five.save_video_on_disk;

import jdk.jfr.ContentType;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class DiskVideoSaverServiceImpl implements DiskVideoSaverService{

    private  String uploadedFileLocation = "C:\\Users\\user\\Videos\\Hng_5_files\\";
    @Override public ApiResponse uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        File fileDirectory =  new File(uploadedFileLocation);
        if(!fileDirectory.exists())fileDirectory.mkdir();
        try {
           file.transferTo(new File(uploadedFileLocation + fileName + ".mp4"));
        }catch (IOException ioException){
           return badResponse("File uploading was unsuccessful...");
        }
        return okResponse("File uploaded ....");
    }

    @Override public Resource renderUploadedVideo(String videoId) {
        String savedVideoPath = uploadedFileLocation + videoId + ".mp4";
        Resource videoResource = new FileSystemResource(savedVideoPath);
        if(videoResource.exists()) return videoResource;
        throw new RuntimeException("Video does not exist");
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
