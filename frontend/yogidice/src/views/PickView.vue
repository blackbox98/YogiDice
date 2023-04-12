<template>
  <div>
    <grey-bg-head-bar></grey-bg-head-bar>
  </div>
  <div class="home-cover">
    <div class="pick-intro-title text-huge">당신의 겜성은?</div>
    <br />
    <div class="pick-intro-describe text-body-1">
      요기다이스의 빅데이터를 기반으로 <br />
      당신의 게임 성향(겜성)을 찾아드려요
    </div>
    <div class="pick-intro-icons">
      <div class="pick-intro-icon">
        <div>질문</div>
        <div style="font-size: 2rem">📃</div>
        <div>5문항</div>
      </div>
      <div class="pick-intro-icon">
        <div>소요시간</div>
        <div style="font-size: 2rem">⏱</div>
        <div>1분</div>
      </div>
    </div>
    <div class="button-box">
      <div class="pick-start" @click="startPick">검사시작</div>
    </div>
  </div>
</template>

<script>
import { useRouter } from "vue-router";
import { getCurrentInstance } from "vue"
import GreyBgHeadBar from "@/layouts/GreyBgHeadBar.vue";
export default {
  components: {
    GreyBgHeadBar,
  },
  setup() {
    const router = useRouter();
    const internalInstance = getCurrentInstance();
    const emitter = internalInstance.appContext.config.globalProperties.emitter;
    const startPick = function () {
      router.push({ name: "QuestionView" });
      emitter.emit("moveToFirst");
    };
    const gomain = function () {
      router.push({ name: "MainPage" });
    };

    return {
      startPick,
      gomain,
    };
  },
};
</script>

<style scoped>
.home-cover {
  width: 90vw;
  height: 100vh;
  padding: 20vh 5vw;
  background-color: var(--color-bg-modal);
  color: white;
  font-family: "Pretendard";
}
.pick-intro-title {
  margin-bottom: 3rem;
}
.pick-intro-describe {
  margin-bottom: 1.3rem;
}
.pick-intro-icons {
  display: flex;
  justify-content: center;
  margin: 3rem 0;
  gap: 3rem;
}
.pick-intro-icon {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 3vw;
  font-size: 1rem;
}
.button-box {
  display: flex;
  width: 100%;
  justify-content: center;
  margin-top: 10vh;
}
.pick-start {
  border-radius: 40px 40px;
  font-size: 1rem;
  background-color: none;
  border: 3px solid white;
  width: 40vw;
  padding: 8px;
  text-align: center;
}
</style>
