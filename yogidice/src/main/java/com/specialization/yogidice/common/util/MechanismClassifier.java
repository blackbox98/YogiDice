package com.specialization.yogidice.common.util;

import com.specialization.yogidice.dto.response.BoardGameResponse;
import com.specialization.yogidice.dto.response.category.MechanismGroupResponse;


public class MechanismClassifier {
    static String[] parentMechName = {"조건", "말", "파티", "전략","경제","추리카드퍼즐"};
    /*static int[][] mainMechanism = {
            //조건
            {2, 3, 4, 5, 6, 7, 8, 12, 23, 25, 30, 34, 36, 41, 44, 45, 57, 71, 72, 81, 84, 86, 109, 115, 127, 131, 132, 135, 142, 158, 163, 167, 177, 179, 180, 183, 184, 188, 191},
            //말
            {10, 11, 32, 43, 50, 51, 52, 63, 65, 88, 89, 90, 93, 96, 99, 100, 101, 102, 108, 111, 120, 124, 130, 134, 141, 168, 170, 171, 175, 189, 190},
            //파티
            {1, 39, 53, 54, 56, 58, 59, 60, 69, 82, 114, 119, 123, 128, 129, 133, 137, 144, 149, 151, 152, 153, 155, 157, 161, 162, 185},
            //전략
            {9, 14, 24, 28, 29, 31, 33, 38, 42, 61, 70, 76, 78, 79, 87, 97, 105, 106, 107, 110, 113, 126, 138, 139, 140, 145, 147, 150, 154, 164, 166, 173, 178, 181, 182, 187},
            //경제
            {13, 15, 16, 17, 18, 19, 20, 21, 22, 26, 35, 37, 40, 49, 73, 74, 85, 91, 104, 112, 136, 146, 160, 172, 176, 186},
            //추리카드퍼즐
            {27, 46, 47, 48, 55, 62, 64, 66, 67, 68, 75, 80, 83, 92, 94, 95, 98, 116, 117, 118, 121, 122, 125, 143, 148, 156, 165, 169, 174}
    };*/

    public static boolean checkMechanism(int parentMechId, BoardGameResponse response) {
        for (MechanismGroupResponse respon :response.getMechanismGroupResponses()){

            if(respon.getParentsMec().equals(parentMechName[parentMechId-1])){
                return true;
            }
        }
        return false;
    }
}
