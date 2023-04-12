package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BoardGameCafeDetailResponse")
public class BoardGameCafeDetailResponse extends BaseResponseBody {
    private Long id;

    private String name;

    private String address;

    public static BoardGameCafeDetailResponse of(Integer statusCode, String message, BoardGameCafeResponse boardGameCafeResponse) {
        BoardGameCafeDetailResponse response = new BoardGameCafeDetailResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(boardGameCafeResponse.getId());
        response.setName(boardGameCafeResponse.getName());
        response.setAddress(boardGameCafeResponse.getAddress());
        return response;
    }
}
