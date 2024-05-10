package com.unleash.consultationservice.DTO;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class APIResponse<T> {
    private int pageNumber;
    private int recordCount;
    private int totalPage;
    private T response;
}
