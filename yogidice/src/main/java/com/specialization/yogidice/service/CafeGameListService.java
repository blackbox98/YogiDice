package com.specialization.yogidice.service;

import com.specialization.yogidice.common.exception.DuplicateException;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.BoardGameCafe;
import com.specialization.yogidice.domain.entity.CafeGameList;
import com.specialization.yogidice.domain.repository.BoardGameCafeRepository;
import com.specialization.yogidice.domain.repository.BoardGameRepository;
import com.specialization.yogidice.domain.repository.CafeGameListRepository;
import com.specialization.yogidice.dto.request.CafeGameListCreateRequest;
import com.specialization.yogidice.dto.request.CafeGameListUpdateRequest;
import com.specialization.yogidice.dto.response.CafeGameResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.specialization.yogidice.common.exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CafeGameListService {
    private final BoardGameCafeRepository boardGameCafeRepository;
    private final BoardGameRepository boardGameRepository;
    private final CafeGameListRepository cafeGameListRepository;

    @Transactional
    public Long createCafeGameList(CafeGameListCreateRequest request) {
        BoardGameCafe cafe = boardGameCafeRepository.findById(request.getCafeId())
                .orElseThrow(() -> new NotFoundException(BOARDGAMECAFE_NOT_FOUND));
        BoardGame boardGame = boardGameRepository.findById(request.getGameId())
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        if (cafeGameListRepository.findCafeGameListByBoardGameCafeAndBoardGame(cafe, boardGame).isPresent()) {
            throw new DuplicateException(String.format("%s 은/는 이미 %s에 등록된 보드게임입니다.", boardGame.getTitleKr(), cafe.getName()));
        }
        CafeGameList saveGameList = CafeGameList.create(cafe, boardGame);
        return cafeGameListRepository.save(saveGameList).getId();
    }

    @Transactional
    public List<CafeGameResponse> readCafeGameListOfBoardGameCafe(Long cafeId) {
        BoardGameCafe cafe = boardGameCafeRepository.findById(cafeId)
                .orElseThrow(() -> new NotFoundException(BOARDGAMECAFE_NOT_FOUND));
        List<CafeGameList> cafeGameLists = cafeGameListRepository.findCafeGameListByBoardGameCafe(cafe);
        if (cafeGameLists.isEmpty()) {
            return new ArrayList<>();
        }
        List<CafeGameResponse> responses = new ArrayList<>();
        for (CafeGameList cafeGameList : cafeGameLists) {
            responses.add(CafeGameResponse.response(cafeGameList));
        }
        return responses;
    }

    @Transactional
    public List<CafeGameResponse> readCafeGameListOfBoardGame(Long gameId) {
        BoardGame boardGame = boardGameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        List<CafeGameList> cafeGameLists = cafeGameListRepository.findCafeGameListByBoardGame(boardGame);
        if (cafeGameLists.isEmpty()) {
            return new ArrayList<>();
        }
        List<CafeGameResponse> responses = new ArrayList<>();
        for (CafeGameList cafeGameList : cafeGameLists) {
            responses.add(CafeGameResponse.response(cafeGameList));
        }
        return responses;
    }

    @Transactional
    public void updateCafeGameList(Long gameListId, CafeGameListUpdateRequest request) {
        CafeGameList cafeGameList = cafeGameListRepository.findById(gameListId)
                .orElseThrow(() -> new NotFoundException(CAFEGAME_NOT_FOUND));
        BoardGame boardGame = boardGameRepository.findById(request.getGameId())
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        cafeGameList.update(boardGame);
        cafeGameListRepository.save(cafeGameList);
    }

    @Transactional
    public void deleteCafeGameList(Long gameListId) {
        CafeGameList cafeGameList = cafeGameListRepository.findById(gameListId)
                .orElseThrow(() -> new NotFoundException(CAFEGAME_NOT_FOUND));
        cafeGameListRepository.delete(cafeGameList);
    }

    @Transactional
    public List<CafeGameResponse> readCafeGameListOfBoardGameCafeByAddress(String address) {
        BoardGameCafe cafe = boardGameCafeRepository.findBoardGameCafeByAddressContaining(address)
                .orElse(null);
        if (cafe == null) return new ArrayList<>();
        List<CafeGameList> cafeGameLists = cafeGameListRepository.findCafeGameListByBoardGameCafe(cafe);
        if (cafeGameLists.isEmpty()) return new ArrayList<>();
        List<CafeGameResponse> responses = new ArrayList<>();
        for (CafeGameList cafeGameList : cafeGameLists) {
            responses.add(CafeGameResponse.response(cafeGameList));
        }
        return responses;
    }
}
