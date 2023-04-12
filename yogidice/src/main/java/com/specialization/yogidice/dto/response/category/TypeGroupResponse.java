package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.domain.entity.category.TypeGroup;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TypeGroupResponse")
public class TypeGroupResponse {
    private Byte typeId;

    private String typeName;

    public static TypeGroupResponse response(TypeGroup typeGroup) {
        return new TypeGroupResponse(
                typeGroup.getType().getId(),
                typeGroup.getType().getName()
        );
    }
}
