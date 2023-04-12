package com.specialization.yogidice.service;

import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.History;
import com.specialization.yogidice.domain.entity.NumOfReview;
import com.specialization.yogidice.domain.entity.User;
import com.specialization.yogidice.domain.entity.type.Reviewed;
import com.specialization.yogidice.domain.repository.BoardGameRepository;
import com.specialization.yogidice.domain.repository.HistoryRepository;
import com.specialization.yogidice.domain.repository.NumOfReviewRepository;
import com.specialization.yogidice.domain.repository.UserRepository;
import com.specialization.yogidice.domain.repository.category.MechanismGroupRepository;
import com.specialization.yogidice.dto.request.HistoryCreateRequest;
import com.specialization.yogidice.dto.request.HistoryUpdateRequest;
import com.specialization.yogidice.dto.response.HistoryDetailResponse;
import com.specialization.yogidice.dto.response.HistoryResponse;
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
public class HistoryService {
    private final UserRepository userRepository;
    private final BoardGameRepository boardGameRepository;
    private final HistoryRepository historyRepository;
    private final NumOfReviewRepository numOfReviewRepository;
    private final MechanismGroupRepository mechanismGroupRepository;

    @Transactional
    public Long createHistory(Long userId, HistoryCreateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        BoardGame boardGame = boardGameRepository.findById(request.getGameId())
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        if (historyRepository.findByUserAndBoardGame(user, boardGame).isPresent()) {
            return historyRepository.findByUserAndBoardGame(user, boardGame).get().getId();
        }
        History saveHistory = History.create(
                user,
                boardGame
        );
        return historyRepository.save(saveHistory).getId();
    }

    @Transactional
    public List<HistoryDetailResponse> readHistoryListOfUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        List<History> histories = historyRepository.findByUser(user);
        if (histories.isEmpty()) {
            return new ArrayList<>();
        }
        List<HistoryDetailResponse> responses = new ArrayList<>();
        for (History history : histories) {
            List<MechanismGroupResponse> mechanismGroupResponses = mechanismGroupRepository.findByBoardGame(history.getBoardGame()).stream()
                    .map(MechanismGroupResponse::response)
                    .collect(Collectors.toList());
            responses.add(HistoryDetailResponse.response(history, mechanismGroupResponses));
        }
        return responses;
    }

    @Transactional
    public List<HistoryResponse> readHistoryDetail(Long userId, Long gameId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        BoardGame boardGame = boardGameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        if (historyRepository.findByUserAndBoardGame(user, boardGame).isPresent()) {
            History history = historyRepository.findByUserAndBoardGame(user, boardGame).get();
            List<HistoryResponse> responses = new ArrayList<>();
            responses.add(HistoryResponse.response(history));
            return responses;
        } else {
            return new ArrayList<>();
        }
    }

    @Transactional
    public void updateHistory(User user, Long historyId, HistoryUpdateRequest request) {
        if (user.getReviewed().equals(Reviewed.F)) {
            user.completeReview();
        }
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new NotFoundException(HISTORY_NOT_FOUND));
        NumOfReview numOfReview = numOfReviewRepository.findByBoardGame(history.getBoardGame())
                .orElseThrow(() -> new NotFoundException(NUMOFREVIEW_NOT_FOUND));
        double sum;
        int number;
        if (history.getRating() == 0) {
            sum = numOfReview.getBoardGame().getRatingUser() * numOfReview.getNumber() + request.getRating();
            number = numOfReview.getNumber() + 1;
            numOfReview.addReview();
        } else {
            sum = numOfReview.getBoardGame().getRatingUser() * numOfReview.getNumber() - history.getRating() + request.getRating();
            number = numOfReview.getNumber();
        }
        history.getBoardGame().updateRatingUser(sum / number);
        history.update(
                request.getRating(),
                request.getReview()
        );
        if (user.getReviewed() == Reviewed.F) {
            user.completeReview();
        }
        userRepository.save(user);
        historyRepository.save(history);
    }

    @Transactional
    public void deleteHistory(Long historyId) {
        History history = historyRepository.findById(historyId)
                .orElseThrow(() -> new NotFoundException(HISTORY_NOT_FOUND));
        NumOfReview numOfReview = numOfReviewRepository.findByBoardGame(history.getBoardGame())
                .orElseThrow(() -> new NotFoundException(NUMOFREVIEW_NOT_FOUND));
        double sum = numOfReview.getBoardGame().getRatingUser() * numOfReview.getNumber() - history.getRating();
        int number = numOfReview.getNumber() - 1;
        history.getBoardGame().updateRatingUser(sum / number);
        numOfReview.deleteReview();
        historyRepository.delete(history);
    }

    @Transactional
    public void deleteHistoryByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        List<History> histories = historyRepository.findByUser(user);
        if (histories.isEmpty()) {
            throw new NotFoundException(HISTORY_LIST_NOT_FOUND);
        }
        for (History history : histories) {
            if (history.getRating() > 0) {
                NumOfReview numOfReview = numOfReviewRepository.findByBoardGame(history.getBoardGame())
                        .orElseThrow(() -> new NotFoundException(NUMOFREVIEW_NOT_FOUND));
                double sum = numOfReview.getBoardGame().getRatingUser() * numOfReview.getNumber() - history.getRating();
                int number = numOfReview.getNumber() - 1;
                history.getBoardGame().updateRatingUser(sum / number);
                numOfReview.deleteReview();
            }
            historyRepository.delete(history);
        }
    }

    @Transactional
    public List<HistoryResponse> readHistoryListById(Long gameId) {
        BoardGame boardGame = boardGameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        List<History> histories = historyRepository.findAllByBoardGame(boardGame);
        if (histories.isEmpty()) {
            return new ArrayList<>();
        }
        List<HistoryResponse> responses = new ArrayList<>();
        for (History history : histories) {
            responses.add(HistoryResponse.response(history));
        }
        return responses;
    }
}
