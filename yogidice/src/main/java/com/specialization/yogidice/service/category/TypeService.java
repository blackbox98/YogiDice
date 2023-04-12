package com.specialization.yogidice.service.category;

import com.specialization.yogidice.common.exception.DuplicateException;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.category.Type;
import com.specialization.yogidice.domain.repository.category.TypeRepository;
import com.specialization.yogidice.dto.request.category.TypeRequest;
import com.specialization.yogidice.dto.response.category.TypeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.specialization.yogidice.common.exception.NotFoundException.TYPE_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TypeService {
    private final TypeRepository typeRepository;

    @Transactional
    public Byte createType(TypeRequest request) {
        if (typeRepository.findByName(request.getName()).isPresent()) {
            throw new DuplicateException(String.format("%s 은/는 이미 등록된 보드게임 타입입니다.", request.getName()));
        }
        Type saveType = Type.create(
                request.getName()
        );
        return typeRepository.save(saveType).getId();
    }

    @Transactional
    public List<TypeResponse> readTypeList() {
        List<Type> types = typeRepository.findAll();
        if (types.isEmpty()) {
            return new ArrayList<>();
        }
        List<TypeResponse> responses = new ArrayList<>();
        for (Type type : types) {
            responses.add(TypeResponse.response(type));
        }
        return responses;
    }

    @Transactional
    public TypeResponse readType(Byte typeId) {
        Type type = typeRepository.findById(typeId)
                .orElseThrow(() -> new NotFoundException(TYPE_NOT_FOUND));
        return TypeResponse.response(type);
    }

    @Transactional
    public void updateType(Byte typeId, TypeRequest request) {
        Type type = typeRepository.findById(typeId)
                .orElseThrow(() -> new NotFoundException(TYPE_NOT_FOUND));
        type.update(
                request.getName()
        );
        typeRepository.save(type);
    }

    @Transactional
    public void deleteType(Byte typeId) {
        Type type = typeRepository.findById(typeId)
                .orElseThrow(() -> new NotFoundException(TYPE_NOT_FOUND));
        typeRepository.delete(type);
    }
}