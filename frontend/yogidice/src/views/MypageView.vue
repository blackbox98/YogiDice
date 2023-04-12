<template>
  <div class="mypage-container">
    <!-- 헤더있는곳 -->
    <div>
      <grey-bg-head-bar></grey-bg-head-bar>
    </div>
    <!-- 헤더있는곳 -->

    <!-- 추천 도움말 모달 -->
    <div class="my-review-bg" v-if="infomodal">
      <div>
        <div style="height: 150px"></div>
        <div class="info-modal-bg">
          <div style="display: flex; justify-content: end">
            <i
              @click="infomodal = false"
              class="far fa-times-circle"
              style="margin: 10px; font-size: 30px"
            ></i>
          </div>
          <div
            class="info-my-modal-inner"
            style="overflow: auto; flex-wrap: wrap"
          >
            <div style="margin-bottom: 20px">
              요기다이스의 게임 성격 대분류는 총 6가지입니다.
            </div>
            <div>
              <div>
                <div style="font-size: 30px">논리</div>
                <div style="margin-top: 10px; margin-bottom: 15px">
                  작은 단서를 통해 정답을 찾아가는 게임 : 카드나 퍼즐 스타일의
                  게임들이 해당되고, 추리를 통해서 플레이어들의 심리를
                  파악하기도 합니다
                </div>
              </div>
              <div>
                <div style="font-size: 30px">전략</div>
                <div style="margin-top: 10px; margin-bottom: 15px">
                  치밀한 전략을 세워 승리하는 게임 : 게임 전반적으로 나만의 게임
                  공략을 가지고 행동해야 합니다. 논리보다는 조금 더 깊은 전략이
                  필요합니다.
                </div>
              </div>
              <div>
                <div style="font-size: 30px">경제</div>
                <div style="margin-top: 10px; margin-bottom: 15px">
                  현실 경제 지식을 활용하는 게임 : 투자나 매매의 개념이
                  포함되어있는 게임입니다. 승점을 돈으로 사용할 수도 있습니다.
                </div>
              </div>
              <div>
                <div style="font-size: 30px">말</div>
                <div style="margin-top: 10px; margin-bottom: 15px">
                  보드에서 말을 움직이는 게임 : 주사위를 굴리고 말을 이동시키는
                  개념이 포함되어 있습니다.
                </div>
              </div>
              <div>
                <div style="font-size: 30px">파티</div>
                <div style="margin-top: 10px; margin-bottom: 15px">
                  단체 게임에 적합 : 몸을 움직이고 그림을 그리는 등의 액션이
                  포함되어 있습니다. 대개 쉬운 난이도로 되어 있으며 누구든지
                  룰을 금방 익히고 게임을 진행할 수 있습니다.
                </div>
              </div>
              <div>
                <div style="font-size: 30px">룰</div>
                <div style="margin-top: 10px; margin-bottom: 15px">
                  특이한 규칙을 가진 게임 : 보드게임 고유의 룰이 있다면 이
                  매커니즘에 해당됩니다.
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 추천 도움말 모달 -->

    <!-- 리뷰받는 모달폼 -->
    <div class="my-review-bg" v-if="reviewmodalview">
      <review-modal
        @CloseReviewModal="CloseReviewModal"
        :reviewedInfo="reviewedInfo"
      ></review-modal>
    </div>
    <!-- 리뷰받는 모달폼 -->

    <!-- 회원정보 나오는곳 -->
    <div class="profile-container-outer">
      <div class="profile-container">
        <p class="profile-inner" style="font-size: 35px">
          <span @click="myPageBtn('main')">{{ nickName }}</span>
        </p>
        <div style="padding: 3px">
          <img src="../static/kakaologo.png" alt="" width="20" height="20" />{{
            kakaoId
          }}
        </div>
      </div>
    </div>
    <!-- 회원정보 나오는곳 -->

    <!-- 마이페이지 세부페이지 버튼-->
    <div class="mypage-link-btn-outer">
      <div class="mypage-link-btn-wrapper">
        <div
          class="mypage-link-btn-inner border-rad-left"
          @click="myPageBtn('play')"
        >
          <div>
            <div><i class="fas fa-chess"></i></div>
            <div class="displayFlex">
              <p class="mp-btn-p">플레이</p>
              <p
                class="mp-btn-p"
                style="color: var(--color-mint); margin-left: 3px"
              >
                {{ userplaygames.length }}
              </p>
            </div>
          </div>
        </div>
        <div class="mypage-link-btn-inner" @click="myPageBtn('review')">
          <div>
            <div><i class="fas fa-pencil-alt"></i></div>
            <div class="displayFlex">
              <p class="mp-btn-p">리뷰</p>
              <p
                class="mp-btn-p"
                style="color: var(--color-mint); margin-left: 3px"
              >
                {{ userreview }}
              </p>
            </div>
          </div>
        </div>
        <div
          class="mypage-link-btn-inner border-rad-right"
          @click="myPageBtn('bookmark')"
        >
          <div>
            <div><i class="fas fa-bookmark"></i></div>
            <div class="displayFlex">
              <p class="mp-btn-p">북마크</p>
              <p
                class="mp-btn-p"
                style="color: var(--color-mint); margin-left: 3px"
              >
                {{ userbookgames.length }}
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 마이페이지 세부페이지 버튼-->

    <!-- 안내문 보여주는곳 -->
    <div>
      <div class="mypage-boardgame-style">
        <!-- mainview : 보드게임 성향 알려주는곳 -->
        <div v-if="mainview">
          <div class="mp-bg-s-inner">
            <span>{{ nickName }}</span
            ><span>님은 </span>
          </div>
          <div class="mp-bg-s-inner">
            <span id="mypage-cate-result">"{{ toponemec }}"</span
            ><span id="mypage-cate-result">마니아</span><span> 입니다 </span
            ><span @click="infomodal = true"
              ><i class="far fa-question-circle"></i
            ></span>
          </div>
        </div>
        <!-- mainview : 보드게임 성향 알려주는곳 -->

        <!-- play : 플레이한 보드게임 알려주는곳-->
        <div v-if="playview">
          <div class="mp-bg-s-inner">
            <span>내가 플레이한 게임</span
            ><span> ({{ userplaygames.length }})</span>
          </div>
        </div>
        <!-- play : 플레이한 보드게임 알려주는곳-->

        <!-- play : 북마크한 보드게임 알려주는곳-->
        <div v-if="bookmarkview">
          <div class="mp-bg-s-inner">
            <span>내가 북마크한 게임</span
            ><span> ({{ userbookgames.length }})</span>
          </div>
        </div>
        <!-- play : 북마크한 보드게임 알려주는곳-->

        <!-- reviewview : 리뷰한 보드게임 보여주는곳 -->
        <div v-if="reviewview">
          <div class="mp-bg-s-inner">
            <span>내가 남긴 리뷰</span><span> ({{ userreview }})</span>
          </div>
        </div>
        <!-- reviewview : 리뷰한 보드게임 보여주는곳 -->
      </div>
    </div>
    <!-- 안내문 보여주는곳 -->

    <!-- 워드클라우드 / 플레이 / 리뷰 / 북마크 상세 들어갈 곳 -->
    <div class="mypage-bottom-container">
      <div v-if="mainview">
        <!-- <div style="height: 500px;"></div> -->
        <ber-chart :lengamecategory="lengamecategory"></ber-chart>
        <!-- <div style="height: 100px;">
                  {{nickName}} 님이 요즘 하고있는 
              </div> -->
      </div>
      <mypage-play
        :userplaygames="userplaygames"
        v-if="playview"
        class="play-components-overflow"
        @OpenReviewModal="OpenReviewModal"
      ></mypage-play>
      <div>
        <mypage-bookmark v-if="bookmarkview"></mypage-bookmark>
      </div>
      <div>
        <mypage-review
          v-if="reviewview"
          :reviewdatum="reviewdatum"
          :reviewedInfo="reviewedInfo"
          @OpenReviewModal="OpenReviewModal"
        ></mypage-review>
      </div>
      <div v-if="bookmarkview" class="cardlist-my">
        <my-page-bookmark></my-page-bookmark>
      </div>
    </div>
    <!-- 워드클라우드 / 플레이 / 리뷰 / 북마크 상세 들어갈 곳 -->
  </div>
