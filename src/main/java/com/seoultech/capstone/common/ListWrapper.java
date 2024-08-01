package com.seoultech.capstone.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListWrapper<T> {
    @Schema(description = "데이터 목록")
    private List<T> items;
}

