<template>
  <div>
    <!-- 추천 도움말 모달 -->
    <div
      class="my-review-bg"
      style="position: absolute; z-index: 3"
      v-if="infomodal"
    >
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
          <div class="info-modal-inner-hd">
            {{ mecName }}
          </div>
          <div class="info-modal-inner">
            {{ mecDisc }}
          </div>
        </div>
      </div>
    </div>
    <!-- 추천 도움말 모달 -->

    <detail-card-item></detail-card-item>
    <div class="game-chemi">
      <div class="text-subtitle-1" style="line-height: 30px">
        <span style="font-size: 20px; color: green">{{ nickName }}</span
        >님과 <br />
        <span style="font-size: 18px; color: blue">{{ gameTitle }}</span
        >의 <br />
        케미는 <span style="font-size: 20px; color: red">{{ userAndGame }}</span
        >입니다
      </div>
      <div class="progress">
        <div class="progress-value"></div>
      </div>
    </div>
    <div class="detail-menu-bar">
      <div id="game-introduce" class="text-subtitle-2 isactive">게임소개</div>
      <div id="game-review" class="text-subtitle-2">사용자평가</div>
      <div id="game-related" class="text-subtitle-2">연관게임</div>
    </div>
    <div class="detail-page">
      <game-introduce @OpenmecModal="OpenmecModal"></game-introduce>
      <game-review></game-review>
      <game-related></game-related>
    </div>
    <div id="footer"></div>
  </div>
</template>

<script>
import DetailCardItem from "@/components/card/DetailCardItem.vue";
import { onMounted, computed, ref } from "@vue/runtime-core";
import { useRoute } from "vue-router";
import { useStore } from "vuex";
import GameIntroduce from "@/components/detail/GameIntroduce.vue";
import GameReview from "@/components/detail/GameReview.vue";
import GameRelated from "@/components/detail/GameRelated.vue";
export default {
  components: {
    DetailCardItem,
    GameIntroduce,
    GameReview,
    GameRelated,
  },

  setup() {
    const route = useRoute();
    const store = useStore();

    let detailMenus;
    let gameId = route.query.gameId;
    let gameTitle = route.query.title;
    store.dispatch("games/resetMainGames");
    store.dispatch("games/getDetailRecommend", gameId);

    // 모달로직
    const infomodal = ref(false);

    const mecDisc = ref("");
    const mecName = ref("");
    function OpenmecModal(mecId) {
      infomodal.value = true;
      let detaildatum = computed(() => store.state.games.detail);
      for (let mec of detaildatum.value.mechanismGroupResponses) {
        if (mec.mechanismId == mecId) {
          mecDisc.value = mec.description;
          mecName.value = mec.mechanismName;
        }
      }
    }
    // 모달로직

    // 케미로직
    store.dispatch("myuser/userAndGame", gameId);
    let userAndGame = computed(() => store.state.myuser.chemi);
    // 케미로직

    onMounted(() => {
      detailMenus = document.querySelectorAll(".detail-menu-bar div");
      detailMenus[0].classList.add("isactive")
      detailMenus[1].classList.remove("isactive")
      detailMenus[2].classList.remove("isactive")
      const detailPage = document.querySelector(".detail-page");
      const coverPage = document.querySelector("#wrap2");
      for (let detailMenu of detailMenus) {
        detailMenu.addEventListener("click", (e) => {
          for (let detailMenu of detailMenus) {
            if (detailMenu.getAttribute("id") === e.target.id) {
              detailMenu.classList.add("isactive");
              if (e.target.id === "game-introduce") {
                detailPage.style.transform = "translateX(0vw)";
                coverPage.style.overflowY = "scroll";
              } else if (e.target.id === "game-review") {
                detailPage.style.transform = "translateX(-100vw)";
                coverPage.style.overflowY = "visible";
              } else {
                detailPage.style.transform = "translateX(-200vw)";
                coverPage.style.overflowY = "visible";
                coverPage.style.height = "120vh;";
              }
            } else {
              detailMenu.classList.remove("isactive");
            }
          }
        });
      }
    });

    // 유저 부분
    let nickName = computed(() => store.state.myuser.nickName);
    return {
      gameTitle,
      nickName,
      infomodal,
      OpenmecModal,
      mecDisc,
      mecName,
      userAndGame,
    };
  },
};
</script>

<style>
.info-modal-inner {
  margin: 20px;
  margin-top: 0px;
  width: 70vw;
  height: 270px;
  /* background-color: pink; */
  overflow: auto;
}
.info-modal-inner-hd {
  margin: 20px;
  margin-top: 0px;
  margin-bottom: 10px;
  height: auto;
  width: 70vw;
  font-size: 25px;
  text-shadow: 3px 3px 5px rgba(0, 0, 0, 0.3);
  /* background-color: yellow; */
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
.game-chemi {
  margin: 3vh 4vw 3vh 4vw;
  white-space: wrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
.progress {
  background: var(--color-grey-3);
  justify-content: flex-start;
  border-radius: 100px;
  align-items: center;
  position: relative;
  padding: 0 1vw;
  display: flex;
  height: 3vh;
  width: 90vw;
  margin: 1vh 0vh 1vh 0vh;
}

.progress-value {
  animation: load 3s normal forwards;
  border-radius: 100px;
  background: linear-gradient(to right, #ffeeee 0%, #ff6e6e 100%);
  height: 2vh;
  /* width: 90vw; */
}

@keyframes load {
  0% {
    width: 0;
  }
  100% {
    width: calc(v-bind ("userAndGame") * 1%);
  }
}

.detail-menu-bar {
  display: flex;
  justify-content: space-evenly;
  padding: 0vw 4vw;
  border-bottom: 2px solid var(--color-grey-opacity-9);
}
.detail-menu-bar div {
  width: 28vw;
  height: 2vh;
  padding: 1.5vh 0vh 1.5vh 0vh;
  color: var(--color-grey-opacity-9);
  text-align: center;
}
.detail-menu-bar div.isactive {
  color: black;
  font-weight: bold;
  border-top: 3px solid var(--color-point);
}
.detail-page {
  width: 300vw;
  display: flex;
  flex-wrap: nowrap;
  overflow-x: hidden;
  transition: all 0.5s;
}
</style>
