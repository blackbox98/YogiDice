<template>
  <div id="input-box">
    <div>
      <input type="number" min="0" max="99" v-model="startHour" />
      <span>시간 </span>
    </div>
    <div>
      <input type="number" min="0" max="59" v-model="startMin" />
      <span>분 </span>
    </div>
    <div>
      <input type="number" min="0" max="59" v-model="startSec" />
      <span>초 </span>
    </div>
    <button id="set-time-button" type="submit">설정</button>
  </div>
  <div class="timer-component">
    <div class="timer-group">
      <div class="timer hour">
        <div class="hand"><span></span></div>
        <div class="hand"><span></span></div>
      </div>
      <div class="timer second">
        <div class="hand"><span></span></div>
        <div class="hand"><span></span></div>
      </div>
      <div class="timer-screen">
        <p id="timer-time" class="text-headline-6">{{ baseTime }}</p>
      </div>
    </div>
  </div>
  <br />

  <div id="timer-buttons">
    <button id="start-button" class="text-button">START</button>
    <button id="stop-button" class="text-button">STOP</button>
    <button id="reset-button" class="text-button">RESET</button>
  </div>
</template>

<script>
import { onMounted, ref } from "@vue/runtime-core";
export default {
  setup() {
    let timer_down;
    let startHour = ref(0);
    let startMin = ref(0);
    let startSec = ref(0);
    let baseTime = ref("00:00:00");
    let isWriting = function () {
      let box = document.querySelector(".bottom-box");
      box.style.display = "none";
    };

    onMounted(() => {
      let timer_down_start;
      let animationTarget1 = document.querySelectorAll(
        ".timer .hand:first-child span",
      );
      let animationTarget2 = document.querySelectorAll(
        ".timer .hand:last-child span",
      );
      let startButton = document.querySelector("#start-button");
      let setTimeButton = document.querySelector("#set-time-button");
      let totalCircle = document.querySelectorAll(".timer.hour .hand span");

      startButton.addEventListener("click", function (e) {
        if (
          !e.target.classList.contains("processing") &&
          baseTime.value !== "00:00:00"
        ) {
          e.target.classList.add("processing");

          // 만약, 리셋한게 아니라 잠시 일시정지라면?
          timer_down();
        }
      });
      let stopButton = document.querySelector("#stop-button");

      stopButton.addEventListener("click", function () {
        for (let t of animationTarget1) {
          t.style.animationName = "";
        }
        for (let t2 of animationTarget2) {
          t2.style.animationName = "";
        }
        startButton.classList.remove("processing");
        clearInterval(timer_down_start);
      });

      let resetButton = document.querySelector("#reset-button");
      resetButton.addEventListener("click", function () {
        baseTime.value = "00:00:00";
      });

      setTimeButton.addEventListener("click", function () {
        let displayNum = [];

        displayNum[0] =
          ("" + startHour.value).length == 1
            ? "0" + ("" + startHour.value)
            : "" + startHour.value;
        displayNum[1] =
          ("" + startMin.value).length == 1
            ? "0" + ("" + startMin.value)
            : "" + startMin.value;
        displayNum[2] =
          ("" + startSec.value).length == 1
            ? "0" + ("" + startSec.value)
            : "" + startSec.value;
        baseTime.value = displayNum.join(":");

        // baseTime.value = `${startHour.value}:${startMin.value}:${startSec.value}`
        // total = computed(()=> startHour.value*3600+startMin.value*60+startSec.value)
      });

      timer_down = function () {
        let time =
          Number(baseTime.value.substr(0, 2)) * 3600 +
          Number(baseTime.value.substr(3, 2)) * 60 +
          Number(baseTime.value.substr(6, 2)); //기준시간 작성
        let hour = ""; //시
        let min = ""; //분
        let sec = ""; //초
        for (let c of totalCircle) {
          c.style.animationDuration = `${time}s`;
        }
        for (let t of animationTarget1) {
          t.style.animationName = "spin1";
        }
        for (let t2 of animationTarget2) {
          t2.style.animationName = "spin2";
        }

        //setInterval(함수, 시간) : 주기적인 실행
        timer_down_start = setInterval(function () {
          time--;
          //parseInt() : 정수를 반환
          hour = "" + parseInt(time / 3600);
          min = "" + parseInt((time % 3600) / 60); //몫을 계산
          sec = "" + (time % 60); //나머지를 계산
          let parts = [];

          parts[0] = hour.length == 1 ? "0" + hour : hour;
          parts[1] = min.length == 1 ? "0" + min : min;
          parts[2] = sec.length == 1 ? "0" + sec : sec;

          baseTime.value = parts.join(":");

          //타임아웃 시
          if (time === 0) {
            clearInterval(timer_down_start); //setInterval() 실행을 끝냄
            baseTime.value = "시간초과";
            for (let t of animationTarget1) {
              t.style.animationName = "";
            }
            for (let t2 of animationTarget2) {
              t2.style.animationName = "";
            }
            if (navigator.vibrate) {
              navigator.vibrate(3000); // 진동을 울리게 한다. 1000ms = 1s 이다.
            }
          }
        }, 1000);
      };
    });
    return {
      startHour,
      startMin,
      startSec,
      baseTime,
      isWriting,
    };
  },
};
</script>

