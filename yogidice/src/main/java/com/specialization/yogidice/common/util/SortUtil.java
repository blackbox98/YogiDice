package com.specialization.yogidice.common.util;

import java.util.Comparator;

public class SortUtil implements Comparator<SortUtil> {
    int index;
    int value;

    public SortUtil(int index, int value) {
        this.index = index;
        this.value = value;
    }

    @Override
    public int compare(SortUtil o1, SortUtil o2) {
        return o1.value - o2.value;
    }
}
