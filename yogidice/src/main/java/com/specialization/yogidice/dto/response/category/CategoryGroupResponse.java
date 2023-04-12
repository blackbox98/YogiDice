package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.domain.entity.category.CategoryGroup;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CategoryGroupResponse")
public class CategoryGroupResponse {
    private Long cateId;

    private String categoryName;

    public static CategoryGroupResponse response(CategoryGroup categoryGroup) {
        return new CategoryGroupResponse(
                categoryGroup.getCategory().getId(),
                categoryGroup.getCategory().getName()
        );
    }
}
