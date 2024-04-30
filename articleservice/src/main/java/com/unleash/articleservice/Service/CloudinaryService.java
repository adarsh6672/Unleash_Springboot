package com.unleash.articleservice.Service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;



    public String upload(MultipartFile file) throws IOException {

        Map result = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("folder","unleash"));
        return (String) result.get("secure_url");

    }
}
