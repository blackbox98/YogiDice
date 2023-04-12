<template>
  <div id="small-game-card-container">
    <div
      v-for="game in smallGameLists"
      :key="game.key"
      id="small-card"
      v-show="!isSearching"
    >
      <game-card-items :game="game"></game-card-items>
    </div>

    <div
      v-for="res in searchResultLists"
      :key="res.id"
      id="search-card"
      v-show="isSearching"
    >
      <game-card-items :game="res"></game-card-items>
    </div>
    <hr id="last-card-line" />
  </div>
</template>

<script>
import { getCurrentInstance, computed, ref } from "vue";
import { useStore } from "vuex";
import GameCardItems from "@/components/card/SmallCardItems.vue";

export default {
  components: {
    GameCardItems,
  },
  setup() {
    const store = useStore();

    let isSearching = ref(false);

    let smallGameLists = computed(() => store.state.games.smallGames);
    let searchResultLists = computed(() => store.state.games.searchResult);

    // mitt 쓰기
    const internalInstance = getCurrentInstance();
    const emitter = internalInstance.appContext.config.globalProperties.emitter;

    async function searchRes(info) {
      if (info.gameTitle === "") {
        isSearching.value = false;
      } else {
        isSearching.value = true;
        await store.dispatch("games/searchGames", info, { root: true });
      }
    }

    // 데이터 받기 (SearchBar로부터 받음)
    emitter.on("inputValue", (data) => {
      let info = { gameTitle: data, searchLoc: "선호도조사" };
      searchRes(info);
    });

    return {
      smallGameLists,
      isSearching,
      searchResultLists,
    };
  },
};
</script>

<style>
#small-game-card-container {
  display: flex;
  justify-content: flex-start;
  align-content: flex-start;
  flex-wrap: wrap;
  overflow: scroll;
  gap: 2vw;
  width: 94vw;
  height: 53vh;
  padding-left: 4vw;
}

#small-game-card-container div {
  width: 28vw;
  overflow: hidden;
}

#small-card {
  position: relative;
}

#search-card {
  position: relative;
}

#last-card-line {
  width: 90vw;
  border-width: 0px;
  margin-top: -2vh;
}
</style>
