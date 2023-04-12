package com.specialization.yogidice.domain.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.specialization.yogidice.common.util.DeduplicationUtils;
import com.specialization.yogidice.common.util.QuestionConfig;
import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.QBoardGame;
import com.specialization.yogidice.dto.request.BoardGamePickRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public class BoardGameRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    QBoardGame boardGame = QBoardGame.boardGame;

    public List<BoardGame> findBoardGameByPick(BoardGamePickRequest request) {
        QuestionConfig questionConfig = new QuestionConfig(request);
        List<BoardGame> boardGameList = jpaQueryFactory
                .selectFrom(boardGame)
                .where(
                        boardGame.difficulty.between(questionConfig.getQTwoLower(), questionConfig.getQTwoUpper()),
                        boardGame.playingTime.between(questionConfig.getQFourLower(), questionConfig.getQFourUpper()),
                        boardGame.publishYear.between(questionConfig.getQFiveLower(), questionConfig.getQFiveUpper()),
                        boardGame.maxPlayers.goe(questionConfig.getQOne()),
                        boardGame.minPlayers.loe(questionConfig.getQOne())
                )
                .limit(100)
                .fetch();
        return DeduplicationUtils.deduplication(boardGameList, BoardGame::getBggCode);
    }
}
