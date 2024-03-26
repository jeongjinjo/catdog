package com.example.catdog.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePasswordRequest {
    private String member_id;
    private String newPassword;
    private String confirmPassword;
}
