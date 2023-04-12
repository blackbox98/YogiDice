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
@ApiModel("TypeGroupListResponse")
public class TypeGroupListResponse extends BaseResponseBody {
    private List<TypeGroupResponse> responses;

    public static TypeGroupListResponse of(Integer statusCode, String message, List<TypeGroupResponse> typeGroupResponses) {
        TypeGroupListResponse response = new TypeGroupListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(typeGroupResponses);
        return response;
    }
}
