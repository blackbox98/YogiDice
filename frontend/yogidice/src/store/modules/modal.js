export default {
  namespaced: true,
  state: () => ({
    showModal: false,
    contents: {
      info: {},
      from: "",
      header: "",
      body: "",
      footer1: "",
      footer2: "",
    },
  }),
  getters: {},
  mutations: {
    OPEN_MODAL(state) {
      state.showModal = true;
    },
    CLOSE_MODAL(state) {
      state.showModal = false;
    },
    REGIST_CONTENTS(state, contents) {
      state.contents = contents;
    },
  },
  actions: {
    openModal({ commit }) {
      commit("OPEN_MODAL");
    },
    closeModal({ commit }) {
      commit("CLOSE_MODAL");
    },
    registModal({ commit }, contents) {
      commit("REGIST_CONTENTS", contents);
    },
  },
};
