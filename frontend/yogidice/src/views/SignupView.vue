<template>
  <div>
    <div>
      <service-introduce />
      <img
        @click="kakaoLogin"
        id="kakao-img"
        src="../static/kakao_login.png"
        alt="Kakao Login"
      />
    </div>
  </div>
</template>

<script>
import ServiceIntroduce from "@/components/intro/ServiceIntroduce.vue";
export default {
  components: {
    ServiceIntroduce,
  },

  setup() {
    const kakaoLogin = function () {
      window.location.replace(
        "https://kauth.kakao.com/oauth/authorize?client_id=" +
          process.env.VUE_APP_KAKAO_REST_API_KEY +
          "&redirect_uri=" +
          process.env.VUE_APP_KAKAO_REDIRECT_URI +
          "&response_type=code",
      );
    };
    return {
      kakaoLogin,
    };
  },
};
</script>

<style scoped>
#kakao-img {
  width: 90vw;
  display: block;
  margin: 5vh auto;
}
</style>
