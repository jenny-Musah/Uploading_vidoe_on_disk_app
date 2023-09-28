package com.hng.stage.five.save_video_on_disk;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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


}
