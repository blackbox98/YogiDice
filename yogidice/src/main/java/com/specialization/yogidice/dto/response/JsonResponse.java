package com.specialization.yogidice.dto.response;




import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * json으로 응답하기 위한 응답값 정의
 */
@Getter
@Setter
@ApiModel("JsonResponse")
public class JsonResponse extends BaseResponseBody {

    @ApiModelProperty(name = "Json")
    String json;

    public static JsonResponse of(Integer statusCode, String message, String json) {
        JsonResponse jsonResponse = new JsonResponse();
        jsonResponse.setStatusCode(statusCode);
        jsonResponse.setMessage(message);
        jsonResponse.setJson(json);
        return jsonResponse;
    }

}