<style>
.inputs {
  display: flex;
  justify-content: center;
  gap: 2vw;
}
.inputs input {
  width: 10vw;
  height: 4vw;
  margin-right: 2px;
}

.timer-group {
  height: 80vw;
  margin: 10vw 10vw;
  position: relative;
  width: 80vw;
}

.timer {
  border-radius: 50%;
  height: 20vw;
  overflow: hidden;
  position: absolute;
  width: 20vw;
}

.timer:after {
  background-color: var(--color-bg-modal);
  border-radius: 50%;
  content: "";
  display: block;
  height: 16vw;
  left: 2vw;
  position: absolute;
  width: 16vw;
  top: 2vw;
}

.timer .hand {
  float: left;
  height: 100%;
  overflow: hidden;
  position: relative;
  width: 50%;
}

.timer .hand span {
  border: 10vw solid rgba(0, 255, 255, 0.4);
  border-bottom-color: transparent;
  border-left-color: transparent;
  border-radius: 50%;
  display: block;
  height: 0;
  position: absolute;
  right: 0;
  top: 0;
  transform: rotate(225deg);
  width: 0;
}

.timer .hand:first-child {
  transform: rotate(180deg);
}

.timer .hand span {
  animation-duration: 4s;
  animation-iteration-count: infinite;
  animation-timing-function: linear;
}

.timer.hour {
  background: rgba(0, 0, 0, 0.3);
  height: 80vw;
  left: 0;
  width: 80vw;
  top: 0;
}

.timer.hour .hand span {
  animation-duration: 3600s;
  border-top-color: rgba(255, 0, 255);
  border-right-color: rgba(255, 0, 255);
  border-width: 40vw;
}

.timer.hour:after {
  height: 72vw;
  left: 4vw;
  width: 72vw;
  top: 4vw;
}

.timer.second {
  background: rgba(0, 0, 0, 0.2);
  height: 60vw;
  left: 10vw;
  width: 60vw;
  top: 10vw;
}

.timer.second .hand span {
  animation-duration: 1s;
  border-top-color: rgba(255, 255, 255, 0.15);
  border-right-color: rgba(255, 255, 255, 0.15);
  border-width: 30vw;
}

.timer.second:after {
  height: 58vw;
  left: 1vw;
  width: 58vw;
  top: 1vw;
}

.timer-screen {
  border-radius: 50%;
  height: 59vw;
  left: 10vw;
  position: absolute;
  width: 59vw;
  text-align: center;
  top: 10vw;
  display: flex;
  justify-content: center;
}

.timer-screen h2 {
  font-weight: 300;
}

.timer-screen p {
  border-radius: 4vw;
  font-size: 15vw;
  font-weight: 400;
  position: absolute;
  color: white;
  align-self: center;
}

@keyframes spin1 {
  0% {
    transform: rotate(225deg);
  }
  50% {
    transform: rotate(225deg);
  }
  100% {
    transform: rotate(405deg);
  }
}

@keyframes spin2 {
  0% {
    transform: rotate(225deg);
  }
  50% {
    transform: rotate(405deg);
  }
  100% {
    transform: rotate(405deg);
  }
}

#timer-buttons {
  margin-top: -4vh;
  display: flex;
  justify-content: center;
  z-index: 1;
}

#timer-buttons button {
  font-size: 6vw;
  height: 8vh;
  background-color: var(--color-bg-modal);
}

#start-button.processing {
  background-color: black;
}

#input-box {
  display: flex;
  justify-content: center;
  gap: 2vw;
  margin-top: 4vh;
  margin-bottom: 2vh;
}
</style>
