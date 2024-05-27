package com.unleash.base_domain.Dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NotificationDto {
    private int userId;
    private String fullName;
    private String mailId;
    private String subject;
    private String message;
}
