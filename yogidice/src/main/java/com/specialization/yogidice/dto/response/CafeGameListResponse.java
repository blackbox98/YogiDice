package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CafeGameListResponse")
public class CafeGameListResponse extends BaseResponseBody {
    private List<CafeGameResponse> responses;

    public static CafeGameListResponse of(Integer statusCode, String message, List<CafeGameResponse> cafeGameResponses) {
        CafeGameListResponse response = new CafeGameListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(cafeGameResponses);
        return response;
    }
}
