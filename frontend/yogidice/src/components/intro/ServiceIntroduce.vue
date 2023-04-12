<template>
  <div id="introduce-slide-container">
    <div id="introduce-slide-box">
      <div class="slide-box">
        <div class="text-huge">
          빅데이터를 통해 <br />
          다양한 게임을 <br />
          만나보세요!
        </div>
        <div class="text-headline-6 introduce-content">
          요기다이스는 총 15천여개의 <br />
          게임 데이터를 활용하여 <br />
          사용자에게 게임을 추천합니다.
        </div>
      </div>
      <div class="slide-box">
        <div class="text-huge">
          나의 보드게임 취향을 <br />
          한눈에 확인하세요!
        </div>
        <div class="text-headline-6 introduce-content">
          보드게임의 진행방식, 난이도, <br />
          카테고리 등을 기반으로 분석한 <br />
          사용자의 취향을 제공합니다.
        </div>
      </div>
      <div class="slide-box">
        <div class="text-huge">
          근처 보드게임 카페를 <br />
          확인하세요!
        </div>
        <div class="text-headline-6 introduce-content">
          사용자의 위치를 기준으로 주변 <br />
          보드게임 카페 정보를 제공합니다. <br /><br />
          또한, 사용자가 원하는 게임이 카페에 있는지도 확인이 가능합니다!
        </div>
      </div>
      <div class="slide-box">
        <div class="text-huge">언제 어디서든<br />보드게임을 즐기세요!</div>
        <div class="text-headline-6 introduce-content">
          보드게임을 진행할 때 필요한 <br />
          선정하기, 벌칙 정하기 등 다양한 <br />
          추가기능을 제공합니다!
        </div>
      </div>
    </div>
    <div id="button-box">
      <button class="slide-1 active"></button>
      <button class="slide-2"></button>
      <button class="slide-3"></button>
      <button class="slide-4"></button>
    </div>
  </div>
  <span class="material-icons prev">arrow_back_ios</span>
  <span class="material-icons next">arrow_forward_ios</span>
</template>

<script>
import { onMounted } from "@vue/runtime-core";
export default {
  setup() {
    onMounted(function () {
      let pageNum = 1;
      const buttons = document.querySelectorAll("#button-box button");

      const changePagination = function () {
        for (let i = 0; i < 4; i++) {
          if (i + 1 === pageNum) {
            buttons[i].classList.add("active");
          } else if (buttons[i].classList.contains("active")) {
            buttons[i].classList.remove("active");
          }
        }
      };
      const slideContainer = document.querySelector("#introduce-slide-box");
      const slide1 = document.querySelector(".slide-1");
      const slide2 = document.querySelector(".slide-2");
      const slide3 = document.querySelector(".slide-3");
      const slide4 = document.querySelector(".slide-4");
      const prevBtn = document.querySelector(".prev");
      const nextBtn = document.querySelector(".next");

      slide1.addEventListener("click", function () {
        pageNum = 1;
        slideContainer.style.transform = "translateX(0vw)";
        changePagination();
      });
      slide2.addEventListener("click", function () {
        pageNum = 2;
        slideContainer.style.transform = "translateX(-100vw)";
        changePagination();
      });
      slide3.addEventListener("click", function () {
        pageNum = 3;
        slideContainer.style.transform = "translateX(-200vw)";
        changePagination();
      });
      slide4.addEventListener("click", function () {
        pageNum = 4;
        slideContainer.style.transform = "translateX(-300vw)";
        changePagination();
      });
      prevBtn.addEventListener("click", function () {
        if (pageNum > 1) {
          slideContainer.style.transform = `translateX(${
            (pageNum - 1) * -100 + 100
          }vw)`;
          pageNum -= 1;
          changePagination();
        } else {
          slideContainer.style.transform = `translateX(${
            (pageNum + 3) * -100 + 100
          }vw)`;
          pageNum = 4;
          changePagination();
        }
      });
      nextBtn.addEventListener("click", function () {
        if (pageNum < 4) {
          slideContainer.style.transform = `translateX(${
            (pageNum - 1) * -100 - 100
          }vw)`;
          pageNum += 1;
          changePagination();
        } else {
          slideContainer.style.transform = `translateX(${
            (pageNum - 3) * 100 - 100
          }vw)`;
          pageNum = 1;
          changePagination();
        }
      });
      setInterval(() => {
        if (pageNum < 4) {
          slideContainer.style.transform = `translateX(${
            (pageNum - 1) * -100 - 100
          }vw)`;
          pageNum += 1;
          changePagination();
        } else {
          slideContainer.style.transform = `translateX(${
            (pageNum - 3) * 100 - 100
          }vw)`;
          pageNum = 1;
          changePagination();
        }
      }, 3000);
    });
  },
};
</script>

<style scoped>
.prev {
  position: absolute;
  top: 50%;
  left: 2%;
}

.next {
  position: absolute;
  top: 50%;
  right: 2%;
}

#button-box {
  height: 20vh;
  display: flex;
  justify-content: center;
  align-items: flex-end;
  margin-top: 10vh;
}

button {
  border-radius: 50%;
  width: 16px;
  height: 16px;
  background-color: black;
  margin: 4px;
  border-width: 0px;
}

button.active {
  background-color: grey;
}

#introduce-slide-box {
  width: 400vw;
  transition: all 0.5s;
  margin-top: 10vh;
}
#introduce-slide-container {
  overflow: hidden;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
}
.slide-box {
  width: 80vw;
  float: left;
  margin-left: 10vw;
  margin-right: 10vw;
  text-align: center;
}

.slide-box .introduce-content {
  margin-top: 80px;
  justify-content: center;
  align-content: center;
  height: 10vh;
}

.text-huge {
  justify-content: center;
  align-content: center;
  height: 10vh;
}
</style>