</template>

<script>
import { ref, computed } from "vue";
import { useStore } from "vuex";

import BerChart from "../components/BerChart.vue";
import MypageReview from "../components/MypageReview.vue";
import MypageBookmark from "../components/MypageBookmark.vue";
import MypagePlay from "../components/MypagePlay.vue";
import GreyBgHeadBar from "@/layouts/GreyBgHeadBar.vue";
import ReviewModal from "@/components/modal/ReviewModal.vue";

export default {
  components: {
    MypageReview,
    MypageBookmark,
    MypagePlay,
    GreyBgHeadBar,
    ReviewModal,
    BerChart,
  },

  setup() {
    const store = useStore();

    const mainview = ref(true);
    const playview = ref(false);
    const reviewview = ref(false);
    const bookmarkview = ref(false);
    const reviewmodalview = ref(false);
    const infomodal = ref(false);
    // const ParentMechanism = ['추카퍼', '경제', '파티', '조건', '말', '전략']
    store.dispatch("myuser/GetUserInfo");
    store.dispatch("myuser/GetUserHistory");
    store.dispatch("myuser/GetUserBookmarkmark");

    let nickName = computed(() => store.state.myuser.nickName);
    let kakaoId = computed(() => store.state.myuser.kakaoId);
    let userplaygames = computed(() => store.state.myuser.history);
    let userreview = computed(() => store.state.myuser.userreview);

    // let userreview = ref(userplaygames.value.length)
    let userbookgames = computed(() => store.state.myuser.bookmark);

    // let likeMecha = computed(()=>store.state.myuser.likeMecha)
    let lengamecategory = computed(() => store.state.myuser.likePMec);
    let toponemec = computed(() => store.state.myuser.likePMecMax);

    const myPageBtn = function (option) {
      // eslint-disable-line no-unused-vars
      if (option == "play") {
        playview.value = true;
        mainview.value = false;
        reviewview.value = false;
        bookmarkview.value = false;
      } else if (option == "review") {
        playview.value = false;
        mainview.value = false;
        reviewview.value = true;
        bookmarkview.value = false;
      } else if (option == "bookmark") {
        playview.value = false;
        mainview.value = false;
        reviewview.value = false;
        bookmarkview.value = true;
      } else if (option == "main") {
        playview.value = false;
        mainview.value = true;
        reviewview.value = false;
        bookmarkview.value = false;
      }
    };
    const CloseReviewModal = function () {
      reviewmodalview.value = false;
    };
    let reviewedInfo = ref(0);
    const OpenReviewModal = function (data) {
      reviewmodalview.value = true;
      reviewedInfo.value = data;
    };

    return {
      myPageBtn,
      mainview,
      playview,
      reviewview,
      bookmarkview,
      userplaygames,
      reviewmodalview,
      CloseReviewModal,
      OpenReviewModal,
      nickName,
      userbookgames,
      kakaoId,
      lengamecategory,
      toponemec,
      reviewedInfo,
      userreview,
      infomodal,
    };
  },
};
</script>

