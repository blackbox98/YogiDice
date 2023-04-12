<template>
  <div>
    <img
      class="smallImg"
      :class="{ 'checked-game': isChecked }"
      @click="selectImg"
      :src="thumbUrl"
      alt=""
    />
    <p class="text-subtitle-2">{{ titleKr }}</p>
    <span id="checked-mark" v-show="isChecked" class="material-icons"
      >check_circle</span
    >
  </div>
</template>

<script>
import { computed, toRefs, getCurrentInstance, ref } from "vue";
import { useStore } from "vuex";

export default {
  props: {
    game: Object,
  },
  setup(props) {
    let store = useStore();
    const internalInstance = getCurrentInstance();
    const emitter = internalInstance.appContext.config.globalProperties.emitter;

    let presentPage = computed(() => store.state.page.presentPage);
    let selectedGames = computed(() => store.state.games.selectedGames);
    let { titleKr } = toRefs(props.game);
    let { thumbUrl } = toRefs(props.game);
    let { id } = toRefs(props.game);
    let checkedMark = ref(false);
    let isChecked = computed(() => selectedGames.value.includes(id.value));

    // 지금 일단 동시 선택 안됨(선택은 되는데 담기지가 않음)
    const selectImg = function () {
      if (presentPage.value === "선호도조사") {
        // 선택된 상태면 선택 해제
        if (isChecked.value) {
          store.dispatch("games/removeSelectedGames", id.value, { root: true });
        } else {
          // 선택해주기
          store.dispatch("games/addSelectedGames", id.value, { root: true });
        }
        emitter.emit("checkOruncheck");
      } else if (presentPage.value === "플레이게임선택") {
        emitter.emit("choicePlayGame", {
          gameTitle: titleKr.value,
          gameId: id.value,
        });
      }
    };

    return {
      titleKr,
      thumbUrl,
      checkedMark,
      selectImg,
      selectedGames,
      isChecked,
    };
  },
};
</script>

<style scoped>
.smallImg {
  width: 28vw;
  height: 28vw;
  object-fit: cover;
}
.smallImg + p {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  margin-top: 0px;
}
#checked-mark {
  color: white;
  position: absolute;
  top: 8px;
  right: 6px;
  font-size: 8vw;
}
.checked-game {
  filter: brightness(50%);
}
</style>
