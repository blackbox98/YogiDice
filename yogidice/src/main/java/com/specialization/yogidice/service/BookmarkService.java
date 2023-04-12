package com.specialization.yogidice.service;

import com.specialization.yogidice.common.exception.DuplicateException;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.Bookmark;
import com.specialization.yogidice.domain.entity.User;
import com.specialization.yogidice.domain.repository.BoardGameRepository;
import com.specialization.yogidice.domain.repository.BookmarkRepository;
import com.specialization.yogidice.domain.repository.UserRepository;
import com.specialization.yogidice.domain.repository.category.MechanismGroupRepository;
import com.specialization.yogidice.dto.request.BookmarkRequest;
import com.specialization.yogidice.dto.response.BookmarkResponse;
import com.specialization.yogidice.dto.response.category.MechanismGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.specialization.yogidice.common.exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookmarkService {
    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;
    private final BookmarkRepository bookmarkRepository;
    private final MechanismGroupRepository mechanismGroupRepository;

    @Transactional
    public Long createBookmark(Long userId, BookmarkRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        BoardGame boardGame = boardGameRepository.findById(request.getGameId())
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        if (bookmarkRepository.findByUserAndBoardGame(user, boardGame).isPresent()) {
            throw new DuplicateException(String.format("%s 은/는 이미 북마크에 등록된 보드게임입니다.", boardGame.getTitleKr()));
        }
        Bookmark saveBookmark = Bookmark.create(user,boardGame);
        return bookmarkRepository.save(saveBookmark).getId();
    }

    @Transactional
    public List<BookmarkResponse> readBookmarkListOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
        if (bookmarks.isEmpty()) {
            return new ArrayList<>();
        }
        List<BookmarkResponse> responses = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            List<MechanismGroupResponse> mechanismGroupResponses = mechanismGroupRepository.findByBoardGame(bookmark.getBoardGame()).stream()
                    .map(MechanismGroupResponse::response)
                    .collect(Collectors.toList());
            responses.add(BookmarkResponse.response(bookmark, mechanismGroupResponses));
        }
        return responses;
    }

    @Transactional
    public void deleteBookmark(Long userId, Long gameId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        BoardGame boardGame = boardGameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        Bookmark bookmark = bookmarkRepository.findByUserAndBoardGame(user, boardGame)
                .orElseThrow(() -> new NotFoundException(BOOKMARK_NOT_FOUND));
        bookmarkRepository.delete(bookmark);
    }
}