<style>
.info-my-modal-inner {
  margin: 20px;
  margin-top: 0px;
  width: 70vw;
  height: 330px;
  /* background-color: pink; */
}
.info-modal-bg {
  width: 80vw;
  height: 400px;
  background-color: whitesmoke;
  border-radius: 20px;
}
.my-review-bg {
  position: absolute;
  width: 100vw;
  height: 100vh;
  background-color: var(--color-bg-modal);
  display: flex;
  justify-content: center;
}
.play-components-overflow {
  overflow: auto;
  height: 50vh;
  width: 95vw;
}
.play-components-overflow::-webkit-scrollbar {
  width: 0px;
}
.mypage-blank {
  height: 70px;
}
.mypage-container {
  height: 100vh;
}
.displayFlex {
  display: flex;
}
.profile-container-outer {
  display: flex;
  justify-content: center;
}
.profile-container {
  text-align: end;
  padding: 20px;
  padding-bottom: 10px;
  width: 100vw;
  /* background-color: pink; */
  height: 15vw;
}
.profile-container > p {
  padding: 0px;
  margin: 0px;
  text-shadow: 3px 3px 5px rgba(0, 0, 0, 0.3);
}
.profile-container > span {
  padding: 3px;
}
.mypage-link-btn-outer {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 150px;
  width: 100%;
  /* background-color: yellow; */
}
.mypage-link-btn-wrapper {
  display: flex;
  justify-content: space-evenly;
  /* background-color: skyblue; */
  background-color: white;
  outline: 1px black solid;
  border-radius: 4px;
  height: 25vw;
  width: 75vw;
  box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2);
}
.mypage-link-btn-inner {
  display: flex;
  justify-content: center;
  align-items: center;
  outline: 0.5px black solid;
  width: 100%;
}
.mypage-link-btn-inner > div {
  text-align: center;
}
.mypage-link-btn-inner > div > div {
  margin-top: 10px;
  margin-bottom: 10px;
}
.border-rad-left {
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
}
.border-rad-right {
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
}
.mp-btn-p {
  margin: 0px;
}

.mypage-boardgame-style {
  display: flex;
  justify-content: center;
  height: 20vw;
  /* background-color: pink; */
  align-items: center;
}
.mypage-boardgame-style > div {
  text-align: center;
}
.mp-bg-s-inner {
  margin: 10px;
}
.mp-bg-s-inner > span {
  font-size: 20px;
}
#mypage-cate-result {
  font-size: 30px;
  font-weight: bolder;
  color: rgb(51, 162, 206);
}

.mypage-bottom-container {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: rgba(255, 255, 255, 0.493);
  height: 50vh;
  overflow: auto;
  /* background-color: brown; */
}
</style>
