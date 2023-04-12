package com.specialization.yogidice.dto.request.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeGroupUpdateRequest {
    @NotNull
    private Byte typeId;
}
