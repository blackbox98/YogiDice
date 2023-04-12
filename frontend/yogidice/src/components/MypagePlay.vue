<template>
  <div>
    <!-- 데이터 없을때 -->
    <div v-if="userplaygames.length == 0" class="review-components-msg">
      <div>
        <div>아직 플레이한 게임이 없습니다</div>
        <div>게임을 플레이해보세요! 😄</div>
      </div>
    </div>
    <!-- 데이터 없을때 -->

    <!-- 데이터 들어오면 -->
    <div>
      <div v-for="game in userplaygames" :key="game.id">
        <div class="play-card-case">
          <img class="long-img" :src="game.thumbUrl" />
          <div class="game-info">
            <div class="my-game-title" @click="showDetail(game)">
              {{ game.gameTitle }}
            </div>
            <div v-if="game.rating" class="chip-rating">
              <span class="play-my-rating-title">내가 준 별점</span>
              <div class="rating">★{{ game.rating }}</div>
            </div>
            <div v-else class="chip-rating">
              <span class="play-my-rating-title writed-yet"
                >리뷰를 작성해보세요</span
              >
              <i
                class="far fa-edit play-my-icon"
                @click="
                  $emit('OpenReviewModal', [
                    game.gameTitle,
                    1,
                    game.review,
                    game.id,
                  ])
                "
              ></i>
            </div>
          </div>
        </div>
      </div>
      <div style="height: 100px"></div>
    </div>
    <!-- 데이터 들어오면 -->
  </div>
</template>

<script>
import { reactive } from "@vue/runtime-core";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  props: {
    userplaygames: Object,
  },
  emits: ["OpenReviewModal"],
  setup(props) {
    const router = useRouter();
    const store = useStore();
    const datum = reactive(props.userplaygames);
    const showDetail = function (n) {
      router.push({
        name: "GameDetail",
        query: { gameId: n.gameId, title: n.gameTitle },
      });
      store.dispatch("games/getDetails", n.gameId);
    };

    return {
      datum,
      showDetail,
    };
  },
};
</script>

<style>
.play-my-icon {
  padding: 10px;
}
.play-my-rating-title {
  margin-top: 4px;
  padding: 5px;
}
.writed-yet {
  font-size: 15px;
}
.play-card-case {
  display: flex;
  width: 85vw;
  padding: 2vw 4vw 2vw 4vw;
  border-bottom: 2px solid var(--color-grey-3);
  background-color: white;
}

.my-game-title {
  width: 80%;
  height: 25px;
  overflow: hidden;
  text-align: left;
  font-weight: bold;
  font-size: 20px;
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
  width: 90%;
  height: 4vh;
}
.rating {
  text-align: start;
  align-self: center;
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
</style>
