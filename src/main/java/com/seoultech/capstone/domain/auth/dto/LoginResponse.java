package com.seoultech.capstone.domain.auth.dto;

import lombok.*;

@Data
@NoArgsConstructor
public class LoginResponse {

  private String type;
  private String accessToken;
  private String refreshToken;

  public LoginResponse(String type, String accessToken, String refreshToken) {
    this.type = type;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
