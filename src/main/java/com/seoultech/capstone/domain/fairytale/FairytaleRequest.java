package com.seoultech.capstone.domain.fairytale;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FairytaleRequest {
    private String title;
    private String background;
    private String characters;
    private String content;
}

