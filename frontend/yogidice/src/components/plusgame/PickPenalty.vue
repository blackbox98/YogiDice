<template>
  <div>
    <canvas> </canvas>
  </div>
  <div class="pick-penalty">
    <button id="starter" class="button-long-blue text-button">벌칙 뽑기</button>
  </div>
</template>

<script>
import { onMounted } from "@vue/runtime-core";
export default {
  setup() {
    let rfaid;
    let chars;
    let text;
    let scale;
    let ctx;
    let offset;
    let offsetV;
    let charMap;
    let breaks;
    let endSpeed;

    function play(sound) {
      if (sound) {
        var audio = new Audio(sound);
        audio.play();
      }
    }

    onMounted(() => {
      const setInit = function () {
        // The message displayed
        chars = [
          "3행시",
          "노래 부르기",
          "다음 게임 가져오기",
          "거울 보기",
          "계산은 내가 한다!",
          "딱밤 맞기",
          "나홀로 존댓말",
          "외래어 금지",
          "인디언 밥",
          "뿅망치 맞기",
          "내일은 정리왕",
          "음료 사오기",
          "간식 사오기",
        ];
        text = chars[Math.floor(Math.random() * chars.length)];
        scale = 50; // Font size and overall scale
        breaks = 0.0002; // Speed loss per frame
        endSpeed = 0.00001; // Speed at which the letter stops
        let firstLetter = 750; // Number of frames untill the first letter stopps (60 frames per second)
        let delay = 100; // Number of frames between letters stopping

        let canvas = document.querySelector("canvas");
        ctx = canvas.getContext("2d");

        charMap = [];
        offset = [];
        offsetV = [];

        for (var i = 0; i < chars.length; i++) {
          charMap[chars[i]] = i;
        }

        for (let i = 0; i < 1; i++) {
          let f = firstLetter + delay * i;
          offsetV[i] = endSpeed + breaks * f;
          offset[i] = (-(1 + f) * (breaks * f + 2 * endSpeed)) / 2;
        }

        (onresize = function () {
          canvas.width = canvas.clientWidth;
          canvas.height = canvas.clientHeight;
        })();
      };

      let loop = function () {
        let canvas = document.querySelector("canvas");
        ctx = canvas.getContext("2d");
        ctx.setTransform(1, 0, 0, 1, 0, 0);
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        ctx.globalAlpha = 1;
        ctx.fillStyle = "#49505733";
        ctx.fillRect(0, (canvas.height - scale) / 2, canvas.width, scale);

        ctx.fillStyle = "#000000";
        ctx.textBaseline = "middle";
        ctx.textAlign = "center";
        ctx.setTransform(
          1,
          0,
          0,
          1,
          canvas.width / 2 - scale,
          Math.floor(canvas.height / 2),
        );
        var o = offset[0];
        while (o < 0) o++;
        o %= 1;
        var h = Math.ceil(canvas.height / 2 / scale);
        for (var j = -h; j < h; j++) {
          var c = charMap[text] + j - Math.floor(offset[0]);
          while (c < 0) c += chars.length;
          c %= chars.length;
          var s = 1 - Math.abs(j + o) / (canvas.height / 2 / scale + 1);
          ctx.globalAlpha = s;
          ctx.font = scale * s + "px Pretendard";
          ctx.fillText(chars[c], scale, (j + o) * scale);
        }
        offset[0] += offsetV[0];
        offsetV[0] -= breaks;

        if (offsetV[0] < endSpeed) {
          offset[0] = 0;
          offsetV[0] = 0;
        }
        rfaid = requestAnimationFrame(loop);

        if (offset[0] === 0) {
          cancelAnimationFrame(rfaid);
        }
      };

      let btn = document.querySelector("#starter");
      btn.addEventListener("click", function () {
        setInit();
        play(require("@/static/Wheel_Spin_Sound.mp3"));
        loop();
      });
    });
    return {
      play,
    };
  },
};
</script>

<style>
canvas {
  background: var(--color-bg-base);
  height: 80vw;
  width: 100vw;
  top: 0;
}
.dump-area {
  height: 5vh;
}
.pick-penalty {
  width: 100vw;
  display: flex;
  justify-content: center;
}
</style>
