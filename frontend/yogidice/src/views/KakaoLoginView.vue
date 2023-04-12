<template>
  <div>
    <div class="loading" v-show="isLoading">
      <loading-spinner v-show="isLoading"></loading-spinner>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import LoadingSpinner from "@/components/spinner/LoadingSpinner.vue";
import { ref } from "@vue/runtime-core";

export default {
  components: {
    LoadingSpinner,
  },
  setup() {
    const store = useStore();

    let query = window.location.search;
    let param = new URLSearchParams(query);
    let code = param.get("code");
    let isLoading = ref(false);

    const getInfo = () => store.dispatch("user/getKakaoUserInfo", code);
    isLoading.value = true;
    setTimeout(getInfo, 2000);
    // isLoading.value = false;
    return {
      code,
      isLoading,
    };
  },
};
</script>

<style>
.loading {
  position: absolute;
  width: 100%;
  height: 100%;
  background-color: var(--color-bg-modal);
}
</style>
