<template>
  <!-- 주사위 하나일때 -->
  <div v-if="diceview == 1">
    <div id="container">
      <div
        id="cube-1"
        :class="{
          show1: dicenum == 6,
          show2: dicenum == 1,
          show3: dicenum == 2,
          show4: dicenum == 3,
          show5: dicenum == 4,
          show6: dicenum == 5,
        }"
      >
        <div class="top"></div>
        <div class="front"></div>
        <div class="left"></div>
        <div class="back"></div>
        <div class="right"></div>
        <div class="bottom"></div>
      </div>
    </div>
    <div style="display: flex; justify-content: center">
      <div class="rollit text-button" id="roll-button" @click="clickDiceInfo">
        Roll It!
      </div>
      <div class="rollit text-button" id="roll-button" @click="diceview = 2">
        2 piece
      </div>
    </div>
  </div>
  <!-- 주사위 하나일때 -->

  <!-- 주사위 두개일때 -->
  <div v-if="diceview == 2">
    <div id="container-wrap">
      <div id="container-2">
        <div
          id="cube-2"
          :class="{
            show1: dicenum == 6,
            show2: dicenum == 1,
            show3: dicenum == 2,
            show4: dicenum == 3,
            show5: dicenum == 4,
            show6: dicenum == 5,
          }"
        >
          <div class="top"></div>
          <div class="front"></div>
          <div class="left"></div>
          <div class="back"></div>
          <div class="right"></div>
          <div class="bottom"></div>
        </div>
      </div>
      <div id="container-2">
        <div
          id="cube-2"
          :class="{
            show1: dicenum2 == 6,
            show2: dicenum2 == 1,
            show3: dicenum2 == 2,
            show4: dicenum2 == 3,
            show5: dicenum2 == 4,
            show6: dicenum2 == 5,
          }"
        >
          <div class="top"></div>
          <div class="front"></div>
          <div class="left"></div>
          <div class="back"></div>
          <div class="right"></div>
          <div class="bottom"></div>
        </div>
      </div>
    </div>
    <div style="display: flex; justify-content: center">
      <div class="rollit text-button" id="roll-button" @click="clickDiceInfo">
        Roll It!
      </div>
      <div class="rollit text-button" id="roll-button" @click="diceview = 1">
        1 piece
      </div>
    </div>
  </div>
  <!-- 주사위 두개일때 -->
</template>

<style>
#roll-button {
  font-size: 6vw;
  height: 8vh;
  background-color: var(--color-bg-modal);
}
.rollit {
  width: 100px;
  height: 50px;
  background-color: gray;
  display: flex;
  justify-content: center;
  align-items: center;
  margin-right: 10px;
  margin-left: 20px;
  border-radius: 20px;
  box-shadow: 0px 5px 4px 1px rgba(0, 0, 0, 0.2);
}
#container-wrap {
  display: flex;
  justify-content: space-evenly;
}
#container {
  margin: 50px auto;
  margin-top: 5vh;
  width: 200px;
  height: 200px;
  perspective: 800px;
  display: flex;
  justify-content: center;
  align-items: center;
}
#container-2 {
  margin-bottom: 0px;
  margin: 50px auto;
  margin-top: 9vh;
  width: 120px;
  height: 170px;
  perspective: 800px;
  display: flex;
  justify-content: center;
  align-items: center;
  /* background-color: pink; */
}
#cube-1 {
  position: relative;
  transform-style: preserve-3d;
  transition: transform 0.2s;
  width: 50%;
  height: 50%;
}

#cube-2 {
  position: relative;
  transform-style: preserve-3d;
  transition: transform 0.2s;
  width: 50%;
  height: 50%;
}

#cube-2 > div,
#cube-1 > div {
  border: 1px solid #ccc;
  border-radius: 20px;
  width: 98px;
  height: 98px;
  position: absolute;
  /* background-image: url(https://1.bp.blogspot.com/-8oHADm0XlWY/UV72I6qYX_I/AAAAAAAAD8U/83sdKrWjjRg/s1600/Dice.png); */
  background-repeat: no-repeat;
  opacity: 0.85;
}

.front {
  transform: rotateY(0deg) translateZ(48px);
  background-image: url("../../static/front.png");
  background-size: contain;
}
.back {
  transform: rotateX(180deg) translateZ(48px);
  background-image: url("../../static/back.png");
  background-size: contain;
}
.right {
  transform: rotateY(90deg) translateZ(48px);
  background-image: url("../../static/right.png");
  background-size: contain;
}
.left {
  transform: rotateY(-90deg) translateZ(48px);
  background-image: url("../../static/left.png");
  background-size: contain;
}
.top {
  transform: rotateX(90deg) translateZ(48px);
  background-image: url("../../static/top.png");
  background-size: contain;
}
.bottom {
  transform: rotateX(-90deg) translateZ(48px);
  background-image: url("../../static/bottom.png");
  background-size: contain;
}

#cube-1.show1,
#cube-2.show1 {
  transform: translateZ(-48px) rotateY(0deg);
}
#cube-1.show2,
#cube-2.show2 {
  transform: translateZ(-48px) rotateX(-180deg);
}
#cube-1.show3,
#cube-2.show3 {
  transform: translateZ(-48px) rotateY(-90deg);
}
#cube-1.show4,
#cube-2.show4 {
  transform: translateZ(-48px) rotateY(90deg);
}
#cube-1.show5,
#cube-2.show5 {
  transform: translateZ(-48px) rotateX(-90deg);
}
#cube-1.show6,
#cube-2.show6 {
  transform: translateZ(-48px) rotateX(90deg);
}
</style>

<script>
import { ref } from "@vue/runtime-core";

export default {
  setup() {
    const diceview = ref(1);
    const dicenum = ref(Math.floor(Math.random() * (6 - 1) + 1));
    const dicenum2 = ref(Math.floor(Math.random() * (6 - 1) + 1));
    function clickDice() {
      dicenum.value = Math.floor(Math.random() * (6 - 1) + 1);
      dicenum2.value = Math.floor(Math.random() * (6 - 1) + 1);
    }
    function clickDiceInfo() {
      play(require("@/static/Roll_Dice_Sound.mp3"));
      for (let i = 1; i < 14; i++) {
        setTimeout(() => clickDice(), 190 * i);
      }
    }
    function play(sound) {
      if (sound) {
        var audio = new Audio(sound);
        audio.play();
      }
    }

    return {
      dicenum,
      dicenum2,
      clickDiceInfo,
      diceview,
      play,
    };
  },
};
</script>
