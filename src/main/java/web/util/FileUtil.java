/*  FileUtil 유틸리티 클래스 | rw 25-04-23 생성
    - 파일 업로드, 다운로드, 삭제 기능을 제공하는 유틸리티 클래스입니다.
    - 업로드는 MultipartFile 기반으로 처리되며, UUID를 이용한 중복 방지 파일명 생성 방식 적용
    - 다운로드는 바이너리 스트림을 통한 응답, 삭제는 File.exists() + delete() 방식 사용
*/

package web.util;

// [ * ] 파일, 스트림 관련 클래스 import
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.UUID;

@Component // 스프링 빈으로 등록
public class FileUtil { // CS

    // [1] 업로드 경로 설정 | 프로젝트 절대경로 + 정적 리소스 upload 디렉토리
    String baseDir = System.getProperty("user.dir");
    String uploadPath = baseDir + "/build/resources/main/static/upload/";

    // [2]. 파일 업로드 메서드 | rw 25-04-23 생성
    public String fileUpload(MultipartFile multipartFile) { // fs
        // (1) UUID + 원본 파일명을 조합하여 업로드 파일명 생성
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + multipartFile.getOriginalFilename().replaceAll("_", "-");
        String filePath = uploadPath + fileName;

        // (2) 업로드 폴더가 없으면 생성
        File folder = new File(uploadPath);
        if (!folder.exists()) folder.mkdir();

        // (3) 실제 업로드 처리
        try {
            multipartFile.transferTo(new File(filePath));
        } catch (IOException e) {
            System.out.println(e);
            return null;
        }
        return fileName;
    } // fe

    // [3]. 파일 다운로드 메서드 | rw 25-04-23 생성
    public void fileDownload(String filename, HttpServletResponse response) { // fs
        String downloadPath = uploadPath + filename;
        File file = new File(downloadPath);
        if (!file.exists()) return; // 파일이 존재하지 않으면 리턴

        try {
            FileInputStream fin = new FileInputStream(downloadPath);
            byte[] bytes = new byte[(int) file.length()];
            fin.read(bytes);
            fin.close();

            // 다운로드 응답 헤더 설정
            String oldFilename = URLEncoder.encode(filename.split("_")[1], "UTF-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + oldFilename);

            ServletOutputStream fout = response.getOutputStream();
            fout.write(bytes);
            fout.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    } // fe

    // [4]. 파일 삭제 메서드 | rw 25-04-23 생성
    public boolean fileDelete(String filename) { // fs
        String filePath = uploadPath + filename;
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    } // fe

} // CE