package com.hng.stage.five.save_video_on_disk;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface DiskVideoSaverService {

    ApiResponse uploadFile(MultipartFile file);

    Resource renderUploadedVideo(String filename);
}
