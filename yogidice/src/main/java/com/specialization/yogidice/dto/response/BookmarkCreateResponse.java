package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BookmarkCreateResponse")
public class BookmarkCreateResponse extends BaseResponseBody {
    private Long id;

    public static BookmarkCreateResponse of(Integer statusCode, String message, Long bookmarkId) {
        BookmarkCreateResponse response = new BookmarkCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(bookmarkId);
        return response;
    }
}
