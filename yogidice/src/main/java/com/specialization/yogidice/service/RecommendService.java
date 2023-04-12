package com.specialization.yogidice.service;

import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.Recommend;
import com.specialization.yogidice.domain.entity.User;
import com.specialization.yogidice.domain.repository.BoardGameRepository;
import com.specialization.yogidice.domain.repository.RecommendRepository;
import com.specialization.yogidice.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.specialization.yogidice.common.exception.NotFoundException.BOARDGAME_NOT_FOUND;
import static com.specialization.yogidice.common.exception.NotFoundException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RecommendService {
    private final UserRepository userRepository;
    private final RecommendRepository recommendRepository;

    private final BoardGameRepository boardGameRepository;

    @Transactional
    public void updateRecommend(Long userId, List<Long> boardGameIds){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        recommendRepository.deleteByUser(user);
        ArrayList<Recommend> recommends = new ArrayList<>();
        for(Long gameId: boardGameIds){
            BoardGame boardGame = boardGameRepository.findById(gameId)
                    .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));

            recommends.add(Recommend.create(user, boardGame));
        }
        recommendRepository.saveAll(recommends);
    }
}
