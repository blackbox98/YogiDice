package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.domain.entity.category.Category;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CategoryResponse")
public class CategoryResponse {
    private Long id;

    private String name;

    public static CategoryResponse response(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}
