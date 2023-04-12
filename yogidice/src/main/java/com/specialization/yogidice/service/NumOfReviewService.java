package com.specialization.yogidice.service;

import com.specialization.yogidice.common.exception.DuplicateException;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.common.util.DeduplicationUtils;
import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.NumOfReview;
import com.specialization.yogidice.domain.repository.BoardGameRepository;
import com.specialization.yogidice.domain.repository.NumOfReviewRepository;
import com.specialization.yogidice.dto.request.NumOfReviewRequest;
import com.specialization.yogidice.dto.response.NumOfReviewResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.specialization.yogidice.common.exception.NotFoundException.BOARDGAME_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NumOfReviewService {
    private final NumOfReviewRepository numOfReviewRepository;
    private final BoardGameRepository boardGameRepository;


    @Transactional
    public void createNumOfReview(NumOfReviewRequest request) {
        BoardGame boardGame = boardGameRepository.findById(request.getGameId())
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        if (numOfReviewRepository.findByBoardGame(boardGame).isPresent()) {
            throw new DuplicateException(String.format("%s 의 총 리뷰 수가 이미 등록되어 있습니다.", boardGame.getTitleKr()));
        }
        NumOfReview saveNumOfReview = NumOfReview.create(
                boardGame
        );
        numOfReviewRepository.save(saveNumOfReview);
    }

    @Transactional
    public List<NumOfReviewResponse> readTop10ListByNumOfReview() {
        List<NumOfReview> numOfReviews = numOfReviewRepository.findTop100ByOrderByNumberDesc();
        if (numOfReviews.isEmpty()) {
            return new ArrayList<>();
        }
        List<NumOfReviewResponse> responses = new ArrayList<>();
        for (NumOfReview numOfReview : numOfReviews) {
            responses.add(NumOfReviewResponse.response(numOfReview));
        }
        responses = DeduplicationUtils.deduplication(responses, NumOfReviewResponse::getBggCode);
        for (NumOfReviewResponse response : responses) System.out.println(response.getBggCode());
        return responses;
    }

    @Transactional
    public List<NumOfReviewResponse> readAllListByNumOfReview(Pageable pageable) {
        List<NumOfReview> numOfReviews = numOfReviewRepository.findAllByOrderByNumberDesc(pageable).getContent();
        if (numOfReviews.isEmpty()) {
            return new ArrayList<>();
        }
        List<NumOfReviewResponse> responses = new ArrayList<>();
        for (NumOfReview numOfReview : numOfReviews) {
            responses.add(NumOfReviewResponse.response(numOfReview));
        }
        return responses;
    }
}
