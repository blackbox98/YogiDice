package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.domain.entity.category.MechanismGroup;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MechanismGroupResponse")
public class MechanismGroupResponse {
    private Long mechanismId;

    private String mechanismName;

    private String parentsMec;

    private String description;

    public static MechanismGroupResponse response(MechanismGroup mechanismGroup) {
        return new MechanismGroupResponse(
                mechanismGroup.getMechanism().getId(),
                mechanismGroup.getMechanism().getName(),
                mechanismGroup.getMechanism().getParentsMec(),
                mechanismGroup.getMechanism().getDescription()
        );
    }
}
