package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("KakaoUserResponse")
public class KakaoUserResponse extends BaseResponseBody {
    private String kakaoId;
    private String nickName;

    public static KakaoUserResponse of(Integer statusCode, String message, KakaoUserResponse kakaoUserResponse) {
        KakaoUserResponse response = new KakaoUserResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setKakaoId(kakaoUserResponse.getKakaoId());
        response.setNickName(kakaoUserResponse.getNickName());
        return response;
    }
}
