package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.BoardGameCafe;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BoardGameCafeResponse")
public class BoardGameCafeResponse {
    private Long id;

    private String name;

    private String address;

    public static BoardGameCafeResponse response(BoardGameCafe cafe) {
        return new BoardGameCafeResponse(
                cafe.getId(),
                cafe.getName(),
                cafe.getAddress()
        );
    }
}
