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
@ApiModel("MechanismGroupListResponse")
public class MechanismGroupListResponse extends BaseResponseBody {
    private List<MechanismGroupResponse> responses;

    public static MechanismGroupListResponse of(Integer statusCode, String message, List<MechanismGroupResponse> mechanismGroupResponses) {
        MechanismGroupListResponse response = new MechanismGroupListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(mechanismGroupResponses);
        return response;
    }
}
