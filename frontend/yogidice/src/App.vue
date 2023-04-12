<template>
  <router-view :key="route.fullPath" />
</template>

<script>
import { useRoute } from "vue-router";
export default {
  name: "App",
  components: {},
  setup() {
    const route = useRoute();

    return {
      route,
    };
  },
};
</script>

<style>
#app {
  width: 100vw;
  height: 100vh;
  background: url("static/lending_page.png") no-repeat fixed;
  background-size: 100% 100%;
  position: fixed;
  /* overflow: scroll; */
}
#main-card:active {
  background: #adb5bd;
  box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.15),
    inset 0px 4px 4px rgba(0, 0, 0, 0.25);
  filter: brightness(0.5);
}
</style>
