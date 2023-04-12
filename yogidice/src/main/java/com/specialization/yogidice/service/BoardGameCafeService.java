package com.specialization.yogidice.service;

import com.specialization.yogidice.common.exception.DuplicateException;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.BoardGameCafe;
import com.specialization.yogidice.domain.repository.BoardGameCafeRepository;
import com.specialization.yogidice.dto.request.BoardGameCafeRequest;
import com.specialization.yogidice.dto.response.BoardGameCafeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.specialization.yogidice.common.exception.NotFoundException.BOARDGAMECAFE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardGameCafeService {
    private final BoardGameCafeRepository boardGameCafeRepository;

    @Transactional
    public Long createBoardGameCafe(BoardGameCafeRequest request) {
        if (boardGameCafeRepository.findBoardGameCafeByNameAndAddress(request.getName(), request.getAddress()).isPresent()) {
            throw new DuplicateException(String.format("%s 은/는 이미 등록된 보드게임 카페입니다.", request.getName()));
        }
        BoardGameCafe saveCafe = BoardGameCafe.create(
                request.getName(),
                request.getAddress()
        );
        return boardGameCafeRepository.save(saveCafe).getId();
    }

    @Transactional
    public List<BoardGameCafeResponse> readBoardGameCafeList() {
        List<BoardGameCafe> cafes = boardGameCafeRepository.findAll();
        if (cafes.isEmpty()) {
            return new ArrayList<>();
        }
        List<BoardGameCafeResponse> responses = new ArrayList<>();
        for (BoardGameCafe cafe : cafes) {
            responses.add(BoardGameCafeResponse.response(cafe));
        }
        return responses;
    }

    @Transactional
    public BoardGameCafeResponse readBoardGameCafe(Long cafeId) {
        BoardGameCafe cafe = boardGameCafeRepository.findById(cafeId)
                .orElseThrow(() -> new NotFoundException(BOARDGAMECAFE_NOT_FOUND));
        return BoardGameCafeResponse.response(cafe);
    }

    @Transactional
    public void updateBoardGameCafe(Long cafeId, BoardGameCafeRequest request) {
        BoardGameCafe cafe = boardGameCafeRepository.findById(cafeId)
                .orElseThrow(() -> new NotFoundException(BOARDGAMECAFE_NOT_FOUND));
        cafe.update(
                request.getName(),
                request.getAddress()
        );
        boardGameCafeRepository.save(cafe);
    }

    @Transactional
    public void deleteBoardGameCafe(Long cafeId) {
        BoardGameCafe cafe = boardGameCafeRepository.findById(cafeId)
                .orElseThrow(() -> new NotFoundException(BOARDGAMECAFE_NOT_FOUND));
        boardGameCafeRepository.delete(cafe);
    }
}
