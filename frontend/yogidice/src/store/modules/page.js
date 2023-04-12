export default {
  namespaced: true,
  state: () => ({
    presentPage: "",
    signUpPage: true,
    nickNamePage: false,
  }),
  mutations: {
    REGIST_PRESENT_PAGE(state, page) {
      state.presentPage = page;
    },
    changeSignUpPage(state) {
      state.signUpPage = !state.signUpPage;
    },
    changeNickNamePage(state) {
      state.nickNamePage = !state.nickNamePage;
    },
  },
  actions: {
    registPresentPage({ commit }, page) {
      commit("REGIST_PRESENT_PAGE", page);
    },
  },
};
