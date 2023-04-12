<template>
  <div class="spinner-container">
    <div class="spinner">
      <div class="spinner-inside"></div>
    </div>
  </div>
</template>

<script>
export default {};
</script>

<style scoped>
.spinner-container {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-content: center;
  flex-wrap: nowrap;
  overflow: scroll;
  gap: 2vw;
  width: 100%;
  height: 80vh;
  justify-content: center;
  overflow: scroll;
}
.spinner {
  position: absolute;
  left: 45%;
  top: 40%;
  color: white;
  width: 50px;
  height: 50px;
  text-align: center;
  justify-content: center;
  font-size: 20px;
  border-radius: 50px;
  background: white;
}
.spinner-inside {
  margin-top: 20%;
  margin-left: 20%;
  width: 30px;
  height: 30px;
  background-image: url("@/static/loading30.gif");
}
/* .spinner-container {
  display: inline-block;
  position: absolute;
  width: 64px;
  height: 64px;
  top: 47%;
  left: 47%;
  background-image: url("@/static/loadingspinner50.gif");
  background-repeat: no-repeat;
  position: relative;
} */
</style>
