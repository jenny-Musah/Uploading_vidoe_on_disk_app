package com.hng.stage.five.save_video_on_disk;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import static com.hng.stage.five.save_video_on_disk.DiskVideoSaverServiceImpl.badResponse;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<?> exceptionHandler(Exception e) {
        return ResponseEntity.badRequest().body(badResponse(e.getMessage()));
    }

}
