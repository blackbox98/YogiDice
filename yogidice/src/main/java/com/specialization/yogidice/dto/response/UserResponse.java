package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.User;
import com.specialization.yogidice.domain.entity.type.Reviewed;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UserResponse")
public class UserResponse {
    private Long id;

    private String kakaoId;

    private String nickName;

    private Reviewed reviewed;

    private List<HistoryResponse> historyResponses;

    private List<BookmarkResponse> bookmarkResponses;

    public static UserResponse response(
            User user,
            List<HistoryResponse> historys,
            List<BookmarkResponse> bookmarks
    ) {
        return new UserResponse(
                user.getId(),
                user.getKakaoId(),
                user.getNickName(),
                user.getReviewed(),
                historys,
                bookmarks
        );
    }
}
