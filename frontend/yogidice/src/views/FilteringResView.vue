<template>
  <div>
    <grey-bg-head-bar></grey-bg-head-bar>
  </div>
  <div class="res-cover">
    <div class="text-headline-6">
      <div>
        <div class="displayflex" style="margin-top: 20px">
          지금 나에게 맞는 게임
        </div>
        <div class="displayflex">
          <div class="again" @click="returnpick">다시하기</div>
        </div>
      </div>
    </div>

    <div class="fail-filtering text-headline-6">
      {{resMsg}}
    </div>
    <main-card-list
      id="res-list"
    ></main-card-list>
  </div>
</template>

<script>
import { computed } from "vue";
import MainCardList from "@/components/card/MainCardList.vue";
import GreyBgHeadBar from "@/layouts/GreyBgHeadBar.vue";
import { useRouter } from "vue-router";
import { useStore } from "vuex";

export default {
  components: {
    MainCardList,
    GreyBgHeadBar,
  },
  setup() {
    const store = useStore();
    let haveRes = computed(()=>store.state.games.filteringResult)
    let resMsg = computed(()=>store.state.games.filteringMessage)


    const router = useRouter();

    const moveToMain = function () {
      router.push({ name: "MainPage" });
    };
    const moveToMypage = function () {
      router.push({ name: "MypageView" });
    };
    const returnpick = function () {
      router.push({ name: "GamePickHome" });
      store.commit("games/RESET_FILTER_RES", { root: true });
    };

    return {
      moveToMain,
      moveToMypage,
      returnpick,
      haveRes,
      resMsg
    };
  },
};
</script>

<style>
.displayflex {
  display: flex;
  justify-content: center;
}
.again {
  margin-top: 20px;
  width: 90px;
  height: 35px;
  border: 3px solid rgb(49, 49, 49);
  color: rgb(49, 49, 49);
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 40px 40px;
}
.res-cover {
  width: 90vw;
  height: 90vh;
  background-color: rgba(255, 255, 255, 0.7);
  padding: 5vh 5vw;
}
.res-cover #res-list {
  display: flex;
  flex-direction: column;
  width: 90vw;
  height: 95vh;
  gap: 2vh;
  overflow: scroll;
  align-items: center;
  margin-top: 2vh;
}
.res-cover #res-list > div {
  margin-bottom: 2vh;
}
.res-cover #res-list > div.card-more {
  visibility: hidden;
}
.fail-filtering {
  display: flex;
  justify-content: center;
  margin-top: 5vh;
}
</style>
