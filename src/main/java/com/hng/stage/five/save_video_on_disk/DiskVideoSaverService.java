package com.hng.stage.five.save_video_on_disk;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

public interface DiskVideoSaverService {

    ApiResponse uploadFile(MultipartFile file) throws IOException;

    InputStream renderUploadedVideo(String videoId);
}
