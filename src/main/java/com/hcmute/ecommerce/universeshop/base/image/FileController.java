package com.hcmute.ecommerce.universeshop.base.image;

import com.hcmute.ecommerce.universeshop.base.utils.FirebaseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/images")
@CrossOrigin(origins = "**", allowedHeaders = "*")
@Slf4j
public class FileController {
    private final FirebaseUtils firebaseUtils;

    @Autowired
    public FileController(FirebaseUtils firebaseUtils) {
        this.firebaseUtils = firebaseUtils;
    }

    @PostMapping
    public String saveImage(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        return firebaseUtils.uploadImage(multipartFile, multipartFile.getOriginalFilename());
    }
}
