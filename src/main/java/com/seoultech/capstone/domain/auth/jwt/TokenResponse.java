package com.seoultech.capstone.domain.auth.jwt;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TokenResponse {

  private String accessToken;
  private String refreshToken;

}
