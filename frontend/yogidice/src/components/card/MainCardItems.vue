<template>
  <img class="main-card-img" :src="thumbUrl" alt="" />
  <div class="title-rating">
    <div class="game-title text-subtitle-1">{{ titleKr }}</div>
    <div class="text-subtitle-1 rating">★{{ rate }}</div>
  </div>
  <div class="game-chip-container">
    <div>
      {{ `${mainGame.minPlayers.value}~${mainGame.maxPlayers.value}인` }}
    </div>
    <div>{{ mainGame.playingTime }}분</div>
    <div>{{ level }}</div>
  </div>
</template>

<script>
import { toRefs, reactive, ref, computed } from "vue";

export default {
  props: {
    game: Object,
  },
  setup(props) {
    let mainGame = toRefs(props.game);
    let { titleKr } = toRefs(props.game);
    let thumbUrl = mainGame.thumbUrl;
    let { difficulty } = toRefs(props.game);
    let rate = computed(() => mainGame.ratingUser.value.toFixed(2));

    let checkedMark = ref(false);

    const userSelection = reactive([]);

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

    return {
      mainGame,
      userSelection,
      checkedMark,
      thumbUrl,
      titleKr,
      level,
      rate,
    };
  },
};
</script>

<style>
.main-card-img {
  width: 56vw;
  height: 56vw;
  object-fit: fill;
  border-bottom: 1px solid black;
  border-radius: 4px 4px 0 0;
}
.title-rating {
  display: flex;
  justify-content: space-between;
  width: 90%;
  margin: 2vw 2vw 0vw 2vw;
  align-items: flex-start;
}
.game-title {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.rating {
  color: var(--color-mint);
  width: 30%;
  text-align: right;
}
.game-chip-container {
  display: flex;
  justify-content: flex-start;
  margin: 2vw 2vw 2vw 2vw;
  gap: 1vw;
  align-self: flex-start;

  font-family: "Pretendard";
  font-style: normal;
  font-weight: 500;
}
.game-chip-container > div {
  border-radius: 40px;
  padding: 2px 6px 2px 6px;
  font-size: 12px;
  font-weight: bold;
}
.game-chip-container div:nth-child(1) {
  background-color: var(--color-chip-bg-blue);
  color: var(--color-chip-letter-blue);
}
.game-chip-container div:nth-child(2) {
  background-color: var(--color-chip-bg-beige);
  color: var(--color-chip-letter-beige);
}
.game-chip-container div:nth-child(3) {
  background-color: var(--color-chip-bg-purple);
  color: var(--color-chip-letter-purple);
}
</style>
