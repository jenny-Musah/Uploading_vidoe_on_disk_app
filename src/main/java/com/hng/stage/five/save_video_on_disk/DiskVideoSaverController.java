package com.hng.stage.five.save_video_on_disk;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
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



    @PostMapping("upload")
    public ResponseEntity<ApiResponse> uploadVideoToDisk(@RequestParam("file")MultipartFile file){
        return ResponseEntity.ok(diskVideoSaverService.uploadFile(file));
    }

    @GetMapping("{videoId}")
    public void renderVideo(@PathVariable String videoId, HttpServletResponse response){
        InputStream videoStream = diskVideoSaverService.renderUploadedVideo(videoId);
        response.setHeader(HttpHeaders.CONTENT_TYPE, "video/mp4"); // Set the appropriate content type
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=video.mp4"); // Set the file name
try {
    byte[] buffer = new byte[1024];
    int bytesRead;
    while ((bytesRead = videoStream.read(buffer)) != -1) {
        response.getOutputStream().write(buffer, 0, bytesRead);
    }
}catch (IOException ioException){
    throw new RuntimeException(ioException.getMessage());
}
    }


}
