package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.domain.entity.category.Mechanism;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("MechanismResponse")
public class MechanismResponse {
    private Long id;

    private String name;

    private String parentsMec;

    private String description;

    public static MechanismResponse response(Mechanism mechanism) {
        return new MechanismResponse(
                mechanism.getId(),
                mechanism.getName(),
                mechanism.getParentsMec(),
                mechanism.getDescription()
        );
    }
}
