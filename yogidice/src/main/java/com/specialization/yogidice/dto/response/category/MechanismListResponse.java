package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MechanismListResponse")
public class MechanismListResponse extends BaseResponseBody {
    private List<MechanismResponse> responses;

    public static MechanismListResponse of(Integer statusCode, String message, List<MechanismResponse> mechanismResponses) {
        MechanismListResponse response = new MechanismListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(mechanismResponses);
        return response;
    }
}
