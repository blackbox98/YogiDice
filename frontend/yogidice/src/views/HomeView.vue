<template>
  <div>
    <div id="cover">
      <img id="logo" src="../static/logo_black.png" alt="" />
    </div>
  </div>
</template>

<script>
import { onMounted } from "vue";
import { useRouter } from "vue-router";

export default {
  setup() {
    const router = useRouter();

    onMounted(() => {
      setTimeout(function () {
        router.push({
          name: "SignupView",
        });
      }, 2000);
    });
  },
};
</script>

<style scoped>
#cover {
  width: 100vw;
  height: 100vh;
  background: url("@/static/lending_page.png") no-repeat fixed;
  background-size: 100% 100%;
  position: fixed;
}

#logo {
  width: 60%;
  position: absolute;
  left: 20%;
  top: 24%;
}
</style>
