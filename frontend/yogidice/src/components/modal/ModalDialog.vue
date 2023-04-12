<template>
  <div class="modal-back">
    <div class="dialog">
      <modal-content>
        <template #header>
          {{ contents.header }}
        </template>
        <template #body>
          {{ contents.body }}
        </template>
        <template #footer1>
          {{ contents.footer1 }}
        </template>
        <template #footer2>
          {{ contents.footer2 }}
        </template>
      </modal-content>
    </div>
  </div>
</template>

<script>
import { onMounted, computed } from "@vue/runtime-core";
import { useStore } from "vuex";
import ModalContent from "./ModalContent.vue";

export default {
  components: {
    ModalContent,
  },
  setup() {
    const store = useStore();

    let contents = computed(() => store.state.modal.contents);

    onMounted(() => {
      const modalBack = document.querySelector(".modal-back");
      window.addEventListener("click", (e) => {
        if (e.target === modalBack) {
          store.dispatch("modal/closeModal");
        }
      });
    });

    return {
      contents,
    };
  },
};
</script>

<style>
.modal-back {
  position: fixed;
  width: 100vw;
  height: 100vh;
  top: 0px;
  background-color: var(--color-bg-modal);
}
</style>
