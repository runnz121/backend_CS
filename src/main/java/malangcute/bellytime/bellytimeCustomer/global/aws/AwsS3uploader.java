package malangcute.bellytime.bellytimeCustomer.global.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import malangcute.bellytime.bellytimeCustomer.global.exception.FailedToConvertImgFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
@AllArgsConstructor
public class AwsS3uploader {

    private static final String FILE_DELIMITER = "_";

    private static final String URL_DELIMITER ="/";

    private static final String BUCKET = "bellytime";

    private static final String CLOUDFRONT_URL = "https://d1utjczuqj7mhr.cloudfront.net" ;

    private final AmazonS3 amazonS3;


    public String upload(MultipartFile multipartFile) throws FailedToConvertImgFileException {
        File uploadFile = convertToFile(multipartFile);
        String fileName = createFileName(uploadFile);
        uploadToS3(uploadFile, fileName);
        return CLOUDFRONT_URL + URL_DELIMITER + fileName;
    }

    // 멀티파트파일을 파일로 변경
    private File convertToFile(MultipartFile multipartFile) throws FailedToConvertImgFileException {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
           throw new FailedToConvertImgFileException("이미지 변환에 실패하였습니다 에러 메시지 : " + e);
        }
        return file;
    }

    //업로드할 파일이름을 생성
    private String createFileName(File uploadFile) {
        Date date = new Date();
        return date +
                FILE_DELIMITER +
                uploadFile.getName() +
                FILE_DELIMITER +
                UUID.randomUUID();
    }

    //버킷 이름, 파일 이름, 파일, 외부에서 파일 봐야됨으로 publicRead
    private void uploadToS3(File uploadFile, String filename){
        amazonS3.putObject(new PutObjectRequest(BUCKET, filename, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }
}
