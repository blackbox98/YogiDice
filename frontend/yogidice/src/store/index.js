import { createStore } from "vuex";
import modal from "@/store/modules/modal.js";
import page from "@/store/modules/page.js";
import user from "@/store/modules/user.js";
import games from "@/store/modules/games.js";
import myuser from "@/store/modules/myuser.js";
import gamedetail from "@/store/modules/gamedetail.js";
import createPersistedState from "vuex-persistedstate";

export default createStore({
  modules: {
    modal,
    page,
    user,
    games,
    myuser,
    gamedetail,
  },
  plugins: [
    createPersistedState({
      paths: ["modal", "page", "user", "games"],
    }),
  ],
});
