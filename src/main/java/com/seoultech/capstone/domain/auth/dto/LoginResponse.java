package com.seoultech.capstone.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@NoArgsConstructor
public class LoginResponse {

  @Schema(description = "유저 타입", example = "STUDENT")
  private String type;

  @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
  private String accessToken;

  @Schema(description = "리프레시 토큰", example = "dGhpc2lzYXJlZnJlc2h0b2tlbg==")
  private String refreshToken;

  public LoginResponse(String type, String accessToken, String refreshToken) {
    this.type = type;
    this.accessToken = accessToken;
    this.refreshToken = refreshToken;
  }
}
