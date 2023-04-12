package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MechanismCreateResponse")
public class MechanismCreateResponse extends BaseResponseBody {
    private Long id;

    public static MechanismCreateResponse of(Integer statusCode, String message, Long mechanismId) {
        MechanismCreateResponse response = new MechanismCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(mechanismId);
        return response;
    }
}
