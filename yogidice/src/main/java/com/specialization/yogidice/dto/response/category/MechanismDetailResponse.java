package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MechanismDetailResponse")
public class MechanismDetailResponse extends BaseResponseBody {
    private Long id;

    private String name;

    private String parentsMec;

    private String description;
    public static MechanismDetailResponse of(Integer statusCode, String message, MechanismResponse mechanismResponse) {
        MechanismDetailResponse response = new MechanismDetailResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(mechanismResponse.getId());
        response.setName(mechanismResponse.getName());
        response.setParentsMec(mechanismResponse.getParentsMec());
        response.setDescription(mechanismResponse.getDescription());
        return response;
    }
}
