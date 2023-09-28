package com.hng.stage.five.save_video_on_disk;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@Service
public class DiskVideoSaverServiceImpl implements DiskVideoSaverService{

    @Override public ApiResponse uploadFile(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        String uploadedFileLocation = "C:\\Users\\user\\Videos\\Hng_5_files\\";
       File fileDirectory =  new File(uploadedFileLocation);
       if(!fileDirectory.exists())fileDirectory.mkdir();
       try {
           file.transferTo(new File(uploadedFileLocation + fileName));
       }catch (IOException ioException){
           return badResponse("File uploading was unsuccessful...");
       }
        return okResponse("File uploaded ....");
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
