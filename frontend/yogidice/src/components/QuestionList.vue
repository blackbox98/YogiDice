<template>
  <div class="question-container">
    <div class="each-question">
      <div class="question-sentence">몇명의 친구들과 게임하시나요?</div>
      <div class="answer answer-one">
        <div>1인</div>
        <div>2인</div>
        <div>3인</div>
        <div>4인</div>
        <div>5인</div>
        <div>6인</div>
        <div>7인<br />이상</div>
      </div>
    </div>
    <div class="each-question">
      <div class="question-sentence">게임의 난이도를선택해주세요!</div>
      <div class="answer answer-two">
        <div>매우쉬움</div>
        <div>쉬움</div>
        <div>보통</div>
        <div>어려움</div>
        <div>매우<br />어려움</div>
      </div>
      <div class="go-to-back">뒤로가기</div>
    </div>
    <div class="each-question">
      <div class="question-sentence">원하는 게임 타입을골라주세요!</div>
      <div class="answer answer-three">
        <div>
          특이한 <br />규칙을 가진<br />
          게임
        </div>
        <div>
          말을 <br />움직이는<br />
          게임
        </div>
        <div>팀을 이루어 <br />협동하는<br />게임</div>
        <div>
          치밀한 <br />전략을 세워<br />승리하는<br />
          게임
        </div>
        <div>경제 지식을<br />활용하는 <br />게임</div>
        <div>
          단서를 통해<br />
          정답을<br />
          찾아가는<br />
          게임
        </div>
      </div>
      <div class="go-to-back">뒤로가기</div>
    </div>
    <div class="each-question">
      <div class="question-sentence">원하는 플레이 타임을 골라주세요!</div>
      <div class="answer answer-four">
        <div>30분<br />이하</div>
        <div>30분<br />~<br />1시간</div>
        <div>1시간<br />~<br />2시간</div>
        <div>2시간<br />~<br />4시간</div>
        <div>4시간<br />이상</div>
      </div>
      <div class="go-to-back">뒤로가기</div>
    </div>
    <div class="each-question">
      <div class="question-sentence">언제 나온 게임이 좋으신가요!</div>
      <div class="answer answer-five">
        <div>옛날감성</div>
        <div>보통</div>
        <div>최신게임</div>
      </div>
      <div class="go-to-back">뒤로가기</div>
    </div>
  </div>
</template>

<script>
import { getCurrentInstance, onMounted } from "@vue/runtime-core";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  setup() {
    const store = useStore();
    const router = useRouter();
    let answers = {
      question1: 0,
      question2: 0,
      question3: 0,
      question4: 0,
      question5: 0,
    };

    const internalInstance = getCurrentInstance();
    // 전역변수로 선언해놓은 mitt 가져오기
    const emitter = internalInstance.appContext.config.globalProperties.emitter;

    onMounted(() => {
      const questionBox = document.querySelector(".question-container");

      let moveEventOne = function () {
        questionBox.style.transform = "translateX(-100vw)";
        emitter.emit("moveToTwo");
      };
      let moveEventTwo = function () {
        questionBox.style.transform = "translateX(-200vw)";
        emitter.emit("moveToThree");
      };
      let moveEventThree = function () {
        questionBox.style.transform = "translateX(-300vw)";
        emitter.emit("moveToFour");
      };
      let moveEventFour = function () {
        questionBox.style.transform = "translateX(-400vw)";
        emitter.emit("moveToFive");
      };
      let moveEventFive = function () {
        store.dispatch("games/filteringGames", answers);
        router.push({ name: "PickResultView" });
      };

      let answerBtnsOne = document.querySelectorAll(".answer-one div");
      for (let i = 0; i < answerBtnsOne.length; i++) {
        answerBtnsOne[i].addEventListener("click", function () {
          answers.question1 = i + 1;
          moveEventOne();
        });
      }
      let answerBtnsTwos = document.querySelectorAll(".answer-two div");
      for (let i = 0; i < answerBtnsTwos.length; i++) {
        answerBtnsTwos[i].addEventListener("click", function () {
          answers.question2 = i + 1;
          moveEventTwo();
        });
      }
      let answerBtnsThrees = document.querySelectorAll(".answer-three div");
      for (let i = 0; i < answerBtnsThrees.length; i++) {
        answerBtnsThrees[i].addEventListener("click", function () {
          answers.question3 = i + 1;
          moveEventThree();
        });
      }
      let answerBtnsFours = document.querySelectorAll(".answer-four div");
      for (let i = 0; i < answerBtnsFours.length; i++) {
        answerBtnsFours[i].addEventListener("click", function () {
          answers.question4 = i + 1;
          moveEventFour();
        });
      }
      let answerBtnsFives = document.querySelectorAll(".answer-five div");
      for (let i = 0; i < answerBtnsFives.length; i++) {
        answerBtnsFives[i].addEventListener("click", function () {
          answers.question5 = i + 1;
          moveEventFive();
        });
      }
      let backBtns = document.querySelectorAll(".go-to-back");
      for (let i = 0; i < backBtns.length; i++)
        backBtns[i].addEventListener("click", function () {
          questionBox.style.transform = `translateX(${i * -100}vw)`;
          emitter.emit(`backToBefore`, i + 1);
        });
    });
  },
};
</script>

<style>
.question-container {
  display: flex;
  flex-wrap: nowrap;
  width: 500vw;
  transition-duration: 0.5s;
}
.each-question {
  width: 100vw;
  height: 60vh;
  margin: 10vw 5vw;
}
.question-sentence {
  width: 90vw;
  font-family: "Pretendard";
  font-size: 1.4rem;
  font-weight: bolder;
  margin-top: 4vh;
  text-align: center;
}
.answer {
  display: flex;
  flex-wrap: wrap;
  width: 90vw;
  justify-content: center;
  gap: 3vw;
  margin-top: 3vh;
}
.answer div {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 24vw;
  height: 15vh;
  margin-top: 2vh;
  background-color: white;
  box-shadow: 2px 4px 4px rgba(0, 0, 0, 0.3);
  border-radius: 8px;
  font-family: "Pretendard";
  font-size: 1.2rem;
  font-weight: bolder;
  text-align: center;
}
.answer div:active {
  background: #adb5bd;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.15),
    inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.answer-three div {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 24vw;
  height: 15vh;
  margin-top: 2vh;
  background-color: white;
  box-shadow: 2px 4px 4px rgba(0, 0, 0, 0.3);
  border-radius: 8px;
  font-family: "Pretendard";
  font-size: 1rem;
  font-weight: bolder;
  text-align: center;
}
.answer-four div {
  font-size: 1.2rem;
}
.go-to-back {
  display: flex;
  justify-content: center;
  margin-top: 10vh;
  font-size: 1rem;
  color: black;
  font-weight: bold;
  font-family: "Pretendard";
}
</style>
