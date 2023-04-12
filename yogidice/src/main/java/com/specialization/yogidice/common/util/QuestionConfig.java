package com.specialization.yogidice.common.util;

import com.specialization.yogidice.dto.request.BoardGamePickRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuestionConfig {
    int qOne;
    int qTwoLower;
    int qTwoUpper;



    String qThree;
    int qFourLower;
    int qFourUpper;
    int qFiveLower;
    int qFiveUpper;


    public QuestionConfig(BoardGamePickRequest request) {
        this.qOne = request.getQuestion1();


        //난이도
        if(request.getQuestion2()==1){
            this.qTwoLower=0;
            this.qTwoUpper=1;
        } else if(request.getQuestion2()==2){
            this.qTwoLower=1;
            this.qTwoUpper=2;
        } else if(request.getQuestion2()==3){
            this.qTwoLower=2;
            this.qTwoUpper=3;
        }else if(request.getQuestion2()==4){
            this.qTwoLower=3;
            this.qTwoUpper=4;
        } else {
            this.qTwoLower=4;
            this.qTwoUpper=5;
        };

        //메커니즘
        if(request.getQuestion3()==1){
            this.qThree = "조건";
        } else if(request.getQuestion3()==2){
            this.qThree = "말";
        } else if(request.getQuestion3()==3){
            this.qThree = "파티";
        }else if(request.getQuestion3()==4){
            this.qThree = "전략";
        } else if(request.getQuestion3()==5){
            this.qThree = "경제";
        }else {
            this.qThree = "추리카드퍼즐";
        };


        //시간
        if(request.getQuestion4()==1){
            this.qFourLower=0;
            this.qFourUpper=30;
        } else if(request.getQuestion4()==2){
            this.qFourLower=31;
            this.qFourUpper=60;
        } else if(request.getQuestion4()==3){
            this.qFourLower=61;
            this.qFourUpper=120;
        }else if(request.getQuestion4()==4){
            this.qFourLower=121;
            this.qFourUpper=240;
        } else {
            this.qFourLower=241;
            this.qFourUpper=1000;
        };

        //발매년도
        if(request.getQuestion5()==1){
            this.qFiveLower=0;
            this.qFiveUpper=2010;
        } else if(request.getQuestion5()==2){
            this.qFiveLower=2010;
            this.qFiveUpper=2018;
        } else {
            this.qFiveLower=2018;
            this.qFiveUpper=10000;
        }
    }
}
