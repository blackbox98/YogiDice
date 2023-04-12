<template>
  <div>
    <div v-show="initMessage" id="choiceMessage" class="text-headline-6">
      여기다이스에서 내 취향에 맞는 <br />
      보드게임을 추천 받으세요!
    </div>
    <div v-show="!initMessage" id="choiceBox">
      <div class="text-headline-6">
        <div>재미있게 플레이했던 게임을 선택해주세요!</div>
      </div>
      <search-bar></search-bar>
      <small-card-list></small-card-list>
      <button
        @click="registInitGame"
        class="button-long-dark init-select-btn text-button"
      >
        아직 플레이해본 게임이 없다면?
      </button>
    </div>
    <modal-dialog v-show="showModal"></modal-dialog>
  </div>
</template>

<script>
import { onMounted, ref, computed, reactive, getCurrentInstance } from "vue";
import { useStore } from "vuex";
import SearchBar from "@/components/SearchBar.vue";
import ModalDialog from "@/components/modal/ModalDialog.vue";
import SmallCardList from "@/components/card/SmallCardList.vue";

export default {
  components: {
    SearchBar,
    SmallCardList,
    ModalDialog,
  },
  setup() {
    const store = useStore();
    let showModal = computed(() => store.state.modal.showModal);
    let contents = reactive({
      info: { from: "initChoice", content: "" },
      header: "",
      body: "",
      footer1: "",
      footer2: "",
    });
    let selectedGames = computed(() => store.state.games.selectedGames);
    let selectCnt = computed(() => selectedGames.value.length);
    let page = 1;

    // 버튼 클릭시
    const registInitGame = function (e) {
      // 아무것도 선택하지 않으면 버튼이 검은색
      if (e.target.className.includes("dark")) {
        contents.body = "게임을 선택하지 않으셨습니다. 계속 진행하시겠습니까?";
        contents.footer1 = "계속";
        contents.footer2 = "취소";
      } else {
        contents.body = `${selectCnt.value}개의 게임을 제출하시겠습니까?`;
        contents.footer1 = "계속";
        contents.footer2 = "취소";
      }
      store.dispatch("modal/registModal", contents);
      store.dispatch("modal/openModal");
    };

    const internalInstance = getCurrentInstance();
    const emitter = internalInstance.appContext.config.globalProperties.emitter;

    let initMessage = ref(true);

    onMounted(() => {
      store.dispatch("games/resetSmallGames", { root: true });
      store.dispatch(
        "games/changeSmallGames",
        { type: "선호도조사", page: page },
        { root: true },
      );
      store.dispatch("page/registPresentPage", "선호도조사", { root: true });
      store.commit("user/BOOKMARK_WORKING");

      // 선택의 개수에 따라 버튼 색깔 바꿔주기
      const initBtn = document.querySelector(".init-select-btn");

      emitter.on("checkOruncheck", () => {
        if (selectCnt.value) {
          initBtn.setAttribute(
            "class",
            "button-long-blue init-select-btn text-button",
          );
          initBtn.innerText = "게임 선택";
          // 여기서 data로 숫자만 받지말고 해당 게임정보 받아서 contents.content에 넣어줘야함
          // 아니면 state에 게임바구니 만들어서 거기에 넣어줘도 됨 -> modal 창에서 받아주고 전송
        } else {
          initBtn.setAttribute(
            "class",
            "button-long-dark init-select-btn text-button",
          );
          initBtn.innerText = "아직 플레이해본 게임이 없다면?";
        }
      });
      // 첫 멘트 나왔다가 사라지기
      setTimeout(function () {
        initMessage.value = !initMessage.value;
        const lastCard = document.querySelector("#last-card-line");
        const io = new IntersectionObserver((entries) => {
          entries.forEach((entry) => {
            if (entry.isIntersecting) {
              page++;
              store.dispatch(
                "games/changeSmallGames",
                { type: "선호도조사", page: page },
                { root: true },
              );
            }
          });
        }); // 관찰자 초기화
        io.observe(lastCard); // 관찰할 대상 등록
      }, 3000);
    });

    return {
      initMessage,
      registInitGame,
      showModal,
      contents,
    };
  },
};
</script>

<style scoped>
#choiceMessage {
  position: relative;
  top: 40vh;
}
#choiceBox {
  display: flex;
  flex-direction: column;
  gap: 36px;
  justify-content: center;
  align-items: center;
  padding: 8vh 3vw;
}
.init-select-btn {
  position: fixed;
  top: 80vh;
}
</style>
