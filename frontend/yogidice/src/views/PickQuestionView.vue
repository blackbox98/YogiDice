<template>
  <div>
    <grey-bg-head-bar></grey-bg-head-bar>
  </div>
  <div class="question-cover">
    <div class="question-page">
      <div class="progress-bar"></div>
      <img class="dice-dice" src="../static/dice.png" alt="" />
      <div class="progress-steps">
        <div class="step step-1"></div>
        <div class="step step-2"></div>
        <div class="step step-3"></div>
        <div class="step step-4"></div>
        <div class="step step-5"></div>
      </div>
    </div>
    <question-list></question-list>
  </div>
</template>

<script>
import QuestionList from "@/components/QuestionList.vue";
import { getCurrentInstance, onMounted } from "@vue/runtime-core";
import GreyBgHeadBar from "@/layouts/GreyBgHeadBar.vue";
import { useRouter } from "vue-router";

export default {
  components: {
    QuestionList,
    GreyBgHeadBar,
  },
  setup() {
    const router = useRouter();
    const internalInstance = getCurrentInstance();
    const emitter = internalInstance.appContext.config.globalProperties.emitter;
    onMounted(() => {
      const dice = document.querySelector(".dice-dice");
      emitter.on("moveToFirst", function () {
        dice.style.animationName = ""
        dice.style.left = "8vw";
      });
      emitter.on("moveToTwo", function () {
        dice.style.animationName = "moveToTwo";
      });
      emitter.on("moveToThree", function () {
        dice.style.animationName = "moveToThree";
      });
      emitter.on("moveToFour", function () {
        dice.style.animationName = "moveToFour";
      });
      emitter.on("moveToFive", function () {
        dice.style.animationName = "moveToFive";
      });
      emitter.on("backToBefore", (data) => {
        dice.style.animationName = `backTo${data}`;
      });
    });
    const gomain = function () {
      router.push({ name: "MainPage" });
    };

    return {
      gomain,
    };
  },
};
</script>

<style>
.question-cover {
  width: 100vw;
  height: 90vh;
  background-color: rgba(255, 255, 255, 0.7);
  padding-top: 15vh;
  overflow: visible;
}
.question-page {
  width: 100%;
  position: relative;
}
.progress-bar {
  margin-left: 5vw;
  width: 90vw;
  background-color: grey;
  border: 3px solid #6c757d;
}
.progress-steps {
  top: -5vw;
  position: absolute;
  display: flex;
  margin-left: 10vw;
  width: 80vw;
  justify-content: space-evenly;
  gap: 5vw;
}
.step {
  width: 8vw;
  height: 8vw;
  background: #ced4da;
  border: 1.5vw solid #6c757d;
  border-radius: 50%;
}
.dice-dice {
  position: absolute;
  top: -6vw;
  left: 8vw;
  z-index: 100;
  width: 16vw;
  animation-duration: 1s;
  animation-iteration-count: 1;
  animation-direction: alternate;
  animation-fill-mode: forwards;
  transform-origin: 50% 50%;
}
@keyframes moveToTwo {
  from {
    left: 8vw;
    transform: rotate(0deg);
  }
  to {
    left: 25vw;
    transform: rotate(360deg);
  }
}
@keyframes moveToThree {
  from {
    left: 25vw;
    transform: rotate(0deg);
  }
  to {
    left: 41vw;
    transform: rotate(360deg);
  }
}
@keyframes moveToFour {
  from {
    left: 41vw;
    transform: rotate(0deg);
  }
  to {
    left: 58vw;
    transform: rotate(360deg);
  }
}
@keyframes moveToFive {
  from {
    left: 58vw;
    transform: rotate(0deg);
  }
  to {
    left: 75vw;
    transform: rotate(360deg);
  }
}
@keyframes backTo4 {
  from {
    left: 75vw;
    transform: rotate(3600deg);
  }
  to {
    left: 58vw;
    transform: rotate(0deg);
  }
}
@keyframes backTo3 {
  from {
    left: 58vw;
    transform: rotate(3600deg);
  }
  to {
    left: 41vw;
    transform: rotate(0deg);
  }
}
@keyframes backTo2 {
  from {
    left: 41vw;
    transform: rotate(3600deg);
  }
  to {
    left: 25vw;
    transform: rotate(0deg);
  }
}
@keyframes backTo1 {
  from {
    left: 25vw;
    transform: rotate(3600deg);
  }
  to {
    left: 8vw;
    transform: rotate(0deg);
  }
}
</style>
