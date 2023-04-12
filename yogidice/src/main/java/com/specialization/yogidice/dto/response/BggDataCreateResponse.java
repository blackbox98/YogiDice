package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BggDataCreateResponse")
public class BggDataCreateResponse extends BaseResponseBody {
    private Long id;

    public static BggDataCreateResponse of(Integer statusCode, String message, Long bggDataId) {
        BggDataCreateResponse response = new BggDataCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(bggDataId);
        return response;
    }
}
