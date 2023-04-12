<template>
  <div>
    <div id="register-nickname">
      <label for="nickname-input" class="text-subtitle-2"
        >사용할 닉네임을 입력하세요.</label
      >
      <input id="nickname-input" v-model="userNickName" type="text" />
      <div>
        <button @click="registNickname" class="button-small-blue text-button">
          계속
        </button>
      </div>
    </div>
    <modal-dialog v-show="showModal"></modal-dialog>
  </div>
</template>

<script>
import { reactive, computed, ref } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import ModalDialog from "@/components/modal/ModalDialog.vue";

export default {
  components: {
    ModalDialog,
  },

  setup() {
    const route = useRoute();
    const store = useStore();

    let userNickName = ref(route.params.nickName);
    const userId = route.params.kakaoId;

    let showModal = computed(() => store.state.modal.showModal);
    let contents = reactive({
      info: {
        from: "registNickName",
        content: { nickName: userNickName.value, kakaoId: userId },
      },
      header: "",
      body: "",
      footer1: "",
      footer2: "",
    });
    const registNickname = function () {
      contents.header = "닉네임 등록";
      contents.body = `${userNickName.value} 으로 등록하시겠습니까?`;
      contents.footer1 = "계속";
      contents.footer2 = "취소";
      contents.info.content = { nickName: userNickName.value, kakaoId: userId };
      store.dispatch("modal/registModal", contents);
      store.dispatch("modal/openModal");
    };
    return {
      showModal,
      // nickNameValue,
      registNickname,
      contents,
      userNickName,
    };
  },
};
</script>

<style>
#register-nickname {
  display: flex;
  flex-direction: column;
  gap: 10px;
  position: absolute;
  top: 30vh;
  margin: 0 15vw;
}
#nickname-input {
  box-sizing: border-box;
  width: 70vw;
  height: 40px;
  border: 1px solid #000000;
  border-radius: 4px;
  padding: 5px 10px;
}
#register-nickname > div {
  display: flex;
  justify-content: flex-end;
}
</style>
