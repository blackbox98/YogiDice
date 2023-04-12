<template>
  <div style="overflow: scroll">
    <div class="plus-backwall">
      <div class="before-card-list">
        <div class="choice-sentence text-headline-6">
          취향 분석 및 부가기능 이용을 위해
          <br />
          플레이 할 게임을 선택해주세요!
        </div>
        <search-bar></search-bar>
      </div>
      <br />
      <div class="card-area">
        <play-game-card-list></play-game-card-list>
      </div>
      <div class="buttons-area">
        <div class="disagree-button" @click="notChoicePlayGame">
          아니요, 선택하지 않고 진행할래요!
        </div>
        <button
          @click="choicePlayGame"
          class="button-long-dark init-select-btn text-button"
        >
          게임을 선택해주세요!
        </button>
      </div>
    </div>
    <modal-dialog v-show="showModal"></modal-dialog>
  </div>
</template>

<script>
import SearchBar from "@/components/SearchBar.vue";
import ModalDialog from "@/components/modal/ModalDialog.vue";
import PlayGameCardList from "@/components/card/PlayGameCardList.vue";
import { useStore } from "vuex";
import { getCurrentInstance, onMounted, reactive, computed } from "vue";
export default {
  components: {
    SearchBar,
    PlayGameCardList,
    ModalDialog,
  },
  setup() {
    const store = useStore();
    store.dispatch("page/registPresentPage", "플레이게임선택", { root: true });
    let choiceGameTitle = "";
    let choiceGameID = 0;
    let showModal = computed(() => store.state.modal.showModal);
    let contents = reactive({
      info: { from: "playGame", content: "" },
      header: "",
      body: "",
      footer1: "",
      footer2: "",
    });
    // 버튼 클릭시
    const choicePlayGame = function (e) {
      // 아무것도 선택하지 않으면 버튼이 검은색
      if (!e.target.className.includes("dark")) {
        contents.info.content = choiceGameTitle;
        contents.header = "";
        contents.body = `${choiceGameTitle}를 플레이 하시겠습니까?`;
        contents.footer1 = "시작";
        contents.footer2 = "취소";
        store.dispatch("gamedetail/setPlayGame", [
          choiceGameTitle,
          choiceGameID,
        ]);
        store.dispatch("modal/registModal", contents);
        store.dispatch("modal/openModal");
      } else {
        notChoicePlayGame();
      }
    };
    const notChoicePlayGame = function () {
      // 아무것도 선택하지 않으면 버튼이 검은색
      contents.info.content = "게임미선택";
      contents.header = "게임 선택 없이 진행하기";
      contents.body =
        "게임을 선택하지 않을 경우, 게임별로 제공되는 정보를 이용할 수 없고, 취향 분석의 정확도가 낮아질 수 있습니다. 그래도 게임 선택 없이 진행하시겠습니까?";
      contents.footer1 = "계속";
      contents.footer2 = "취소";
      store.dispatch("gamedetail/setPlayGame", [contents.info.content, 0]);
      store.dispatch("modal/registModal", contents);
      store.dispatch("modal/openModal");
    };

    const internalInstance = getCurrentInstance();
    const emitter = internalInstance.appContext.config.globalProperties.emitter;
    onMounted(() => {
      store.dispatch("games/resetSelectedGames", { root: true });
      const playGameBtn = document.querySelector(".buttons-area button");

      emitter.on("choicePlayGame", function (data) {
        playGameBtn.setAttribute(
          "class",
          "button-long-blue init-select-btn text-button",
        );
        playGameBtn.innerText = "게임 선택";
        choiceGameTitle = data.gameTitle;
        choiceGameID = data.gameId;
      });
    });
    return {
      choicePlayGame,
      notChoicePlayGame,
      showModal,
    };
  },
};
</script>

<style scoped>
.plus-backwall {
  height: 100vh;
  padding: 5vh 3vw;
}
.choice-sentence {
  margin-bottom: 2vh;
}
.before-card-list {
  padding: 2vh 2vw;
}
.buttons-area {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 1vh;
}
.disagree-button {
  text-decoration-line: underline;
  font-size: 3vw;
  color: var(--color-grey-5);
}
.card-area {
  height: 60vh;
  margin-bottom: 2vh;
  overflow-y: scroll;
  overflow-x: hidden;
}
</style>
