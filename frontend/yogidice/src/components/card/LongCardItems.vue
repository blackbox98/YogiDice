<template>
  <div id="long-card">
    <img class="long-img" :src="thumbUrl" alt="" @click="showDetail(lg)" />
    <div class="game-info" @click="showDetail(lg)">
      <div class="game-title text-subtitle-1">{{ titleKr }}</div>
      <div class="chip-rating">
        <div class="text-subtitle-1 rating">★{{ rate }}</div>
        <div class="game-chip-container">
          <div>{{ `${minPlayers}~${maxPlayers}인` }}</div>
          <div>{{ `${playingTime}분 소요` }}</div>
          <div>{{ level }}</div>
        </div>
      </div>
    </div>
    <div class="bookmark-alert text-subtitle-1" v-show="showRegister">
      북마크가 등록되었습니다!<span class="material-icons">check_circle</span>
    </div>
    <span
      class="material-icons bookmark-icon"
      v-show="!isGameinBookMark"
      @click="registerBookMark"
      >bookmark_border</span
    >
    <span
      class="material-icons bookmark-icon bookmarked"
      v-show="isGameinBookMark"
      @click="registerBookMark"
      >bookmark</span
    >
  </div>
</template>

<script>
import { toRefs, ref, computed } from "vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";
export default {
  props: {
    lg: Object,
  },
  setup(props) {
    const store = useStore();
    const router = useRouter();
    let checkedMark = ref(false);

    let { titleKr } = toRefs(props.lg);
    let { thumbUrl } = toRefs(props.lg);
    let { id } = toRefs(props.lg);
    let { maxPlayers } = toRefs(props.lg);
    let { minPlayers } = toRefs(props.lg);
    let { playingTime } = toRefs(props.lg);
    let { ratingUser } = toRefs(props.lg);
    let { difficulty } = toRefs(props.lg);
    let rate = ratingUser.value.toFixed(2);
    let level = computed(() => {
      if (difficulty.value <= 1) {
        return "매우 쉬움";
      } else if (difficulty.value <= 2) {
        return "쉬움";
      } else if (difficulty.value <= 3) {
        return "보통";
      } else if (difficulty.value <= 4) {
        return "어려움";
      } else {
        return "매우 어려움";
      }
    });

    let myBookMarks = computed(() =>
      store.state.user.myBookMark.map((mark) => mark.gameId),
    );
    let isGameinBookMark = computed(() => myBookMarks.value.includes(id.value));

    const showDetail = function (n) {
      router.push({
        name: "GameDetail",
        query: { gameId: n.id, title: n.titleKr },
      });
      store.dispatch("games/getDetails", n.id);
    };

    let showRegister = ref(false);
    const registerBookMark = function () {
      if (isGameinBookMark.value === true) {
        store.dispatch("user/deleteBookMark", id.value);
      } else {
        showRegister.value = true;
        //북마크 등록 api
        store.dispatch("user/registBookMark", id.value);
        setTimeout(() => (showRegister.value = false), 3000);
      }
    };

    return {
      titleKr,
      id,
      thumbUrl,
      checkedMark,
      registerBookMark,
      showRegister,
      maxPlayers,
      minPlayers,
      playingTime,
      ratingUser,
      difficulty,
      isGameinBookMark,
      showDetail,
      level,
      rate,
    };
  },
};
</script>

<style scoped>
.bookmark-alert {
  width: 92vw;
  height: 20vw;
  background-color: var(--color-bg-modal);
  color: white;
  position: absolute;
  text-align: center;
  line-height: 20vw;
}
.game-title {
  width: 80%;
  text-align: left;
}
.long-img {
  width: 20vw;
  height: 20vw;
}
.game-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  width: 68vw;
  gap: 1vh;
  padding: 4px 0 4px 2vw;
}
.chip-rating {
  display: flex;
  width: 100%;
  height: 4vh;
  justify-content: space-between;
}
.rating {
  text-align: start;
  align-self: center;
  width: 22%;
}
.game-chip-container {
  margin: 0px;
  align-self: center;
}

.bookmark-icon {
  position: absolute;
  top: -8px;
  right: 8px;
  font-size: 8vw;
}
#long-card {
  position: relative;
  display: flex;
  width: 90vw;
  height: 20vw;
  box-shadow: var(--shadow-card);
  border-radius: 4px;
  background-color: white;
  overflow: hidden;
}
</style>
