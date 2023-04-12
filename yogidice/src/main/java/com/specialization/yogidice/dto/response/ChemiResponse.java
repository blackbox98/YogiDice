package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("ChemiResponse")
public class ChemiResponse extends BaseResponseBody {
    private int response;

    public static ChemiResponse of(Integer statusCode, String message, int chemi) {
        ChemiResponse response = new ChemiResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponse(chemi);
        return response;
    }
}
