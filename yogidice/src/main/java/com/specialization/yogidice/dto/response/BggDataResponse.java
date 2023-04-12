package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.BggData;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BggDataResponse")
public class BggDataResponse {
    private Long id;

    private Long bggCode;

    private String nickName;

    private double ratingUser;

    public static BggDataResponse response(
            BggData bggData
    ) {
        return new BggDataResponse(
                bggData.getId(),
                bggData.getBggCode(),
                bggData.getNickName(),
                bggData.getRatingUser()
        );
    }
}
