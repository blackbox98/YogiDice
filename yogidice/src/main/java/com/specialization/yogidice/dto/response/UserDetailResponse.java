package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.type.Reviewed;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UserDetailResponse")
public class UserDetailResponse extends BaseResponseBody {
    private Long id;

    private String kakaoId;

    private String nickName;

    private Reviewed reviewed;

    private List<HistoryResponse> historyResponses;

    private List<BookmarkResponse> bookmarkResponses;

    public static UserDetailResponse of(Integer statusCode, String message, UserResponse userResponse) {
        UserDetailResponse response = new UserDetailResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(userResponse.getId());
        response.setKakaoId(userResponse.getKakaoId());
        response.setNickName(userResponse.getNickName());
        response.setReviewed(userResponse.getReviewed());
        response.setHistoryResponses(userResponse.getHistoryResponses());
        response.setBookmarkResponses(userResponse.getBookmarkResponses());
        return response;
    }
}
