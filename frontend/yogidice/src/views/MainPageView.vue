<template>
  <div class="search-bar-black-bg">
    <search-bar></search-bar>
  </div>
  <div class="search-res-bg" v-show="showSearchResult">
    <long-card-list></long-card-list>
    <div class="block-search-result"></div>
  </div>
  <div id="wrap2-back">
    <div class="text-headline-6 main-message">
      <span style="color: rgb(51, 162, 206);">{{ nickName }}</span>님에게 맞는 <br />
      보드게임을 추천해드려요
    </div>
    <!-- 추천 게임 타입 선택 -->
    <div class="cardlist-type">
      <div @click="changeActive" class="text-body-1 isactive">추천</div>
      <div @click="changeActive" class="text-body-1">평점순</div>
      <div @click="changeActive" class="text-body-1">최신게임</div>
      <div @click="changeActive" class="text-body-1">리뷰많은순</div>
    </div>
    <!-- 추천 게임 목록 -->
    <main-card-list></main-card-list>
    <!-- 부가기능 버튼 3개 -->
    <div class="text-subtitle-1 main-message">
      요기 다이스의 부가 기능을 살펴보세요!
    </div>
    <div class="add-ons">
      <div class="left-one" @click="moveToPick">
        <div>
          <div class="left-title text-subtitle-1">
            내 취향저격<br />
            게임
          </div>
          <br />
          <div class="left-content text-body-1">
            지금 내가 할 수 있는<br />
            최고의 게임 찾기!
          </div>
        </div>
        <img class="left-img" src="../static/Checklist.png" alt="" />
      </div>
      <div class="right-two">
        <div class="right-top" @click="moveToGamePage">
          <div class="left-title text-subtitle-1">인벤토리</div>
          <img src="../static/Slot_machine.png" alt="" />
        </div>
        <div class="right-bottom" @click="moveToCafes">
          <div class="left-title text-subtitle-1">근처 보드카페 찾기</div>
          <img src="../static/Map.png" alt="" />
        </div>
      </div>
    </div>
    <div id="footer"></div>
  </div>
</template>

<script>
import {
  onMounted,
  getCurrentInstance,
  ref,
  computed,
} from "@vue/runtime-core";
import MainCardList from "@/components/card/MainCardList.vue";
import LongCardList from "@/components/card/LongCardList.vue";
import SearchBar from "@/components/SearchBar.vue";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  components: {
    MainCardList,
    LongCardList,
    SearchBar,
  },
  setup() {
    const store = useStore();
    const router = useRouter();

    store.dispatch("games/resetMainGames");
    store.dispatch("user/getBookMark", { root: true });
    store.dispatch("games/getMainRecommend", { root: true });

    const moveToPick = function () {
      router.push({ name: "GamePickHome" });
    };
    const moveToGamePage = function () {
      router.push({ name: "ChoicePlusGame" });
    };
    const moveToCafes = function () {
      router.push({ name: "PlaceView" });
    };
    // mitt 쓰기
    const internalInstance = getCurrentInstance();
    const emitter = internalInstance.appContext.config.globalProperties.emitter;

    let showSearchResult = ref(false);

    // 데이터 받기 (SearchBar로부터 받음)
    emitter.on("inputValue", (data) => {
      if (data === "") {
        showSearchResult.value = false;
      } else {
        showSearchResult.value = true;
      }
    });

    let cardListTypes;
    onMounted(() => {
      cardListTypes = document.querySelectorAll(".cardlist-type div");
    });

    const changeActive = (e) => {
      for (let cardListType of cardListTypes) {
        if (cardListType.classList.contains("isactive")) {
          cardListType.classList.remove("isactive");
        }
      }
      e.target.classList.add("isactive");
      store.dispatch("games/resetMainGames");
      if (e.target.innerText === "추천") {
        store.dispatch("games/getMainRecommend");
      } else {
        store.dispatch("games/changeMainGames", e.target.innerText);
      }
    };

    // 유저 부분
    store.dispatch("myuser/GetUserInfo");
    let nickName = computed(() => store.state.myuser.nickName);

    return {
      changeActive,
      moveToCafes,
      moveToGamePage,
      nickName,
      showSearchResult,
      moveToPick,
    };
  },
};
</script>

<style>
#footer {
  height: 10vh;
}
#wrap2-back {
  width: 90vw;
  height: 90vh;
  padding: 4vh 5vw 1vh 5vw;
  background-color: var(--color-bg-base);
  overflow: scroll;
}
.main-message {
  text-align: left;
}
.cardlist-type {
  display: flex;
  gap: 2vw;
  margin: 4vh 0vh 2vh 0vh;
}
.cardlist-type .isactive {
  font-weight: bold;
}
.add-ons {
  display: flex;
  justify-content: space-between;
  margin-top: 2vh;
  margin-bottom: 10vh;
  gap: 4vw;
}
.add-ons img {
  width: 100%;
}
.left-one {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  width: 40vw;
  height: 36vh;
  padding: 2vh 0 0 2vh;
  background-color: white;
  border-radius: 10px;
  box-shadow: var(--shadow-card);
  overflow: hidden;
}
.left-title {
  font-family: "Pretendard";
  font-style: normal;
  font-weight: bolder;
  font-size: 16px;
  line-height: 19px;
  text-align: left;
}
.left-content {
  font-family: "Pretendard";
  font-style: normal;
  font-weight: 600;
  font-size: 12px;
  line-height: 12px;
  text-align: left;
  color: var(--color-grey-5);
}
.left-img {
  align-self: flex-end;
}
.right-two {
  display: flex;
  flex-direction: column;
  gap: 4vw;
}
.right-top {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;

  width: 40vw;
  height: 16vh;
  padding: 2vh 0 0 2vh;
  background-color: white;
  border-radius: 10px;
  box-shadow: var(--shadow-card);
  overflow: hidden;
}
.right-bottom {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
  width: 40vw;
  height: 16vh;

  padding: 2vh 0 0 2vh;
  background-color: white;
  border-radius: 10px;
  box-shadow: var(--shadow-card);
  overflow: hidden;
}
.search-bar-black-bg {
  position: sticky;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: black;
  height: 8vh;
  width: 100vw;
}
.search-res-bg {
  position: absolute;
  width: 100vw;
  height: 90vh;
  background-color: var(--color-bg-modal);
  overflow: scroll;
}
.block-search-result {
  width: 100vw;
  height: 10vh;
}
.left-one:active {
  background: gainsboro;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.15),
    inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.right-top:active {
  background: #adb5bd;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.15),
    inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
.right-bottom:active {
  background: #adb5bd;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.15),
    inset 0px 4px 4px rgba(0, 0, 0, 0.25);
}
</style>
