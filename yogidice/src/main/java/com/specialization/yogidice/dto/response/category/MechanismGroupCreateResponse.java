package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MechanismGroupCreateResponse")
public class MechanismGroupCreateResponse extends BaseResponseBody {
    private Long id;

    public static MechanismGroupCreateResponse of(Integer statusCode, String message, Long mechanismGroupId) {
        MechanismGroupCreateResponse response = new MechanismGroupCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(mechanismGroupId);
        return response;
    }
}
