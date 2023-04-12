<template>
  <div class="review-box">
    <div class="rating-total">
      <div>
        <div class="text-huge">
          <span v-if="avg">{{ avg.toFixed(2) }}</span
          ><span v-if="!avg">0</span>
        </div>
        <div class="text-caption">{{ cnt }}명 참여</div>
      </div>
      <div class="count-rating">
        <div class="rating-graph">
          <span class="rating">★2</span>
          <span class="review-progress">
            <span class="review-progress-value-2"></span>
          </span>
        </div>
        <div class="rating-graph">
          <span class="rating">★4</span>
          <span class="review-progress">
            <span class="review-progress-value-4"></span>
          </span>
        </div>
        <div class="rating-graph">
          <span class="rating">★6</span>
          <span class="review-progress">
            <span class="review-progress-value-6"></span>
          </span>
        </div>
        <div class="rating-graph">
          <span class="rating">★8</span>
          <span class="review-progress">
            <span class="review-progress-value-8"></span>
          </span>
        </div>
        <div class="rating-graph">
          <span class="rating">★10</span>
          <span class="review-progress">
            <span class="review-progress-value-10"></span>
          </span>
        </div>
      </div>
    </div>

    <!-- 리뷰 컴포넌트 들어갈 곳 -->
    <div class="game-review-bottom-container">
      <div>
        <div class="review-nan-box" v-if="cnt == 0">
          <div>
            <div style="font-size: 30px; font-weight: bold; text-align: center">
              댓글이 없어요
            </div>
            <div style="margin-top: 20px">
              게임을 플레이하고 리뷰를 남겨보세요 ! 😄
            </div>
          </div>
        </div>
        <div v-for="data in datum" :key="data.key">
          <div v-if="data.review" id="review-component-base">
            <div class="review-title-rating-wrapper">
              <div class="review-rating review-title" @click="showDetail(data)">
                {{ data.nickName }}
              </div>
              <div class="rating review-rating">★{{ data.rating }}</div>
              <div
                v-if="isuser"
                class="icon-padding"
                @click="$emit('OpenReviewModal', [data.id, data.gameTitle])"
              >
                <i class="far fa-edit review-rating"></i>
              </div>
            </div>
            <div class="review-create-date">
              {{ data.createDate.slice(0, 10) }}
            </div>
            <div class="review-content-text-overflow">
              {{ data.review }}
            </div>
          </div>
        </div>
        <div style="height: 100px"></div>
      </div>
    </div>
    <!-- 리뷰 컴포넌트 들어갈 곳 -->
  </div>
</template>

<script>
import axios from "axios";
import { onMounted, ref } from "@vue/runtime-core";
// import ReviewComponents from '../card/ReviewComponents.vue';
import { useRoute } from "vue-router";

export default {
  components: {
    // ReviewComponents
  },
  setup() {
    const route = useRoute();
    let gameId = route.query.gameId;
    const datum = ref([]);
    const cnt = ref(0);
    const avg = ref(0);
    const onetwo = ref(0);
    const trfo = ref(0);
    const fisx = ref(0);
    const sveg = ref(0);
    const nitn = ref(0);
    onMounted(() => {
      axios({
        url: `https://yogidice.site/api/games/reviewAll/${gameId}`,
        method: "get",
      })
        .then((res) => {
          datum.value = res.data.responses;
          for (let data of datum.value) {
            if (data.review != null) {
              cnt.value += 1;
              avg.value += data.rating;
            }
            if ((data.rating == 1) | (data.rating == 2)) {
              onetwo.value += 1;
            } else if ((data.rating == 3) | (data.rating == 4)) {
              trfo.value += 1;
            } else if ((data.rating == 5) | (data.rating == 6)) {
              fisx.value += 1;
            } else if ((data.rating == 7) | (data.rating == 8)) {
              sveg.value += 1;
            } else if ((data.rating == 9) | (data.rating == 10)) {
              nitn.value += 1;
            }
          }
          avg.value = avg.value / cnt.value;
          onetwo.value = (onetwo.value / cnt.value) * 100;
          trfo.value = (trfo.value / cnt.value) * 100;
          fisx.value = (fisx.value / cnt.value) * 100;
          sveg.value = (sveg.value / cnt.value) * 100;
          nitn.value = (nitn.value / cnt.value) * 100;
        })
        .catch((err) => {
          console.log(err);
        });
    });

    return {
      datum,
      cnt,
      avg,
      onetwo,
      trfo,
      fisx,
      sveg,
      nitn,
    };
  },
};
</script>

<style>
.review-nan-box {
  width: 80vw;
  height: 50vw;
  background-color: rgba(214, 214, 214, 0.777);
  display: flex;
  justify-content: center;
  align-items: center;
}
.review-box {
  width: 100vw;
}
.rating-total {
  align-items: center;
  display: flex;
  justify-content: space-evenly;
  width: 80vw;
  height: 10vh;
  margin: 10vh 10vw 2vh 10vw;
  box-shadow: 4px 4px 4px rgba(0, 0, 0, 0.25);
  border-radius: 4px;
  background-color: white;
}
.count-rating {
  display: flex;
  flex-direction: column;
  height: 10vh;
  margin-top: 2px;
  gap: 2px;
  margin-top: 12px;
}
.count-rating span {
  font-family: "Pretendard";
  font-style: normal;
  font-weight: 400;
  font-size: 8px;
  text-align: left;
}
.review-progress {
  background: var(--color-grey-3);
  justify-content: flex-start;
  align-items: center;
  position: relative;
  display: flex;
  height: 100%;
  width: 40vw;
  align-self: center;
}
.review-progress-value-2 {
  animation: loading-2 3s normal forwards;
  background: linear-gradient(to right, #ffeeee 0%, #ff6e6e 100%);
  height: 100%;
  width: 100%;
}
.review-progress-value-4 {
  animation: loading-4 3s normal forwards;
  background: linear-gradient(to right, #ffeeee 0%, #ff6e6e 100%);
  height: 100%;
  width: 100%;
}
.review-progress-value-6 {
  animation: loading-6 3s normal forwards;
  background: linear-gradient(to right, #ffeeee 0%, #ff6e6e 100%);
  height: 100%;
  width: 100%;
}
.review-progress-value-8 {
  animation: loading-8 3s normal forwards;
  background: linear-gradient(to right, #ffeeee 0%, #ff6e6e 100%);
  height: 100%;
  width: 100%;
}
.review-progress-value-10 {
  animation: loading-10 3s normal forwards;
  background: linear-gradient(to right, #ffeeee 0%, #ff6e6e 100%);
  height: 100%;
  width: 100%;
}
.rating-graph {
  display: flex;
  gap: 2px;
}
@keyframes loading-2 {
  0% {
    width: 0;
  }
  100% {
    width: calc(v-bind ("onetwo") * 1%);
  }
}
@keyframes loading-4 {
  0% {
    width: 0;
  }
  100% {
    width: calc(v-bind ("trfo") * 1%);
  }
}
@keyframes loading-6 {
  0% {
    width: 0;
  }
  100% {
    width: calc(v-bind ("fisx") * 1%);
  }
}
@keyframes loading-8 {
  0% {
    width: 0;
  }
  100% {
    width: calc(v-bind ("sveg") * 1%);
  }
}
@keyframes loading-10 {
  0% {
    width: 0;
  }
  100% {
    width: calc(v-bind ("nitn") * 1%);
  }
}
.game-review-bottom-container {
  display: flex;
  justify-content: center;
  align-items: center;
  /* height: 43vh; */
  overflow: hidden;
}
</style>
