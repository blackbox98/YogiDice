<template>
  <div id="more-game-card-container">
    <div
      v-for="lg in games"
      :key="lg.id"
      id="more-card"
      @click="showDetail(lg)"
    >
      <div class="border_bottom">
        <long-card-items :lg="lg"></long-card-items>
      </div>
    </div>
    <hr id="last-card-line" />
  </div>
</template>

<script>
import LongCardItems from "@/components/card/LongCardItems.vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";
import { computed, onMounted } from "vue";

export default {
  components: {
    LongCardItems,
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    let pageType = computed(() => store.state.games.morePageType);

    const showDetail = function (game) {
      router.push({
        name: "GameDetail",
        query: { gameId: game.id, title: game.titleKr },
      });
    };
    // 나중엔 store에서 받아올듯?
    let games = computed(() => store.state.games.longGames);
    let page = 1;

    onMounted(() => {
      const lastCard = document.querySelector("#last-card-line");
      const io = new IntersectionObserver((entries) => {
        if (pageType.value !== "추천") {
          entries.forEach((entry) => {
            if (entry.isIntersecting) {
              page++;
              store.dispatch("games/changeLongGames", {
                type: pageType.value,
                page: page,
              });
            }
          });
        }
      }); // 관찰자 초기화
      io.observe(lastCard); // 관찰할 대상 등록
    });
    return {
      games,
      showDetail,
    };
  },
};
</script>

<style>
.border_bottom {
  display: flex;
  width: 100vw;
  padding: 2vw 4vw 2vw 4vw;
  border-bottom: 2px solid var(--color-grey-3);
}
#more-game-card-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-content: center;
  flex-wrap: nowrap;
  overflow: scroll;
  width: 100vw;
  background-color: white;
}

#more-card {
  position: relative;
  display: flex;
  width: 100vw;
  /* border-bottom: 1px black solid; */
  background-color: white;
  overflow: hidden;
}
#last-card-line {
  width: 90vw;
  border-width: 0px;
  margin-top: -2vh;
}
</style>
