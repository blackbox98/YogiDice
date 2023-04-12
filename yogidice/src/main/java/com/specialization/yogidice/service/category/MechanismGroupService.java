package com.specialization.yogidice.service.category;

import com.specialization.yogidice.common.exception.DuplicateException;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.category.Mechanism;
import com.specialization.yogidice.domain.entity.category.MechanismGroup;
import com.specialization.yogidice.domain.repository.BoardGameRepository;
import com.specialization.yogidice.domain.repository.category.MechanismGroupRepository;
import com.specialization.yogidice.domain.repository.category.MechanismRepository;
import com.specialization.yogidice.dto.request.category.MechanismGroupCreateRequest;
import com.specialization.yogidice.dto.request.category.MechanismGroupUpdateRequest;
import com.specialization.yogidice.dto.response.category.MechanismGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.specialization.yogidice.common.exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MechanismGroupService {
    private final BoardGameRepository boardGameRepository;
    private final MechanismRepository mechanismRepository;
    private final MechanismGroupRepository mechanismGroupRepository;

    @Transactional
    public Long createMechanismGroup(MechanismGroupCreateRequest request) {
        BoardGame boardGame = boardGameRepository.findById(request.getGameId())
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        Mechanism mechanism = mechanismRepository.findById(request.getMechanismId())
                .orElseThrow(() -> new NotFoundException(MECHANISM_NOT_FOUND));
        if (mechanismGroupRepository.findByBoardGameAndMechanism(boardGame, mechanism).isPresent())
            throw new DuplicateException(String.format("%s 은/는 이미 %s에 등록된 진행방식입니다.", mechanism.getName(), boardGame.getTitleKr()));
        MechanismGroup saveMechanismGroup = MechanismGroup.create(boardGame, mechanism);
        return mechanismGroupRepository.save(saveMechanismGroup).getId();
    }

    @Transactional
    public List<MechanismGroupResponse> readMechanismGroupListOfBoardGame(Long gameId) {
        BoardGame boardGame = boardGameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        List<MechanismGroup> mechanismGroups = mechanismGroupRepository.findByBoardGame(boardGame);
        if (mechanismGroups.isEmpty()) {
            return new ArrayList<>();
        }
        List<MechanismGroupResponse> responses = new ArrayList<>();
        for (MechanismGroup mechanismGroup : mechanismGroups) {
            responses.add(MechanismGroupResponse.response(mechanismGroup));
        }
        return responses;
    }

    @Transactional
    public List<MechanismGroupResponse> readMechanismGroupListOfMechanism(Long mechanismId) {
        Mechanism mechanism = mechanismRepository.findById(mechanismId)
                .orElseThrow(() -> new NotFoundException(MECHANISM_NOT_FOUND));
        List<MechanismGroup> mechanismGroups = mechanismGroupRepository.findByMechanism(mechanism);
        if (mechanismGroups.isEmpty()) {
            return new ArrayList<>();
        }
        List<MechanismGroupResponse> responses = new ArrayList<>();
        for (MechanismGroup mechanismGroup : mechanismGroups) {
            responses.add(MechanismGroupResponse.response(mechanismGroup));
        }
        return responses;
    }

    @Transactional
    public void updateMechanismGroup(Long mechanismGroupId, MechanismGroupUpdateRequest request) {
        MechanismGroup mechanismGroup = mechanismGroupRepository.findById(mechanismGroupId)
                .orElseThrow(() -> new NotFoundException(MECHANISM_LIST_NOT_FOUND));
        Mechanism mechanism = mechanismRepository.findById(request.getMechanismId())
                .orElseThrow(() -> new NotFoundException(MECHANISM_NOT_FOUND));
        mechanismGroup.update(mechanism);
        mechanismGroupRepository.save(mechanismGroup);
    }

    @Transactional
    public void deleteMechanismGroup(Long mechanismGroupId) {
        MechanismGroup mechanismGroup = mechanismGroupRepository.findById(mechanismGroupId)
                .orElseThrow(() -> new NotFoundException(MECHANISM_LIST_NOT_FOUND));
        mechanismGroupRepository.delete(mechanismGroup);
    }
}
