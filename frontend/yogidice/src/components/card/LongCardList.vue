<template>
  <div id="long-game-card-container">
    <div class="searchMsg text-subtitle-1" v-show="showFailMsg">
      찾으시는 게임이 없습니다😥
    </div>
    <div v-for="lg in longGameLists" :key="lg.id">
      <long-card-items :lg="lg"></long-card-items>
    </div>
  </div>
</template>

<script>
import { computed, getCurrentInstance } from "vue";
import LongCardItems from "@/components/card/LongCardItems.vue";
import { useStore } from "vuex";

export default {
  components: {
    LongCardItems,
  },
  setup() {
    const store = useStore();

    let longGameLists = computed(() => store.state.games.longGames);
    let showFailMsg = computed(() => longGameLists.value.length === 0);

    // mitt 쓰기
    const internalInstance = getCurrentInstance();
    const emitter = internalInstance.appContext.config.globalProperties.emitter;

    async function searchRes(info) {
      if (info.gameTitle === "") {
        showFailMsg.value = true;
      } else {
        await store.dispatch("games/searchGames", info, { root: true });
      }
    }

    // 데이터 받기 (SearchBar로부터 받음)
    emitter.on("inputValue", (data) => {
      let info = { gameTitle: data, searchLoc: "메인페이지" };
      searchRes(info);
    });

    return {
      longGameLists,
      showFailMsg,
    };
  },
};
</script>

<style>
.searchMsg {
  color: white;
  text-align: center;
  width: 90vw;
  margin-top: 20px;
  font-size: 20px;
}
#long-game-card-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-content: center;
  flex-wrap: nowrap;
  gap: 2vw;
  width: 90vw;
  padding: 5vw;
}
</style>
