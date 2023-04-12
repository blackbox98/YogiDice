import axios from "axios";
import router from "@/router";
import api from "@/api/api.js";

export default {
  namespaced: true,

  state: () => ({
    token: localStorage.getItem("token") || "",
    currentUser: {},
    isBookMarkWorking: true,
    myBookMark: [],
  }),
  getters: {
    isLogginedIn: (state) => !!state.token, 
    authHeader: (state) => ({
      Authorization: `Bearer ${state.token}`,
      "Content-type": "Application/JSON",
    }),
  },
  mutations: {
    SET_TOKEN: (state, token) => (state.token = token),
    SET_CURRENT_USER: (state, user) => (state.currentUser = user),
    BOOKMARK_NOT_WORKING: (state) => (state.isBookMarkWorking = false),
    BOOKMARK_WORKING: (state) => (state.isBookMarkWorking = true),
    SET_BOOKMARK: (state, bookmarks) => (state.myBookMark = bookmarks),
  },
  actions: {
    registNickName({ commit, getters }, newNickName) {
      axios({
        url: api.users.regist(),
        method: "put",
        headers: getters.authHeader,
        data: newNickName,
      })
        .then(() => {
          commit("SET_CURRENT_USER", newNickName);
          alert("닉네임이 성공적으로 변경되었습니다!");
          router.push({ name: "InitChoice" }); //(22.10.06 - 튕김이슈발생 : 잠깐 메인으로 바로 가게 함)
          //router.push({ name: "MainPage" });
        })
        .catch(() => alert("닉네임을 변경하지 못했습니다."));
    },
    registBookMark({ getters, commit, dispatch }, gameId) {
      return axios({
        url: api.users.bookmark(),
        method: "post",
        headers: getters.authHeader,
        data: { gameId: gameId },
      })
        .then(() => {
          dispatch("getBookMark");
          dispatch("fetchCurrentUser")
        })

        .catch(() => {
          commit("BOOKMARK_NOT_WORKING");
        });
    },
    deleteBookMark({ dispatch, getters }, gameId) {
      return axios({
        url: api.users.deleteBookMark(gameId),
        method: "delete",
        headers: getters.authHeader,
      })
        .then(() => {
          dispatch("getBookMark");
          dispatch("fetchCurrentUser")
        })

        .catch((err) => {
          console.log(err);
        });
    },
    getBookMark({ getters, commit, dispatch }) {
      return axios({
        url: api.users.bookmark(),
        method: "get",
        headers: getters.authHeader,
      })
        .then((res) => {
          commit("SET_BOOKMARK", res.data.responses);
          dispatch("fetchCurrentUser")

        })

        .catch((err) => {
          if (err.response.status === 400) {
            commit("SET_BOOKMARK", []);
          }
        });
    },
    saveToken({ commit }, token) {
      commit("SET_TOKEN", token);
      localStorage.setItem("token", token);
    },

    async removeToken({ commit }) {
      await commit("SET_TOKEN", "");
      await commit("SET_CURRENT_USER", {});
      localStorage.setItem("token", "");
    },

    fetchCurrentUser({ commit, getters, dispatch }) {
      if (getters.isLogginedIn) {
        axios({
          url: api.users.get(),
          method: "get",
          headers: getters.authHeader,
        })
          .then((res) => {
            commit("SET_CURRENT_USER", res.data);
          })
          .catch((err) => {
            if (err.response.status === 401) {
              dispatch("removeToken");
              router.push({ name: "Login" });
            }
          });
      }
    },

    getKakaoUserInfo({ dispatch }, code) {
      axios
        .get(`${api.users.callback()}?code=${code}`)
        .then((res) => {
          dispatch("checkUser", res.data);
        })
        .catch((err) => {
          console.log(err);
        });
    },

    checkUser({ dispatch }, userInfo) {
      axios({
        url: api.users.check(),
        method: "post",
        data: userInfo,
      })
        .then((res) => {
          if (res.data.registed) {
            dispatch("kakaoLogin", userInfo);
          } else {
            router.push({
              name: "RegistNickName",
              params: {
                nickName: userInfo.nickName,
                kakaoId: userInfo.kakaoId,
              },
            });
          }
        })
        .catch((err) => {
          console.log(err);
        });
    },

    kakaoRegist({ dispatch }, formData) {
      axios({
        url: api.users.regist(),
        method: "post",
        data: formData,
      })
        .then((res) => {
          const token = res.headers["authorization"];
          dispatch("saveToken", token);
          dispatch("fetchCurrentUser");
          dispatch("modal/closeModal", null, { root: true });
          router.push({ name: "InitChoice" }); //(22.10.06 - 튕김이슈발생 : 잠깐 메인으로 바로 가게 함)
          // router.push({ name: "MainPage" });
        })
        .catch((err) => {
          console.log(err);
        });
    },

    kakaoLogin({ dispatch }, userInfo) {
      axios({
        url: api.users.login(),
        method: "post",
        data: userInfo,
      })
        .then((res) => {
          const token = res.headers["authorization"];
          dispatch("saveToken", token);
          dispatch("fetchCurrentUser");
          router.push({ name: "MainPage" });
        })
        .catch((err) => {
          console.log(err);
        });
    },
  },
};
