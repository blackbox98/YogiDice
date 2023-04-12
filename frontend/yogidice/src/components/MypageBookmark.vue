<template>
  <div class="my-bookmark-tag">
    <div v-for="game in games" :key="game.key" @click="showDetail(game)">
      <img class="smallImg" @click="selectImg" :src="game.thumbUrl" alt="" />
      <p class="text-subtitle-2" style="width: 25vw">{{ game.gameTitle }}</p>
    </div>
  </div>
</template>

<script>
import { computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();
    const store = useStore();
    let games = computed(() => store.state.myuser.bookmark);
    const showDetail = function (n) {
      router.push({
        name: "GameDetail",
        query: { gameId: n.gameId, title: n.gameTitle },
      });
      store.dispatch("games/getDetails", n.gameId);
    };

    return {
      games,
      showDetail,
    };
  },
};
</script>

<style>
.my-bookmark-tag {
  display: flex;
  justify-content: flex-start;
  align-content: flex-start;
  flex-wrap: wrap;
  overflow: scroll;
  gap: 2vw;
  width: 94vw;
  height: 50vh;
  padding-left: 4vw;
}
.smallImg {
  width: 28vw;
  height: 28vw;
  object-fit: cover;
}
.smallImg + p {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-top: 0px;
}
#checked-mark {
  color: white;
  position: absolute;
  top: 8px;
  right: 6px;
  font-size: 8vw;
}
/* .text-subtitle-2 {
    width: 28vw;
    overflow: hidden;
} */
</style>
