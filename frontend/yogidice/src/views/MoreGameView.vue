<template>
  <div class="back-page-wall">
    <div class="type-comment-bg top-ten-bg text-subtitle-2" v-show="isTopTen">
      <div class="type-comment">
        가장 평점이 <br />
        높은 게임입니다🏅
      </div>
    </div>
    <div class="type-comment-bg recent-bg text-subtitle-2" v-show="isRecent">
      <div class="type-comment">
        가장 최근에 <br />
        출시된 게임입니다🏅
      </div>
    </div>
    <div class="type-comment-bg review-bg text-subtitle-2" v-show="isReview">
      <div class="type-comment">
        가장 리뷰가 <br />
        많은 게임입니다🏅
      </div>
    </div>
    <div class="type-comment-bg review-bg text-subtitle-2" v-show="isRecommend">
      <div class="type-comment">
        자신의 리뷰와 북마크를 통해 <br />
        추천된 게임입니다🏅
      </div>
    </div>
    <div id="wrap-back-more">
      <more-card-list> </more-card-list>
    </div>
  </div>
</template>

<script>
import MoreCardList from "@/components/card/MoreCardList.vue";
import { useRoute } from "vue-router";
import { ref } from "vue";

export default {
  components: {
    MoreCardList,
  },
  setup() {
    const route = useRoute();
    let presentType = route.params.type;
    let isRecent = ref(false);
    let isReview = ref(false);
    let isTopTen = ref(false);
    let isRecommend = ref(false)

    if (presentType === "평점순") {
      isTopTen.value = true;
      isRecent.value = false;
      isReview.value = false;
      isRecommend.value = false;
    } else if (presentType === "최신게임") {
      isTopTen.value = false;
      isRecent.value = true;
      isReview.value = false;
      isRecommend.value = false;
    } else if (presentType === "리뷰많은순") {
      isTopTen.value = false;
      isRecent.value = false;
      isReview.value = true;
      isRecommend.value = false;
    } else {
      isTopTen.value = false;
      isRecent.value = false;
      isReview.value = false;
      isRecommend.value = true;
    }

    return {
      isRecent,
      isReview,
      isTopTen,
      isRecommend
    };
  },
};
</script>

<style scoped>
#wrap2.back-page-wall {
  background-color: white 
}
.type-comment-bg {
  width: 100vw;
  height: 20vh;
  position: relative;
  background-size: cover;
  text-align: center;
  color: white;
}
.top-ten-bg {
  background-image: url("../static/topten.jpg");
}
.recent-bg {
  background-image: url("../static/recent.jpg");
}
.review-bg {
  background-image: url("../static/review.jpg");
}

#wrap-back-more {
  width: 100vw;
  height: 70vh;
  background-color: white;
  overflow: scroll;
  margin-bottom: 2vh;
}
.type-comment {
  width: 100%;
  height: 12vh;
  padding: 4vh 0 4vh 0;
  background-color: var(--color-bg-modal);
  color: white;
  text-align: center;
  font-size: 20px;
  line-height: 5vh;
}
</style>
