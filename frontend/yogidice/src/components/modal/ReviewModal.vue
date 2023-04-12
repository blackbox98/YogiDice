<template>
  <!-- <div> -->
  <!-- 모달폼 작성하는 곳 -->
  <!-- <div style="height: 20vw"></div> -->
  <div class="review-modal-form" v-if="reviewform">
    <div class="review-modal-form-inner">
      <div class="review-modal-title">
        <span>리뷰</span>
        <span style="margin-right: 20px" @click="$emit('CloseReviewModal')"
          ><i class="fas fa-times"></i
        ></span>
      </div>
      <div class="rmi-displayflex">
        <div class="review-modal-inner">
          <div>
            <div class="rating-comment">
              플레이 하신 "{{ gameTitle }}" 에 대해 리뷰를 남겨주세요!
            </div>
            <div class="stars-rating-wrap">
              <div class="stars-rating">
                ★★★★★
                <span>★★★★★</span>
                <input type="range" v-model="stars" step="1" min="1" max="10" />
              </div>
              <div class="stars-rating-point">{{ stars }}점</div>
            </div>

            <textarea
              class="review-modal-inner-textarea"
              v-model="gamereviewtext"
            ></textarea>
          </div>
        </div>
      </div>
      <div class="rmi-displayflex">
        <div class="review-modal-bottom">
          <div class="review-modal-btn-wrap">
            <div class="review-submit" @click="submitReview">등록</div>
            <div class="review-revoke" @click="$emit('CloseReviewModal')">
              취소
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <!-- 모달폼 작성하는 곳 -->

  <!-- 작성완료 확인모달 -->
  <div class="modal-review-submitted" v-if="!reviewform">
    <p style="font-weight: bold">리뷰가 등록되었습니다 😄</p>
  </div>
  <!-- 작성완료 확인모달 -->
  <!-- </div> -->
</template>

<script>
import { ref, reactive } from "vue";
import { getCurrentInstance } from "vue";
import { useStore } from "vuex";

export default {
  emits: ["CloseReviewModal"],
  props: {
    reviewedInfo: Array,
  },
  setup(props) {
    const { emit } = getCurrentInstance();
    const store = useStore();
    const gameTitle = reactive(props.reviewedInfo[0]);
    const stars = ref(props.reviewedInfo[1]);
    const gamereviewtext = ref(props.reviewedInfo[2]);
    const reviewform = ref(true);
    const submitReview = function () {
      if (gamereviewtext.value == "" || stars.value == 0) {
        window.alert("리뷰를 작성하고 제출해주세요!");
      } else {
        reviewform.value = false;

        store.dispatch("myuser/SendReview", [
          {
            rating: stars.value,
            review: gamereviewtext.value,
          },
          props.reviewedInfo[3],
        ]);
        setTimeout(() => {
          emit("CloseReviewModal");
          reviewform.value = true;
        }, 1500);
      }
    };
    return {
      stars,
      gameTitle,
      gamereviewtext,
      submitReview,
      reviewform,
    };
  },
};
</script>

<style scoped>
.review-modal-title {
  margin: 10px;
  margin-left: 20px;
  margin-top: 20px;
  font-weight: bold;
  font-size: 5vw;
  display: flex;
  justify-content: space-between;
}
.rating-comment {
  margin-top: 15px;
  margin-bottom: 5px;
  font-size: 4vw;
}
.stars-rating-wrap {
  display: flex;
  justify-content: center;
}
.stars-rating-point {
  margin: 10px;
  font-size: 6vw;
  color: rgb(0, 0, 0);
}
.stars-rating {
  position: relative;
  font-size: 8vw;
  color: #ddd;
  margin-bottom: 15px;
}

.stars-rating > input {
  width: 100%;
  height: 100%;
  position: absolute;
  left: 0;
  opacity: 0;
  cursor: pointer;
}

.stars-rating > span {
  width: calc(v-bind ("stars") * 10%);
  /* width:  50%; */
  position: absolute;
  left: 0;
  color: rgb(255, 239, 19);
  overflow: hidden;
  pointer-events: none;
}
.review-modal-form {
  position: relative;
  top: 100px;
  width: 85vw;
  height: 350px;
  background-color: white;
  border-radius: 10px;
}
.review-modal-bottom {
  /* background-color: greenyellow; */
  width: 75vw;
  height: 70px;
  align-items: end;
  padding-top: 30px;
}
.review-modal-inner {
  /* background-color: pink; */
  height: 200px;
  width: 75vw;
  border-top: 1px solid rgb(121, 121, 121);
}
.im-not-play-this-game {
  margin-top: 20px;
  font-size: 3vw;
  text-align: end;
  color: rgb(145, 145, 145);
  text-decoration: underline;
  text-underline-position: under;
}
.rmi-displayflex {
  display: flex;
  justify-content: center;
}
.review-modal-inner-textarea {
  height: 100px;
  width: 74vw;
  border: none;
  border-radius: 5px;
  background-color: rgb(216, 216, 216);
}
.review-modal-inner-textarea:focus {
  outline: solid 2px rgb(199, 199, 199);
}
.review-modal-btn-wrap {
  display: flex;
  justify-content: end;
  margin: 5px;
}
.review-submit {
  margin: 5px;
  width: 80px;
  height: 25px;
  border-radius: 5px;
  background-color: #889eeb;
  color: white;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0px 2px 1px 1px rgba(0, 0, 0, 0.2);
}
.review-revoke {
  margin: 5px;
  width: 80px;
  height: 25px;
  border-radius: 5px;
  background-color: #ffffff;
  outline: solid 1px #c3d0ff83;
  color: #889eeb;
  font-weight: bold;
  display: flex;
  justify-content: center;
  align-items: center;
  box-shadow: 0px 2px 1px 1px rgba(0, 0, 0, 0.2);
}
.modal-review-submitted {
  position: relative;
  top: 200px;
  width: 85vw;
  height: 20vh;
  background-color: white;
  border-radius: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
}
</style>
