package com.seoultech.capstone.domain.fairytale.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FairytaleRequest {

    @Schema(description = "동화 제목", example = "운수 좋은 날")
    private String title;

    @Schema(description = "동화 배경", example = "조선시대")
    private String background;

    @Schema(description = "동화 등장인물", example = "{\n" +
            "  \"김첨지\": {\n" +
            "    \"성격\": \"착하면서도 가난에 찌들어 현실적이고 때로는 거칠게 행동함. 속으로는 가족에 대한 사랑이 깊음.\",\n" +
            "    \"설명\": \"인력거꾼으로서 가난하게 살지만 아내와 아이를 위해 노력하는 인물. 아내에게 설렁탕을 사주려 했으나 아내의 죽음으로 절망하게 됨.\"\n" +
            "  },\n" +
            "  \"아내\": {\n" +
            "    \"성격\": \"희생적이고 순종적이며 병약함. 마지막까지 가족을 위해 희생함.\",\n" +
            "    \"설명\": \"김첨지의 아내로서 가난과 병으로 고통받다가 끝내 설렁탕을 먹지 못하고 사망하는 인물. 그녀의 죽음은 김첨지에게 큰 충격을 줌.\"\n" +
            "  }")
    private String characters;

    @Schema(description = "동화 내용", example = "등장인물로는 주인공 김 첨지, 병으로 고통받다 죽은 아내, 그리고 김 첨지의 친구인 인력거꾼 치삼이가 있다. 김 첨지는 아내를 사랑하지만 가난과 현실의 고단함 속에서 거칠게 행동하며, 아내의 죽음으로 큰 슬픔과 절망을 겪는다.")
    private String content;
}
