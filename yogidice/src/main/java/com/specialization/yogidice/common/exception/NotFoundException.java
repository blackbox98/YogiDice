package com.specialization.yogidice.common.exception;

public class NotFoundException extends RuntimeException {
    public static final String USER_NOT_FOUND = "등록되지 않은 사용자입니다.";
    public static final String BOARDGAME_NOT_FOUND = "등록되지 않은 보드게임입니다.";
    public static final String CATEGORY_NOT_FOUND = "등록되지 않은 카테고리(테마)입니다.";
    public static final String CATEGORY_LIST_NOT_FOUND = "등록된 카테고리(테마)가 없습니다.";
    public static final String TYPE_NOT_FOUND = "등록되지 않은 타입입니다.";
    public static final String TYPE_LIST_NOT_FOUND = "등록된 타입이 없습니다.";
    public static final String MECHANISM_NOT_FOUND = "등록되지 않은 진행방식입니다.";
    public static final String MECHANISM_LIST_NOT_FOUND = "등록된 진행방식이 없습니다.";
    public static final String HISTORY_NOT_FOUND = "등록되지 않은 기록입니다.";
    public static final String HISTORY_LIST_NOT_FOUND = "게임 기록이 없습니다.";
    public static final String BOOKMARK_NOT_FOUND = "등록되지 않은 북마크 입니다.";
    public static final String BOARDGAMECAFE_NOT_FOUND = "등록되지 않은 보드게임 카페입니다.";
    public static final String CAFEGAME_NOT_FOUND = "해당 카페에 등록되지 않은 보드게임입니다.";
    public static final String BGGDATA_NOT_FOUND = "등록되지 않은 BGG 평점 데이터입니다.";
    public static final String NUMOFREVIEW_NOT_FOUND = "등록되지 않은 리뷰 수 데이터입니다";

    public NotFoundException(String message) {
        super(message);
    }
}