<template>
  <div class="search-bar">
    <span class="material-icons">search</span>
    <input
      type="text"
      @keyup.enter="inputValue"
      v-model="searchInput"
      placeholder="보드게임을 검색해보세요!"
    />
    <span class="material-icons" @click="eraser">close</span>
  </div>
</template>

<script>
import { getCurrentInstance, ref } from "vue";
export default {
  setup() {
    const internalInstance = getCurrentInstance();
    // 전역변수로 선언해놓은 mitt 가져오기
    const emitter = internalInstance.appContext.config.globalProperties.emitter;
    let searchInput = ref("");

    const inputValue = function () {
      emitter.emit("inputValue", searchInput.value);
    };
    const eraser = function () {
      searchInput.value = "";
      inputValue();
    };

    return {
      searchInput,
      inputValue,
      eraser,
    };
  },
};
</script>

<style>
.search-bar {
  box-sizing: border-box;

  /* Auto layout */
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 3px 10px;
  gap: 8px;

  width: 90vw;
  height: 32px;
  left: 5vw;

  /* white */

  background: #ffffff;
  /* black */

  border: 1px solid #000000;
  border-radius: 8px;
}
.search-bar input {
  width: 100%;
  border-width: 0ch;
}
</style>
