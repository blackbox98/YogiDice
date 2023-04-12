const HOST = "https://yogidice.site/api";

const user = "/users";
const game = "/games";
const bgg = "/bggdatas";
const cafe = "/cafes";
const cafeGames = "/cafegamelists";
const categories = "/categories";

export default {
  users: {
    get: () => HOST + user,
    login: () => HOST + user + "/login",
    regist: () => HOST + user + "/regist",
    check: () => HOST + user + "/check",
    callback: () => HOST + user + "/callback",
    bookmark: () => HOST + user + "/bookmark",
    deleteBookMark: (gameId) => HOST + user + `/bookmark/${gameId}`,
    history: () => HOST + user + "/history",
    historyControll: (historyId) => HOST + user + "/history" + `/${historyId}`,
    getHistory: (gameId) => HOST + user + "/history" + `/${gameId}`,
    review: () => HOST + user + "/review",
    bookmarkurl: () => HOST + user + "/bookmark",
  },

  games: {
    getCreate: () => HOST + game,
    searchGame: (gameTitle) => HOST + game + "/search" + `/${gameTitle}`,
    detailEdit: (gameId) => HOST + game + `/${gameId}`,
    extendedGames: (gameId) => HOST + game + "/extend" + `/${gameId}`,
    mostReviewed10: () => HOST + game + "/top10/review",
    mostRecent10: () => HOST + game + "/top10/recent",
    mostRating10: () => HOST + game + "/top10/rating",
    sortRating: () => HOST + game + "/rating",
    sortReview: () => HOST + game + "/review",
    sortRecent: () => HOST + game + "/recent",
    filtering: () => HOST + game + "/recommend",
    mainRecommend: () => HOST + game + "/recommend/main",
    detailRecommend: (gameId) =>
      HOST + game + "/recommend/detail" + `/${gameId}`,
  },

  bggDats: {
    getCreate: () => HOST + bgg,
    detailEdit: (bggDataId) => HOST + bgg + `/${bggDataId}`,
  },

  cafes: {
    getCreate: () => HOST + cafe,
    detailEdit: (cafeId) => HOST + cafe + `/${cafeId}`,
    getCafeGames: (address) => HOST + cafeGames + `/cafe/address/${address}`,
  },

  cafeGames: {
    create: () => HOST + cafeGames,
    edit: (gameListId) => HOST + cafeGames + `/${gameListId}`,
    getCafe: (cafeId) => HOST + cafeGames + cafe + `/${cafeId}`,
    getGame: (gameId) => HOST + cafeGames + `/game/${gameId}`,
  },

  gameInfo: {
    mechanism: (mecId) => HOST + categories + `/mechanism/${mecId}`,
    userandgame: (gameId) => HOST + game + `/chemi/${gameId}`,
  },
};
