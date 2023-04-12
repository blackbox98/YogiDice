<template>
  <div class="related-box">
    <div class="related-game-container">
      <h3>이 게임이 좋았다면?</h3>
      <main-card-list class="related-game-card"></main-card-list>
    </div>
    <div class="related-game-container">
      <sub-card-list></sub-card-list>
    </div>
  </div>
</template>

<script>
import MainCardList from "../card/MainCardList.vue";
import SubCardList from "../card/SubCardList.vue";
export default {
  components: {
    MainCardList,
    SubCardList,
  },
};
</script>

<style>
.related-game-container {
  margin: 2vh 2vw;
  overflow: scroll;
}
.related-box {
  overflow-x: scroll;
}
.related-game-card > div.card-more {
  visibility: hidden;
}
</style>
