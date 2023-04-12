<template>
  <div class="review-components-overflow">
    <!-- 데이터 없을때 -->
    <div v-if="reviewdatum.length == 0" class="review-components-msg">
      <div>
        <div>남긴 리뷰가 없습니다.</div>
        <div>리뷰를 남겨보세요! 😄</div>
      </div>
    </div>
    <!-- 데이터 없을때 -->

    <!-- 데이터 들어오면 -->
    <review-components
      class="review-components-overflow"
      :reviewdatum="reviewdatum"
      :reviewId="reviewId"
      @OpenReviewModal="OpenReviewModal"
    ></review-components>
    <!-- 데이터 들어오면 -->
  </div>
</template>

<script>
import ReviewComponents from "./card/ReviewComponents.vue";
import { getCurrentInstance, computed } from "vue";
import { useStore } from "vuex";

export default {
  components: {
    ReviewComponents,
  },
  emits: ["OpenReviewModal"],
  setup() {
    const store = useStore();
    const { emit } = getCurrentInstance();
    const reviewdatum = computed(() => store.state.myuser.history);

    const OpenReviewModal = function (data) {
      emit("OpenReviewModal", data);
    };
    return {
      reviewdatum,
      OpenReviewModal,
    };
  },
};
</script>

<style>
.review-components-overflow {
  align-items: center;
  overflow: auto;
  height: 50vh;
  width: 90vw;
  /* background-color: pink; */
}
.review-components-overflow::-webkit-scrollbar {
  width: 0px;
}
.review-components-msg {
  font-size: 20px;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  width: 90vw;
  height: 90vw;
  background-color: rgba(255, 255, 255, 0.823);
}
</style>
