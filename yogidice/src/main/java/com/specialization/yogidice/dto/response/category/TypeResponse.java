package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.domain.entity.category.Type;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TypeResponse")
public class TypeResponse {
    private Byte id;

    private String name;

    public static TypeResponse response(Type type) {
        return new TypeResponse(
                type.getId(),
                type.getName()
        );
    }
}
