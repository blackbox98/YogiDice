package com.specialization.yogidice.service.category;

import com.specialization.yogidice.common.exception.DuplicateException;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.category.Type;
import com.specialization.yogidice.domain.entity.category.TypeGroup;
import com.specialization.yogidice.domain.repository.BoardGameRepository;
import com.specialization.yogidice.domain.repository.category.TypeGroupRepository;
import com.specialization.yogidice.domain.repository.category.TypeRepository;
import com.specialization.yogidice.dto.request.category.TypeGroupCreateRequest;
import com.specialization.yogidice.dto.request.category.TypeGroupUpdateRequest;
import com.specialization.yogidice.dto.response.category.TypeGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.specialization.yogidice.common.exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TypeGroupService {
    private final BoardGameRepository boardGameRepository;
    private final TypeRepository typeRepository;
    private final TypeGroupRepository typeGroupRepository;

    @Transactional
    public Long createTypeGroup(TypeGroupCreateRequest request) {
        BoardGame boardGame = boardGameRepository.findById(request.getGameId())
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        Type type = typeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new NotFoundException(TYPE_NOT_FOUND));
        if (typeGroupRepository.findByBoardGameAndType(boardGame, type).isPresent())
            throw new DuplicateException(String.format("%s 은/는 이미 %s에 등록된 유형입니다.", type.getName(), boardGame.getTitleKr()));
        TypeGroup saveTypeGroup = TypeGroup.create(boardGame, type);
        return typeGroupRepository.save(saveTypeGroup).getId();
    }

    @Transactional
    public List<TypeGroupResponse> readTypeGroupListOfBoardGame(Long gameId) {
        BoardGame boardGame = boardGameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        List<TypeGroup> typeGroups = typeGroupRepository.findByBoardGame(boardGame);
        if (typeGroups.isEmpty()) {
            return new ArrayList<>();
        }
        List<TypeGroupResponse> responses = new ArrayList<>();
        for (TypeGroup typeGroup : typeGroups) {
            responses.add(TypeGroupResponse.response(typeGroup));
        }
        return responses;
    }

    @Transactional
    public List<TypeGroupResponse> readTypeGroupListOfType(Byte typeId) {
        Type type = typeRepository.findById(typeId)
                .orElseThrow(() -> new NotFoundException(TYPE_NOT_FOUND));
        List<TypeGroup> typeGroups = typeGroupRepository.findByType(type);
        if (typeGroups.isEmpty()) {
            return new ArrayList<>();
        }
        List<TypeGroupResponse> responses = new ArrayList<>();
        for (TypeGroup typeGroup : typeGroups) {
            responses.add(TypeGroupResponse.response(typeGroup));
        }
        return responses;
    }

    @Transactional
    public void updateTypeGroup(Long typeGroupId, TypeGroupUpdateRequest request) {
        TypeGroup typeGroup = typeGroupRepository.findById(typeGroupId)
                .orElseThrow(() -> new NotFoundException(TYPE_LIST_NOT_FOUND));
        Type type = typeRepository.findById(request.getTypeId())
                .orElseThrow(() -> new NotFoundException(TYPE_NOT_FOUND));
        typeGroup.update(type);
        typeGroupRepository.save(typeGroup);
    }

    @Transactional
    public void deleteTypeGroup(Long typeGroupId) {
        TypeGroup typeGroup = typeGroupRepository.findById(typeGroupId)
                .orElseThrow(() -> new NotFoundException(TYPE_LIST_NOT_FOUND));
        typeGroupRepository.delete(typeGroup);
    }
}
