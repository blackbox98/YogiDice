package com.specialization.yogidice.service;

import com.specialization.yogidice.common.exception.DuplicateException;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.BggData;
import com.specialization.yogidice.domain.repository.BggDataRepository;
import com.specialization.yogidice.dto.request.BggDataRequest;
import com.specialization.yogidice.dto.response.BggDataResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.specialization.yogidice.common.exception.NotFoundException.BGGDATA_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BggDataService {
    private final BggDataRepository bggDataRepository;

    @Transactional
    public Long createBggData(BggDataRequest request) {
        if (bggDataRepository.findByBggCodeAndNickNameAndRatingUser(request.getBbgCode(), request.getNickName(), request.getRatingUser()).isPresent()) {
            throw new DuplicateException(String.format("해당 보드게임에 대한 %s 님의 평가가 이미 등록되어 있습니다.", request.getNickName()));
        }
        BggData saveBggData = BggData.create(
                request.getBbgCode(),
                request.getNickName(),
                request.getRatingUser()
        );
        return bggDataRepository.save(saveBggData).getId();
    }

    @Transactional
    public List<BggDataResponse> readBggDataList() {
        List<BggData> bggDataList = bggDataRepository.findAll();
        if (bggDataList.isEmpty()) {
            return new ArrayList<>();
        }
        List<BggDataResponse> responses = new ArrayList<>();
        for (BggData bggData : bggDataList) {
            responses.add(BggDataResponse.response(bggData));
        }
        return responses;
    }

    @Transactional
    public BggDataResponse readBggData(Long bggDataId) {
        BggData bggData = bggDataRepository.findById(bggDataId)
                .orElseThrow(() -> new NotFoundException(BGGDATA_NOT_FOUND));
        return BggDataResponse.response(bggData);
    }

    @Transactional
    public void updateBggData(Long bggDataId, BggDataRequest request) {
        BggData bggData = bggDataRepository.findById(bggDataId)
                .orElseThrow(() -> new NotFoundException(BGGDATA_NOT_FOUND));
        bggData.update(
                request.getBbgCode(),
                request.getNickName(),
                request.getRatingUser()
        );
        bggDataRepository.save(bggData);
    }

    @Transactional
    public void deleteBggData(Long bggDataId) {
        BggData bggData = bggDataRepository.findById(bggDataId)
                .orElseThrow(() -> new NotFoundException(BGGDATA_NOT_FOUND));
        bggDataRepository.delete(bggData);
    }
}
