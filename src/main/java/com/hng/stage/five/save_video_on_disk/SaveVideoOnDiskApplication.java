package com.hng.stage.five.save_video_on_disk;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class SaveVideoOnDiskApplication{



    public static void main(String[] args) {
        SpringApplication.run(SaveVideoOnDiskApplication.class, args);
    }


    @Bean
    public AmazonS3 amazonS3() {
        return AmazonS3ClientBuilder.defaultClient();
    }

}
