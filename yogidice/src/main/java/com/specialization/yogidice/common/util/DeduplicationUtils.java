package com.specialization.yogidice.common.util;

import com.specialization.yogidice.domain.entity.BoardGame;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class DeduplicationUtils {
    public static <T> List<T> deduplication(List<T> list, Function<? super T,?> key){
        return list.stream().filter(deduplication(key)).collect(Collectors.toList());
    }

    public static <T> Predicate<T> deduplication(Function<? super T,?> key){
        Set<Object> set = ConcurrentHashMap.newKeySet();
        return predicate ->set.add(key.apply(predicate));
    }

}
