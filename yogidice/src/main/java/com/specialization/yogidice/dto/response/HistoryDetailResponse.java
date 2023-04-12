package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.History;
import com.specialization.yogidice.dto.response.category.MechanismGroupResponse;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("HistoryDetailResponse")
public class HistoryDetailResponse {
    private Long Id;

    private double rating;

    private String review;

    private LocalDateTime createDate;

    private Long userId;

    private String nickName;

    private Long gameId;

    private String gameTitle;

    private String thumbUrl;

    private List<MechanismGroupResponse> mechanismGroupResponses;

    public static HistoryDetailResponse response(
            History history,
            List<MechanismGroupResponse> mechanismLists) {
        return new HistoryDetailResponse(
                history.getId(),
                history.getRating(),
                history.getReview(),
                history.getCreatedDate(),
                history.getUser().getId(),
                history.getUser().getNickName(),
                history.getBoardGame().getId(),
                history.getBoardGame().getTitleKr(),
                history.getBoardGame().getThumbUrl(),
                mechanismLists
        );
    }
}
