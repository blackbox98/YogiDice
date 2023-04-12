<template>
  <div class="play-header text-headline-6">
    {{ playnowname }} 관련 영상
    <span
      @click="closeYoutube"
      class="close-button-cafe material-icons-outlined"
      >close</span
    >
  </div>
  <br />
  <div v-for="videoId in videoIds" :key="videoId">
    <iframe
      id="ytplayer"
      type="text/html"
      :src="videoId"
      frameborder="0"
    ></iframe>
    <br />
  </div>
</template>

<script>
import { toRefs, computed } from "vue";
import { useStore } from "vuex";

export default {
  props: {
    videoIds: Array,
  },
  emits: ["close-video"],
  setup(props, { emit }) {
    const store = useStore();
    let playnowname = computed(() => {
      if (store.state.gamedetail.playnowname === "게임미선택") {
        return "";
      } else {
        return store.state.gamedetail.playnowname;
      }
    });
    let ids = toRefs(props).videoIds;
    const closeYoutube = function () {
      emit("close-video");
    };

    return {
      playnowname,
      ids,
      closeYoutube,
    };
  },
};
</script>

<style>
#ytplayer {
  width: 80vw;
  height: 25vh;
  margin-bottom: 2vh;
}
.play-header {
  display: flex;
  justify-content: space-between;
}
</style>
