<template>
  <div class="map-wrap">
    <div id="map"></div>

    <div id="search-result-wrap">
      <div @click="showSearchList">
        <div class="arrow-icon material-icons-outlined" style="rotate: 90deg">
          arrow_back_ios
        </div>
        <div class="search-list-title text-headline-6">근처 보드게임 카페</div>
      </div>
      <ul id="place-list"></ul>
      <div id="pagination"></div>
    </div>
  </div>
  <cafe-game-list
    v-show="showCafeGameList"
    :name="cafeName"
    @close-cafe-modal="clsoeCafeGameList"
  ></cafe-game-list>
</template>

<script>
import { onMounted, ref } from "vue";
import CafeGameList from "@/components/modal/CafeGameList.vue";
import { useStore } from "vuex";
export default {
  components: {
    CafeGameList,
  },
  setup() {
    const store = useStore();
    let locPosition;
    let cafeName = ref("");
    let showCafeGameList = ref(false);
    store.dispatch("games/resetSmallLenGames")
    let showSearchList = function () {
      let searchList = document.querySelector(".search-list-title");
      let mapContainer = document.querySelector("#map");
      let arrowIcon = document.querySelector(".arrow-icon");
      if (searchList.classList.contains("showing-list")) {
        mapContainer.style.height = "70vh";
        arrowIcon.style.rotate = "90deg";
        searchList.classList.remove("showing-list");
      } else {
        mapContainer.style.height = "35vh";
        arrowIcon.style.rotate = "270deg";
        searchList.classList.add("showing-list");
      }
    };
    const clsoeCafeGameList = function () {
      showCafeGameList.value = false;
    };

    onMounted(() => {
      if (window.kakao && window.kakao.maps) {
        initMap();
      } else {
        const script = document.createElement("script");
        script.src =
          "//dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=98483126b77d6c122713d9878feec1bc&libraries=services";
        /* global kakao */
        script.addEventListener("load", () => {
          kakao.maps.load(initMap);
        });
        document.head.appendChild(script);
      }
    });
    const initMap = function () {
      var markers = [];

      var mapContainer = document.getElementById("map"), // 지도를 표시할 div
        mapOption = {
          center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
          level: 3, // 지도의 확대 레벨
        };
      // 지도를 생성합니다
      var map = new kakao.maps.Map(mapContainer, mapOption);

      if (navigator.geolocation) {
        // GeoLocation을 이용해서 접속 위치를 얻어옵니다
        navigator.geolocation.getCurrentPosition(function (position) {
          let newLat = position.coords.latitude; // 위도
          let newLon = position.coords.longitude; // 경도
          locPosition = new kakao.maps.LatLng(newLat, newLon);
          displayMarker(locPosition, "현재위치");
          map.setCenter(locPosition);
          console.log(locPosition,"센터지정")
          // 키워드로 장소를 검색합니다
          searchPlaces();
        });
      }

      const displayMarker = function (locPosition, message) {
        // 마커 이미지 변경하기
        // const imageSrc = '이미지 주소'
        // // 마커이미지의 주소입니다
        // const imageSize = new kakao.maps.Size(64, 69) // 마커이미지의 크기입니다
        // const imageOption = {offset: new kakao.maps.Point(27, 69)} // 마커이미지의 옵션입니다. 마커의 좌표와 일치시킬 이미지 안에서의 좌표를 설정합니다.

        // // 마커의 이미지정보를 가지고 있는 마커이미지를 생성합니다
        // const markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imageOption)

        // 마커를 생성합니다
        var marker = new kakao.maps.Marker({
          map: map,
          position: locPosition,
          // image: markerImage
        });

        var iwContent = message, // 인포윈도우에 표시할 내용
          iwRemoveable = true;

        // 인포윈도우를 생성합니다
        var infowindow = new kakao.maps.InfoWindow({
          content: iwContent,
          removable: iwRemoveable,
        });

        // 인포윈도우를 마커위에 표시합니다
        infowindow.open(map, marker);
      };

      // 장소 검색 객체를 생성합니다
      var ps = new kakao.maps.services.Places();

      // 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
      var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });



      // 키워드 검색을 요청하는 함수입니다
      function searchPlaces() {
        var keyword = "보드게임 카페";
        let center = map.getCenter();
        console.log(center,"검색기준위치")
        // 장소검색 객체를 통해 키워드로 장소검색을 요청합니다
        ps.keywordSearch(keyword, placesSearchCB, {
          location: center,
          radius:5000,
          sort: kakao.maps.services.SortBy.ACCURACY,
          size: 3,
        });
      }

      // 장소검색이 완료됐을 때 호출되는 콜백함수 입니다
      function placesSearchCB(data, status, pagination) {
        if (status === kakao.maps.services.Status.OK) {
          // 정상적으로 검색이 완료됐으면
          // 검색 목록과 마커를 표출합니다
          displayPlaces(data);

          // 페이지 번호를 표출합니다
          displayPagination(pagination);
        } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
          alert("검색 결과가 존재하지 않습니다.");
          return;
        } else if (status === kakao.maps.services.Status.ERROR) {
          alert("검색 결과 중 오류가 발생했습니다.");
          return;
        }
      }

      // 검색 결과 목록과 마커를 표출하는 함수입니다
      function displayPlaces(places) {
        var listEl = document.getElementById("place-list"),
          menuEl = document.getElementById("search-result-wrap"),
          fragment = document.createDocumentFragment(),
          bounds = new kakao.maps.LatLngBounds();

        // 검색 결과 목록에 추가된 항목들을 제거합니다
        removeAllChildNods(listEl);

        // 지도에 표시되고 있는 마커를 제거합니다
        removeMarker();

        for (var i = 0; i < places.length; i++) {
          // 마커를 생성하고 지도에 표시합니다
          var placePosition = new kakao.maps.LatLng(places[i].y, places[i].x),
            marker = addMarker(placePosition, i),
            itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다

          // 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
          // LatLngBounds 객체에 좌표를 추가합니다
          bounds.extend(placePosition);

          // 마커와 검색결과 항목에 mouseover 했을때
          // 해당 장소에 인포윈도우에 장소명을 표시합니다
          // mouseout 했을 때는 인포윈도우를 닫습니다
          (function (marker, title) {
            kakao.maps.event.addListener(marker, "mouseover", function () {
              displayInfowindow(marker, title);
            });

            kakao.maps.event.addListener(marker, "mouseout", function () {
              infowindow.close();
            });

            itemEl.onmouseover = function () {
              displayInfowindow(marker, title);
            };

            itemEl.onmouseout = function () {
              infowindow.close();
            };
          })(marker, places[i].place_name);

          // fragment.appendChild(document.createElement("hr"))
          fragment.appendChild(itemEl);
        }

        // 검색결과 항목들을 검색결과 목록 Element에 추가합니다
        listEl.appendChild(fragment);
        menuEl.scrollTop = 0;

        // 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
        map.setBounds(bounds);
      }

      // 검색결과 항목을 Element로 반환하는 함수입니다
      function getListItem(index, places) {
        var el = document.createElement("li"),
          itemStr =
            '<div class="li-container"> <div class="info">' +
            '   <div class="place-title text-subtitle-1">' +
            places.place_name +
            "</div>";

        if (places.road_address_name) {
          itemStr +=
            '    <div class="place-address">' +
            places.road_address_name +
            "</div>";
        } else {
          itemStr +=
            '    <div class="place-address">' + places.address_name + "</div>";
        }
        itemStr +=
          '<div class="tel">' + places.phone + "</div></div>" + "</div>";

        let detailBtn = document.createElement("div");
        detailBtn.setAttribute("class", "cafe-detail-btn");
        let tmp1 = document.createElement("span");
        // tmp1.innerText = "카페상세보기"
        tmp1.innerText = "info";
        tmp1.setAttribute(
          "class",
          "material-icons-outlined text-button cafe-detail-link",
        );
        tmp1.addEventListener("click", function () {
          window.open(places.place_url);
        });

        const openCafeGameList = function () {
          cafeName.value = places.place_name;
          showCafeGameList.value = !showCafeGameList.value;
          let address = places.road_address_name
            ? places.road_address_name
            : places.address_name;
          store.dispatch("games/getCafeGames", address);
        };
        let tmp2 = document.createElement("span");
        tmp2.innerText = "format_list_bulleted";
        tmp2.setAttribute(
          "class",
          "material-icons-outlined text-button cafe-own-game",
        );
        tmp2.addEventListener("click", openCafeGameList);

        detailBtn.append(tmp1, tmp2);

        el.innerHTML = itemStr;
        el.append(detailBtn);

        el.className = "item";
        return el;
      }

      // 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
      function addMarker(position, idx) {
        var imageSrc =
            "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
          imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
          imgOptions = {
            spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
            spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
          },
          markerImage = new kakao.maps.MarkerImage(
            imageSrc,
            imageSize,
            imgOptions,
          ),
          marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage,
          });

        marker.setMap(map); // 지도 위에 마커를 표출합니다
        markers.push(marker); // 배열에 생성된 마커를 추가합니다

        return marker;
      }

      // 지도 위에 표시되고 있는 마커를 모두 제거합니다
      function removeMarker() {
        for (var i = 0; i < markers.length; i++) {
          markers[i].setMap(null);
        }
        markers = [];
      }

      // 검색결과 목록 하단에 페이지번호를 표시는 함수입니다
      function displayPagination(pagination) {
        var paginationEl = document.getElementById("pagination"),
          fragment = document.createDocumentFragment(),
          i;

        // 기존에 추가된 페이지번호를 삭제합니다
        while (paginationEl.hasChildNodes()) {
          paginationEl.removeChild(paginationEl.lastChild);
        }

        for (i = 1; i <= pagination.last; i++) {
          var el = document.createElement("a");
          el.href = "#";
          el.innerHTML = i;

          if (i === pagination.current) {
            el.className = "on";
          } else {
            el.className = "off";
            el.onclick = (function (i) {
              return function () {
                pagination.gotoPage(i);
              };
            })(i);
          }
          if (pagination.current - 2 <= i && pagination.current + 2 >= i) {
            fragment.appendChild(el);
          }
        }
        paginationEl.appendChild(fragment);
      }
      // 검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
      // 인포윈도우에 장소명을 표시합니다
      function displayInfowindow(marker, title) {
        var content = '<div style="padding:5px;z-index:1;">' + title + "</div>";

        infowindow.setContent(content);
        infowindow.open(map, marker);
      }

      // 검색결과 목록의 자식 Element를 제거하는 함수입니다
      function removeAllChildNods(el) {
        while (el.hasChildNodes()) {
          el.removeChild(el.lastChild);
        }
      }

    };
    return {
      showSearchList,
      showCafeGameList,
      clsoeCafeGameList,
      cafeName,
    };
  },
};
</script>

