<template>
  <div class="introduce-box">
    <div class="subdetail-box">
      <div class="subdetail-title text-headline-6">게임 유형</div>
      <ber-chart :lengamecategory="lengamecategory"> </ber-chart>
    </div>
    <div class="subdetail-box">
      <div class="subdetail-title text-headline-6" style="margin-bottom: 10px">
        게임 테마
      </div>
      <span v-for="data in gametheme" :key="data.key">
        <span class="game-description">{{ data.categoryName }}</span>
      </span>
    </div>
    <div class="subdetail-box">
      <div class="subdetail-title text-headline-6">게임 방식</div>
      <div v-for="data in gamemec" :key="data.key">
        <div class="play-type text-body-2" @click="describeType">
          {{ data.mechanismName
          }}<span
            class="material-icons-outlined"
            style="margin-left: 5px; font-size: 17px"
            @click="$emit('OpenmecModal', data.mechanismId)"
            >info</span
          >
        </div>
      </div>
    </div>
    <div class="subdetail-box">
      <div class="subdetail-title text-headline-6" style="margin-bottom: 15px">
        게임 설명
      </div>
      <div
        style="
          width: 90vw;
          height: 70px;
          display: flex;
          justify-content: center;
          align-items: center;
        "
        v-if="!contents"
      >
        데이터가 없어요 😥
      </div>
      <div style="margin-bottom: 10px">{{ contents }}</div>
      <img :src="contentsImgUrl" alt="" style="width: 90vw" />
    </div>
    <div class="subdetail-box">
      <div class="subdetail-title text-headline-6">관련 영상</div>
      <div
        style="
          width: 90vw;
          height: 70px;
          display: flex;
          justify-content: center;
          align-items: center;
        "
        v-if="!youtubeUrl"
      >
        데이터가 없어요 😥
      </div>
      <iframe
        width="360"
        height="215"
        :src="youtubeUrl"
        frameborder="0"
        allow="accelerometer; autoplay; encrypted-media; gyroscope; picture-in-picture"
        allowfullscreen
      ></iframe>
    </div>
  </div>
  <modal-dialog v-show="showModal" :contents="contents"></modal-dialog>
</template>

<script>
import axios from "axios";
import BerChart from "../BerChart.vue";
import ModalDialog from "@/components/modal/ModalDialog.vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import { computed, onMounted, ref } from "vue";

export default {
  components: {
    BerChart,
    ModalDialog,
  },
  emits: ["OpenmecModal"],
  setup() {
    const route = useRoute();
    const store = useStore();

    const gametheme = ref([]);
    const gamemec = ref([]);

    let gameId = route.query.gameId;
    onMounted(() => {
      axios({
        url: `https://yogidice.site/api/games/${gameId}`,
        method: "get",
      }).then((res) => {
        gamemec.value = res.data.mechanismGroupResponses;
        gametheme.value = res.data.categoryGroupResponses;
      });
    });

    store.dispatch("gamedetail/getLengames", gameId);
    store.dispatch("gamedetail/getDetails", gameId);
    let lengamecategory = computed(
      () => store.state.gamedetail.lengamecategory,
    );
    let contents = computed(() => store.state.gamedetail.detail.contents);
    let youtubeUrl = computed(() => store.state.gamedetail.detail.youtubeUrl);
    let contentsImgUrl = computed(
      () => store.state.gamedetail.detail.contentsImgUrl,
    );

    return {
      // showModal,
      // contents,
      // describeType,
      lengamecategory,
      gametheme,
      gamemec,
      youtubeUrl,
      contents,
      contentsImgUrl,
    };
  },
};
</script>

<style>
.play-type {
  height: 20px;
  font-size: 17px;
  margin-top: 10px;
}
.introduce-box div {
  width: 90vw;
  text-align: left;
}
.subdetail-box {
  background-color: white;
  box-shadow: var(--shadow-card);
  padding: 3vh 5vw;
  margin-bottom: 2vh;
}
.game-description {
  margin-top: 2vh;
  margin-right: 1vh;
}
</style>
