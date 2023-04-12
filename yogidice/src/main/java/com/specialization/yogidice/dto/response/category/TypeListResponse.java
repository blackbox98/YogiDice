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
@ApiModel("TypeListResponse")
public class TypeListResponse extends BaseResponseBody {
    private List<TypeResponse> responses;

    public static TypeListResponse of(Integer statusCode, String message, List<TypeResponse> typeResponses) {
        TypeListResponse response = new TypeListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(typeResponses);
        return response;
    }
}
