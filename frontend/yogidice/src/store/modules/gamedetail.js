import api from "@/api/api";
import router from "@/router";
import axios from "axios";

export default {
  namespaced: true,

  state: () => ({
    detail: {},
    token: localStorage.getItem("token"),
    lengamecategory: [0, 0, 0, 0, 0, 0],
    gamemec: [],
    mecDisc: "",
    mecName: "",
    playnowname: "",
    playnowid: 0,
    playnowhistoryid: 0,
    notting: 0,
    userRating: 0,
    userReview: "",
  }),
  getters: {
    authHeader: (state) => ({
      Authorization: `Bearer ${state.token}`,
      "Content-type": "Application/JSON",
    }),
    playnowid: (state) => state.playnowid,
  },
  mutations: {
    DETAIL_RESET: (state) => (state.detail = {}),
    SET_DETAIL: (state, details) => (state.detail = details),
    LIKE_P_MEC: (state, gid) => (state.lengamecategory[gid] += 1),
    LIKE_P_MEC_RESET: (state) => (state.lengamecategory = [0, 0, 0, 0, 0, 0]),
    GAME_MECHA: (state, gameinfo) => state.gamemec.push(gameinfo),
    GAME_MECHA_RESET: (state) => (state.gamemec = []),

    NOTTING: (state) => (state.notting = 0),

    GAME_MEC_DISC: (state, info) => (state.mecDisc = info),
    GAME_MEC_NAME: (state, info) => (state.mecName = info),

    PLAY_GAME_NAME: (state, game) => (state.playnowname = game),
    PLAY_GAME_ID: (state, game) => (state.playnowid = game),
    PLAY_GAME_HIS_ID: (state, game) => (state.playnowhistoryid = game),

    SET_USER_RATING: (state, rating) => (state.userRating = rating),
    SET_USER_REVIEW: (state, review) => (state.userReview = review),
  },
  actions: {
    getLengames({ commit }, gameId) {
      commit("LIKE_P_MEC_RESET");
      axios({
        url: api.games.detailEdit(gameId),
        method: "get",
      })
        .then((res) => {
          let pmec = [0, 0, 0, 0, 0, 0];
          commit("GAME_MECHA_RESET");
          for (let mecha of res.data.mechanismGroupResponses) {
            commit("GAME_MECHA", [mecha.mechanismName, mecha.parentsMec]);
            if (mecha.parentsMec == "추리카드퍼즐") {
              commit("LIKE_P_MEC", 0);
              pmec[0] += 1;
            } else if (mecha.parentsMec == "경제") {
              commit("LIKE_P_MEC", 1);
              pmec[1] += 1;
            } else if (mecha.parentsMec == "파티") {
              commit("LIKE_P_MEC", 2);
              pmec[2] += 1;
            } else if (mecha.parentsMec == "조건") {
              commit("LIKE_P_MEC", 3);
              pmec[3] += 1;
            } else if (mecha.parentsMec == "말") {
              commit("LIKE_P_MEC", 4);
              pmec[4] += 1;
            } else if (mecha.parentsMec == "전략") {
              commit("LIKE_P_MEC", 5);
              pmec[5] += 1;
            }
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },
    getGameMecDetail({ commit }, mecId) {
      axios({
        url: api.gameInfo.mechanism(mecId),
        method: "get",
      })
        .then((res) => {
          commit("GAME_MEC_DISC", res.data.description);
          commit("GAME_MEC_NAME", res.data.name);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    PlayGame({ commit, getters }, GData) {
      commit("PLAY_GAME_NAME", GData[0]);
      commit("PLAY_GAME_ID", GData[1]);
      axios({
        url: api.users.history(),
        method: "post",
        headers: getters.authHeader,
        data: {
          gameId: GData[1],
        },
      })
        .then((res) => {
          commit("PLAY_GAME_HIS_ID", res.data.id);
        })
        .catch((err) => {
          console.log(err);
        });
    },
    ExitGame() {
      router.push({ name: "home" });
    },
    getDetails({ commit }, gameId) {
      axios({
        url: api.games.detailEdit(gameId),
        method: "get",
      })
        .then((res) => {
          commit("SET_DETAIL", res.data);
        })
        .catch((err) => console.log(err));
    },
    setPlayGame({ commit }, GData) {
      commit("PLAY_GAME_NAME", GData[0]);
      commit("PLAY_GAME_ID", GData[1]);
    },
    getHistory({ getters, commit }, gameId) {
      axios({
        url: api.users.getHistory(gameId),
        method: "get",
        headers: getters.authHeader,
      })
        .then((res) => {
          commit("SET_USER_RATING", res.data.responses[0].rating);
          commit("SET_USER_REVIEW", res.data.responses[0].review);
        })
        .catch((err) => console.log(err));
    },
  },
};
