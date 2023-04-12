<template>
  <div class="cafe-games-box">
    <div @click="closeCafeGame" class="close-button">
      <span class="material-icons-outlined">close</span>
    </div>
    <div class="cafe-games-title text-headline-6">{{ name }}의 보유 게임</div>
    <hr class="line-cafe-game" />
    <br />
    <div class="cafe-games-count text-body-1">총 {{ count }}개의 게임</div>
    <div>
      <small-card-list></small-card-list>
    </div>
  </div>
</template>

<script>
import SmallCardList from "@/components/card/SmallCardList.vue";
import { useStore } from "vuex";
import { computed, ref } from "@vue/runtime-core";

export default {
  components: {
    SmallCardList,
  },
  props: {
    name: String,
  },
  setup(props, { emit }) {
    const store = useStore();
    const count = ref(0);
    count.value = computed(() => store.state.games.smallGamesLen);
    const closeCafeGame = function () {
      emit("closeCafeModal");
      store.dispatch("games/resetSmallLenGames");
    };
    return {
      closeCafeGame,
      count,
    };
  },
};
</script>

<style>
.cafe-games-box {
  display: flex;
  flex-direction: column;
  width: 94vw;
  height: 90vh;
  z-index: 999;
  background-color: white;
  position: absolute;
  top: 5vh;
  left: 3vw;
  border-radius: 4px;
  padding-top: 4vh;
}
.search-box {
  width: 94vw;
  padding: 2vh 2vw;
}
.cafe-games-title {
  color: black;
}
.cafe-games-count {
  color: black;
  text-align: start;
  margin-left: 8vw;
  margin-bottom: 2vh;
}
.line-cafe-game {
  width: 88vw;
  background-color: white;
  margin: 2vh 3vw;
}
.close-button {
  margin-right: 4vw;
}
</style>
