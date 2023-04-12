package com.specialization.yogidice.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardGamePickRequest {

    @NotNull
    @ApiModelProperty(name="인원수 ", notes = "1: 1, 2: 2, 3: 3, 4:4, 5:5, 6:6, 7: 7 이상", example = "3")
    private Integer question1;

    @NotNull
    @ApiModelProperty(name="난이도", notes = "1: 1이하, 2:1초과 2이하,  3: 2초과 3이하, 4: 3초과 4이하 , 5: 4초과 5이하" , example = "3")
    private Integer question2;

    @NotNull
    @ApiModelProperty(name="매커니즘", notes = "1: 조건 , 2:말,  3: 파티, 4: 전략 , 5: 경제, 6: 추리 카드 퍼즐" , example = "3")
    private Integer question3;

    @NotNull
    @ApiModelProperty(name="시간", notes = "1: 30 이하, 2: 30 초과 60이하,  3: 60초과 120이하, 4: 120초과 240이하, 5: 240초과" , example = "3")
    private Integer question4;

    @NotNull
    @ApiModelProperty(name="발매년도", notes = "1: 2010 이하 옛날 감성, 2: 2010 초과 2018 ,  3: 2018~" , example = "3")
    private Integer question5;

}
