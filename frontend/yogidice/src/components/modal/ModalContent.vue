<template>
  <div class="modal-wrap">
    <div class="modal-header">
      <slot name="header"></slot>
    </div>
    <div class="modal-body">
      <slot name="body"></slot>
    </div>
    <div class="modal-footer">
      <button class="button-small-nonbg-blue text-button">
        <slot name="footer1"></slot>
      </button>
      <button class="button-small-nonbg-blue text-button">
        <slot name="footer2"></slot>
      </button>
    </div>
  </div>
</template>

<script>
import { onMounted, computed, watch } from "@vue/runtime-core";
import { useStore } from "vuex";
import { useRouter } from "vue-router";

export default {
  setup() {
    const store = useStore();
    const router = useRouter();

    let isShowModal = computed(() => store.state.modal.showModal);

    let info = computed(() => store.state.modal.contents.info);

    // 이벤트 리스너 중복 방지를 위한 함수 정의
    const registerToInit = function () {
      store.dispatch("user/kakaoRegist", {
        kakaoId: info.value.content.kakaoId,
        nickName: info.value.content.nickName,
      });
    };
    const registBookMark = function (gameId) {
      store.dispatch("user/registBookMark", gameId);
    };
    const closeModal = function () {
      store.dispatch("modal/closeModal");
    };

    onMounted(() => {
      const titleBox = document.querySelector(".modal-header");
      const bodyBox = document.querySelector(".modal-body");
      const firstButton = document.querySelector(
        ".modal-footer button:nth-child(1)",
      );
      const secondButton = document.querySelector(
        ".modal-footer button:nth-child(2)",
      );
      titleBox.classList.add("text-subtitle-1");
      bodyBox.classList.add("text-subtitle-2");
      bodyBox.style.color = "var(--color-grey-5)";

      let registInit = function () {
        {
          // 고른 게임들 확인
          let isBookMarkWorking = computed(
            () => store.state.user.isBookMarkWorking,
          );
          let selectGames = computed(() => store.state.games.selectedGames);
          for (let i = 0; i < selectGames.value.length; i++) {
            // 각 게임을 북마크에 등록
            registBookMark(selectGames.value[i]);
            if (isBookMarkWorking.value === false) {
              break;
            } else {
              continue;
            }
          }
          if (isBookMarkWorking.value === false) {
            store.dispatch(
              "modal/registModal",
              {
                info: {},
                from: "",
                header: "",
                body: "등록에 실패하였습니다!",
                footer1: "",
                footer2: "",
              },
              { root: true },
            );
            store.dispatch("modal/openModal", null, { root: true });
            store.commit("user/BOOKMARK_WORKING");
            setTimeout(function () {
              store.dispatch("modal/closeModal", null, { root: true });
            }, 2000);
          } else {
            store.dispatch(
              "modal/registModal",
              {
                info: {},
                from: "",
                header: "",
                body: "성공적으로 등록되었습니다!",
                footer1: "",
                footer2: "",
              },
              { root: true },
            );
            store.commit("user/BOOKMARK_WORKING");

            setTimeout(function () {
              store.dispatch("modal/closeModal", null, { root: true });
              router.push({
                name: "MainPage",
              });
            }, 2000);
          }
        }
      };

      watch(isShowModal, (newValue) => {
        if (newValue === true && info.value.from === "registNickName") {
          firstButton.addEventListener("click", registerToInit);
          secondButton.addEventListener("click", closeModal);
        }
        // 선호도 조사에서 넘어온 모달인 경우
        else if (newValue === true && info.value.from === "initChoice") {
          firstButton.addEventListener("click", registInit);
          secondButton.addEventListener("click", closeModal);
        } else if (newValue === true && info.value.from === "playGame") {
          if (info.value.content !== "") {
            firstButton.addEventListener("click", () => {
              let playingGameId = computed(
                () => store.state.gamedetail.playnowid,
              );
              if (playingGameId.value != 0) {
                store.dispatch("gamedetail/PlayGame", [
                  info.value.content,
                  playingGameId.value,
                ]);
              }
              store.dispatch("games/resetSelectedGames", { root: true });
              router.push({ name: "GamePlusView" });
              closeModal();
            });
            secondButton.addEventListener("click", closeModal);
          } else {
            firstButton.addEventListener("click", () => {
              store.dispatch("games/resetSelectedGames", { root: true });
              store.commit("gamedetail/PLAY_GAME_NAME", "", { root: true });
              store.commit("gamedetail/PLAY_GAME_ID", "", { root: true });
              router.push({ name: "GamePlusView" });
              closeModal();
            });
            secondButton.addEventListener("click", closeModal);
          }
        } else if (newValue === true && info.value.from === "gamePlusView") {
          firstButton.addEventListener("click", function () {
            closeModal();
            router.push({ name: "MainPage" });
          });
          secondButton.addEventListener("click", closeModal);
        }
      });
    });
  },
};
</script>

<style>
.modal-wrap {
  width: 100%;
}
.modal-header {
  display: flex;
  justify-content: flex-start;
  align-items: flex-end;
  padding: 12px 16px 0px 16px;
  /* Inside auto layout */
}
.modal-body {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  margin: 20px 0px;
  padding: 0px 16px 12px 16px;
}

.modal-footer {
  display: flex;
  justify-content: flex-end;
  padding: 0px;
}
</style>