<style>
#map {
  width: 100vw;
  height: 70vh;
  position: relative;
  overflow: hidden;
}
#search-result-wrap {
  display: flex;
  flex-direction: column;
  justify-self: flex-start;
  background-color: white;
  border-radius: 20px;
  position: fixed;
  height: 80vh;
  width: 100vw;
}
.search-list-title {
  text-align: left;
  font-weight: bolder;
  padding: 0vh 0vw 2vh 4vw;
  z-index: 10;
}
#place-list {
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  padding: 0vh;
  margin: 0vh;
  overflow: scroll;
  height: 35vh;
}

#wrap2.map-wrap {
  overflow: hidden;
}
.tel {
  width: 40vw;
  text-align: start;
}
#pagination {
  display: flex;
  justify-content: center;
  gap: 2vw;
  margin-top: 1vh;
  align-items: baseline;
}
.place-title {
  text-align: start;
}
a {
  font-size: 16px;
  text-decoration: none;
}
.on {
  text-decoration: underline;
  color: black;
  font-weight: bold;
  font-size: 20px;
}
.arrow-icon {
  rotate: 90deg;
  margin-left: calc(50vw - 24px);
  padding-top: 2vh;
}
hr {
  width: 5vw;
  margin: 0px;
}
.li-container {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-between;
  list-style: none;
  margin: 1vh 2vw;
  gap: 4vw;
  padding: 1vh 4vw;
  height: 10vh;
  width: 70vw;
}
.cafe-detail-btn {
  display: flex;
  justify-content: space-around;
  height: 4vh;
  align-self: center;
  gap: 2vw;
  margin-right: 4vw;
}
.cafe-detail-link {
  color: black;
}
.cafe-own-game {
  color: black;
}
.item {
  display: flex;
  justify-content: space-around;
}
</style>
