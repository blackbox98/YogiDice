package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BookmarkListResponse")
public class BookmarkListResponse extends BaseResponseBody {
    private List<BookmarkResponse> responses;

    public static BookmarkListResponse of(Integer statusCode, String message, List<BookmarkResponse> bookmarkResponses) {
        BookmarkListResponse response = new BookmarkListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(bookmarkResponses);
        return response;
    }
}
