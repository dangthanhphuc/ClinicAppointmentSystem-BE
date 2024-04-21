package com.example.main.utils;

import com.example.main.exceptions.PayloadTooLargeException;
import com.example.main.exceptions.UnsupportedMediaTypeException;
import com.example.main.responses.ResponseObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;


public class StoreImageFile {
    // Kiểm tra định dạng có phải là image fule không
    private static void isImageFile(MultipartFile file) throws UnsupportedMediaTypeException, PayloadTooLargeException {

        // Kiểm tra dịnh dạng file có phải file ảnh không
        if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
            throw new UnsupportedMediaTypeException();
        }

        // Kiểm tra kích thước < 10MB
        if (file.getSize() > 10 * 1024 * 1024) {
            throw new PayloadTooLargeException();
        }

    }

    // Trả về tên image file
    public static String storeImage(MultipartFile file) throws IOException, UnsupportedMediaTypeException, PayloadTooLargeException {
        // Kiểm tra đầu vào nếu sai thì ném lỗi
        isImageFile(file);
        // Lấy tên file
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        // Thêm UUID vào trước tên file để đảm bảo tên file là duy nhất
        String uniqueFilename = UUID.randomUUID().toString() + '_' + filename;
        // Đường dẫn đến thư mục bạn muốn lưu file
        Path uploadDir = Paths.get("uploads");
        // Kiểm tra và tạo thư mục nếu nó không tồn tại
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //Đường dẫn đầy đủ đến file
        Path destination = Paths.get(uploadDir.toString(), uniqueFilename);
        // Sao chép file vào thư mục đích
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFilename;
    }
}
