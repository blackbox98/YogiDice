<template>
  <div>
    <div class="tab-container"></div>
  </div>
</template>

<script>
import { onMounted, ref } from "@vue/runtime-core";
export default {
  setup() {
    let test = ref(false);
    onMounted(() => {
      const board = document.querySelector(".tab-container");
      const choiceFirstPlayer = function () {
        let players = document.querySelectorAll(".tab-container .tab-circle");
        let firstPlayer = Math.floor(Math.random() * players.length);
        for (let i = 0; i < players.length; i++) {
          if (i !== firstPlayer) {
            players[i].remove();
          } else {
            players[i].style.background =
              "radial-gradient(50% 50% at 50% 50%, rgba(147, 150, 218, 0) 6.56%, #AE93DA 100%)";
            players[i].style.animation =
              "resultAlert 1s linear infinite alternate";

            if (navigator.vibrate) {
              navigator.vibrate(500); // 진동을 울리게 한다. 1000ms = 1s 이다.
            }
          }
        }
      };

      let choiceOne;
      board.addEventListener("touchstart", (e) => {
        e.preventDefault();
        if (e.targetTouches.length >= 3) {
          clearTimeout(choiceOne);
          choiceOne = setTimeout(choiceFirstPlayer, 3000);
        } else if (e.targetTouches.length === 2) {
          choiceOne = setTimeout(choiceFirstPlayer, 3000);
        }
      });

      board.addEventListener("touchstart", (e) => {
        [...e.changedTouches].forEach((touch) => {
          const dot = document.createElement("div");
          dot.classList.add("tab-circle");
          dot.style.left = `${touch.pageX}px`;
          dot.style.top = `${touch.pageY}px`;
          dot.id = touch.identifier;
          board.append(dot);
          if (navigator.vibrate) {
            navigator.vibrate(50); // 진동을 울리게 한다. 1000ms = 1s 이다.
          }
        });
      });
      board.addEventListener("touchmove", function (e) {
        [...e.changedTouches].forEach((touch) => {
          const dot = document.getElementById(touch.identifier);
          dot.style.top = `${touch.pageY}px`;
          dot.style.left = `${touch.pageX}px`;
        });
      });
      board.addEventListener("touchend", (e) => {
        [...e.changedTouches].forEach((touch) => {
          const dot = document.getElementById(touch.identifier);
          dot.remove();
        });
      });
      board.addEventListener("touchcancel", (e) => {
        [...e.changedTouches].forEach((touch) => {
          const dot = document.getElementById(touch.identifier);
          dot.remove();
        });
      });
    });
    return {
      test,
    };
  },
};
</script>

<style>
.tab-container {
  width: 90vw;
  height: 60vh;
  margin: 5vh 5vw;
  background: radial-gradient(
    50% 50% at 50% 50%,
    rgba(0, 0, 0, 0.63) 0%,
    rgba(0, 0, 0, 0.75) 100%
  );
  border-radius: 4px;
}
.tab-circle {
  border-radius: 100%;
  width: 80px;
  height: 80px;
  position: absolute;
}

.tab-circle:nth-child(1) {
  background: radial-gradient(
    50% 50% at 50% 50%,
    rgba(153, 218, 147, 0) 6.56%,
    #93dab4 100%
  );
}
.tab-circle:nth-child(2) {
  background: radial-gradient(
    50% 50% at 50% 50%,
    rgba(147, 180, 218, 0) 6.56%,
    #93bcda 100%
  );
}
.tab-circle:nth-child(3) {
  background: radial-gradient(
    50% 50% at 50% 50%,
    rgba(221, 134, 134, 0) 6.56%,
    #dd6e6e 100%
  );
}
.tab-circle:nth-child(4) {
  background: radial-gradient(
    50% 50% at 50% 50%,
    rgba(218, 211, 147, 0) 6.56%,
    #dacf93 100%
  );
}
.tab-circle:nth-child(5) {
  background: radial-gradient(
    50% 50% at 50% 50%,
    rgba(218, 185, 147, 0) 6.56%,
    #dab593 100%
  );
}
.tab-circle:nth-child(6) {
  background: radial-gradient(
    50% 50% at 50% 50%,
    rgba(147, 171, 218, 0) 6.56%,
    #3261db 100%
  );
}
@keyframes resultAlert {
  from {
    width: 80px;
    height: 80px;
  }
  to {
    width: 100px;
    height: 100px;
  }
}
</style>
