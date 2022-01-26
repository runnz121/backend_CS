package malangcute.bellytime.bellytimeCustomer.global.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

//@Component
//@Slf4j
//@AllArgsConstructor
//public class AwsS3uploader {
//
//    private static final String FILE_DELIMITER = "_";
//
//    private final AmazonS3 amazonS3;
//
//    private final String bucket;
//
//    public String upload(MultipartFile multipartFile, String name) {
//        File uploadFile = convertToFile(multipartFile);
//
//    }
//
//    private File convertToFile(MultipartFile multipartFile) {
//        String or
//    }
//
//    private String createFileName(File uploadFile, String)
//
//    private String uploadToS3(File uploadFile, String filename){
//        amazonS3.putObject(new PutObjectRequest(bucket, filename, uploadFile)
//                .withCannedAcl(CannedAccessControlList.PublicRead));
//        return amazonS3.getUrl(bucket, filename).toString();
//    }
//
//
//}
