package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.Bookmark;
import com.specialization.yogidice.dto.response.category.MechanismGroupResponse;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BookmarkResponse")
public class BookmarkResponse {
    private Long id;

    private Long userId;

    private String nickName;

    private Long gameId;

    private String gameTitle;

    private String thumbUrl;

    private List<MechanismGroupResponse> mechanismGroupResponses;

    public static BookmarkResponse response(Bookmark bookmark, List<MechanismGroupResponse> mechanismLists) {
        return new BookmarkResponse(
                bookmark.getId(),
                bookmark.getUser().getId(),
                bookmark.getUser().getNickName(),
                bookmark.getBoardGame().getId(),
                bookmark.getBoardGame().getTitleKr(),
                bookmark.getBoardGame().getThumbUrl(),
                mechanismLists
        );
    }
}
