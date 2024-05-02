package com.unleash.consultationservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateContactDto {
    private String name;
    private String email;
    private String contact;
    private int referenceId;
    private String accountNo;
    private String ifcCode;

}
