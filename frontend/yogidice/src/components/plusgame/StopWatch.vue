<template>
  <div class="stop-watch-component">
    <div class="stop-watch-group">
      <div class="stop-watch hour">
        <div class="hand"><span></span></div>
        <div class="hand"><span></span></div>
      </div>
      <div class="stop-watch minute">
        <div class="hand"><span></span></div>
        <div class="hand"><span></span></div>
      </div>
      <div class="stop-watch second">
        <div class="hand"><span></span></div>
        <div class="hand"><span></span></div>
      </div>
      <div class="screen">
        <p id="time">00:00:00</p>
      </div>
    </div>
    <div id="stop-watch-buttons">
      <button id="start-button-stopwatch" class="text-button">start</button>
      <button id="stop-button-stopwatch" class="text-button">stop</button>
      <button id="reset-button-stopwatch" class="text-button">reset</button>
    </div>
  </div>
</template>

<script>
import { onMounted } from "@vue/runtime-core";
export default {
  setup() {
    let timer;
    // let timeDisplay = ref("00:00:00")
    let startTimer;

    onMounted(() => {
      let one_second = 1000;
      let one_minute = one_second * 60;
      let one_hour = one_minute * 60;
      let screen = document.getElementById("time");

      let animationTarget1 = document.querySelectorAll(
        ".stop-watch .hand:first-child span",
      );
      let animationTarget2 = document.querySelectorAll(
        ".stop-watch .hand:last-child span",
      );
      let startButton = document.querySelector("#start-button-stopwatch");

      startButton.addEventListener("click", function (e) {
        if (!e.target.classList.contains("processing")) {
          e.target.classList.add("processing");
          for (let t of animationTarget1) {
            t.style.animationName = "spin1";
          }
          for (let t2 of animationTarget2) {
            t2.style.animationName = "spin2";
          }
          // 만약, 리셋한게 아니라 잠시 일시정지라면?
          let baseTime = screen.innerText;
          let startDate = new Date();

          startTimer = setInterval(function () {
            timer(startDate, baseTime);
          }, 1000);
        }
      });

      let stopButton = document.querySelector("#stop-button-stopwatch");

      stopButton.addEventListener("click", function () {
        for (let t of animationTarget1) {
          t.style.animationName = "";
        }
        for (let t2 of animationTarget2) {
          t2.style.animationName = "";
        }
        startButton.classList.remove("processing");
        clearInterval(startTimer);
      });

      let resetButton = document.querySelector("#reset-button-stopwatch");
      resetButton.addEventListener("click", function () {
        screen.innerText = "00:00:00";
      });

      timer = function (startDate, base) {
        let now = new Date();
        let passed = now - startDate;
        let parts = [];
        let baseHour = Number(base.substr(0, 2));
        let baseMin = Number(base.substr(3, 2));
        let baseSec = Number(base.substr(6, 2));

        parts[0] = "" + (Math.floor(passed / one_hour) + baseHour);
        parts[1] =
          "" + (Math.floor((passed % one_hour) / one_minute) + baseMin);
        parts[2] =
          "" +
          (Math.floor(((passed % one_hour) % one_minute) / one_second) +
            baseSec);

        parts[0] = parts[0].length == 1 ? "0" + parts[0] : parts[0];
        parts[1] = parts[1].length == 1 ? "0" + parts[1] : parts[1];
        parts[2] = parts[2].length == 1 ? "0" + parts[2] : parts[2];

        screen.innerText = parts.join(":");
      };
    });
    return {};
  },
};
</script>

<style scoped>
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

.stop-watch-group {
  height: 80vw;
  margin: 10vw 10vw;
  position: relative;
  width: 80vw;
}

.stop-watch {
  border-radius: 50%;
  height: 20vw;
  overflow: hidden;
  position: absolute;
  width: 20vw;
}

.stop-watch:after {
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

.stop-watch .hand {
  float: left;
  height: 100%;
  overflow: hidden;
  position: relative;
  width: 50%;
}

.stop-watch .hand span {
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

.stop-watch .hand:first-child {
  transform: rotate(180deg);
}

.stop-watch .hand span {
  animation-duration: 4s;
  animation-iteration-count: infinite;
  animation-timing-function: linear;
}

.stop-watch.hour {
  background: rgba(0, 0, 0, 0.3);
  height: 80vw;
  left: 0;
  width: 80vw;
  top: 0;
}

.stop-watch.hour .hand span {
  animation-duration: 3600s;
  border-top-color: rgba(255, 0, 255);
  border-right-color: rgba(255, 0, 255);
  border-width: 40vw;
}

.stop-watch.hour:after {
  height: 72vw;
  left: 4vw;
  width: 72vw;
  top: 4vw;
}

.stop-watch.minute {
  background: rgba(0, 0, 0, 0.2);
  height: 70vw;
  left: 5vw;
  width: 70vw;
  top: 5vw;
}

.stop-watch.minute .hand span {
  animation-duration: 60s;
  border-top-color: rgba(0, 255, 255);
  border-right-color: rgba(0, 255, 255);
  border-width: 35vw;
}

.stop-watch.minute:after {
  height: 62vw;
  left: 4vw;
  width: 62vw;
  top: 4vw;
}

.stop-watch.second {
  background: rgba(0, 0, 0, 0.2);
  height: 60vw;
  left: 10vw;
  width: 60vw;
  top: 10vw;
}

.stop-watch.second .hand span {
  animation-duration: 1s;
  border-top-color: rgba(255, 255, 255, 0.15);
  border-right-color: rgba(255, 255, 255, 0.15);
  border-width: 30vw;
}

.stop-watch.second:after {
  height: 58vw;
  left: 1vw;
  width: 58vw;
  top: 1vw;
}

.screen {
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

.screen h2 {
  font-weight: 300;
}

.screen p {
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

#stop-watch-buttons {
  display: flex;
  justify-content: center;
  z-index: 1;
}

#stop-watch-buttons button {
  font-size: 6vw;
  height: 8vh;
  background-color: var(--color-bg-modal);
}

#start-button-stopwatch.processing {
  background-color: black;
}
</style>
