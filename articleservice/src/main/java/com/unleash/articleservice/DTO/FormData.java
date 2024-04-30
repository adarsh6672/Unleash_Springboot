package com.unleash.articleservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


@Data
@NoArgsConstructor
public class FormData {

    private MultipartFile image;
    private String title;
    private int relatedTo;
    private String content;
    private String counselorName;

}
