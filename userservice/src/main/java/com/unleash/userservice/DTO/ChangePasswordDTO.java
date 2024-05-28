package com.unleash.userservice.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ChangePasswordDTO {
    String oldPassword;
    String newPassword;
}
