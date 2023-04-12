package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UserRegistResponse")
public class UserRegistResponse extends BaseResponseBody {
    private boolean isRegisted;

    public static UserRegistResponse of(Integer statusCode, String message, UserRegistResponse userRegistResponse) {
        UserRegistResponse response = new UserRegistResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setRegisted(userRegistResponse.isRegisted());
        return response;
    }
}
