<template>
  <header>
    <div class="header-box">
      <div class="header-1">
        <img
          id="head-logo"
          @click="moveToMain"
          src="../static/logo_white.png"
          alt=""
        />
      </div>
      <div id="head-title" class="text-headline-6">요기 Dice</div>
      <div class="header-2">
        <span id="head-profile" @click="moveToMypage" class="material-icons"
          >person</span
        >
      </div>
    </div>
  </header>
  <router-view id="wrap2"></router-view>
</template>

<script>
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();

    const moveToMain = function () {
      router.push({ name: "MainPage" });
    };
    const moveToMypage = function () {
      router.push({ name: "MypageView" });
    };
    return {
      moveToMain,
      moveToMypage,
    };
  },
};
</script>

<style>
header {
  position: sticky;
}
#wrap2 {
  background-color: var(--color-bg-base);
  height: 94vh;
  overflow-x: hidden;
  overflow-y: scroll;
}
.header-box {
  display: flex;
  justify-content: center;
  height: 6vh;
  padding: 3vw 4vw 0vw 4vw;
  gap: 4vw;
  background: black;
}
.header-1 {
  display: flex;
  flex-direction: column;
  justify-content: flex-end;
  width: 16vw;
}
.header-2 {
  display: flex;
  justify-content: flex-end;
  align-self: center;
  width: 16vw;
}
#head-title {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 60vw;
  font-size: 5vw;
  color: white;
}
#head-profile {
  font-size: 8vw;
  color: white;
}
</style>
