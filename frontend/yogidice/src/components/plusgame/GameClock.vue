<template>
  <div class="clock-box">
    <div class="timer-choice">
      <div @click="toStopWatch" class="button-small-blue text-button">
        스톱워치
      </div>
      <div @click="toTimer" class="button-small-blue text-button">타이머</div>
    </div>
    <div v-show="showStopWatch">
      <stop-watch></stop-watch>
    </div>
    <div v-show="showTimer">
      <game-timer></game-timer>
    </div>
  </div>
</template>

<script>
import StopWatch from "@/components/plusgame/StopWatch.vue";
import GameTimer from "@/components/plusgame/GameTimer.vue";
import { ref } from "vue";
export default {
  components: {
    StopWatch,
    GameTimer,
  },
  setup() {
    let showStopWatch = ref(true);
    let showTimer = ref(false);

    const toStopWatch = function () {
      if (showStopWatch.value === false) {
        showStopWatch.value = true;
        showTimer.value = false;
      }
    };
    const toTimer = function () {
      if (showTimer.value === false) {
        showStopWatch.value = false;
        showTimer.value = true;
      }
    };

    return {
      showStopWatch,
      showTimer,
      toTimer,
      toStopWatch,
    };
  },
};
</script>

<style>
.timer-choice {
  display: flex;
  justify-content: center;
  gap: 2vw;
  margin-top: 4vh;
}
.clock-box {
  height: 90vh;
}
</style>
