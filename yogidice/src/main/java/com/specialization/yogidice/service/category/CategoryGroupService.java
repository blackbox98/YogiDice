package com.specialization.yogidice.service.category;

import com.specialization.yogidice.common.exception.DuplicateException;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.category.Category;
import com.specialization.yogidice.domain.entity.category.CategoryGroup;
import com.specialization.yogidice.domain.repository.BoardGameRepository;
import com.specialization.yogidice.domain.repository.category.CategoryGroupRepository;
import com.specialization.yogidice.domain.repository.category.CategoryRepository;
import com.specialization.yogidice.dto.request.category.CategoryGroupCreateRequest;
import com.specialization.yogidice.dto.request.category.CategoryGroupUpdateRequest;
import com.specialization.yogidice.dto.response.category.CategoryGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.specialization.yogidice.common.exception.NotFoundException.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryGroupService {
    private final BoardGameRepository boardGameRepository;
    private final CategoryRepository categoryRepository;
    private final CategoryGroupRepository categoryGroupRepository;

    @Transactional
    public Long createCategoryGroup(CategoryGroupCreateRequest request) {
        BoardGame boardGame = boardGameRepository.findById(request.getGameId())
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));
        if (categoryGroupRepository.findByBoardGameAndCategory(boardGame, category).isPresent())
            throw new DuplicateException(String.format("%s 은/는 이미 %s에 등록된 카테고리(테마)입니다.", category.getName(), boardGame.getTitleKr()));
        CategoryGroup saveCategoryGroup = CategoryGroup.create(boardGame, category);
        return categoryGroupRepository.save(saveCategoryGroup).getId();
    }

    @Transactional
    public List<CategoryGroupResponse> readCategoryGroupListOfBoardGame(Long gameId) {
        BoardGame boardGame = boardGameRepository.findById(gameId)
                .orElseThrow(() -> new NotFoundException(BOARDGAME_NOT_FOUND));
        List<CategoryGroup> categoryGroups = categoryGroupRepository.findByBoardGame(boardGame);
        if (categoryGroups.isEmpty()) {
            return new ArrayList<>();
        }
        List<CategoryGroupResponse> responses = new ArrayList<>();
        for (CategoryGroup categoryGroup : categoryGroups) {
            responses.add(CategoryGroupResponse.response(categoryGroup));
        }
        return responses;
    }

    @Transactional
    public List<CategoryGroupResponse> readCategoryGroupListOfCategory(Long cateId) {
        Category category = categoryRepository.findById(cateId)
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));
        List<CategoryGroup> categoryGroups = categoryGroupRepository.findByCategory(category);
        if (categoryGroups.isEmpty()) {
            return new ArrayList<>();
        }
        List<CategoryGroupResponse> responses = new ArrayList<>();
        for (CategoryGroup categoryGroup : categoryGroups) {
            responses.add(CategoryGroupResponse.response(categoryGroup));
        }
        return responses;
    }

    @Transactional
    public void updateCategoryGroup(Long cateGroupId, CategoryGroupUpdateRequest request) {
        CategoryGroup categoryGroup = categoryGroupRepository.findById(cateGroupId)
                .orElseThrow(() -> new NotFoundException(CATEGORY_LIST_NOT_FOUND));
        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new NotFoundException(CATEGORY_NOT_FOUND));
        categoryGroup.update(category);
        categoryGroupRepository.save(categoryGroup);
    }

    @Transactional
    public void deleteCategoryGroup(Long cateGroupId) {
        CategoryGroup categoryGroup = categoryGroupRepository.findById(cateGroupId)
                .orElseThrow(() -> new NotFoundException(CATEGORY_LIST_NOT_FOUND));
        categoryGroupRepository.delete(categoryGroup);
    }
}
