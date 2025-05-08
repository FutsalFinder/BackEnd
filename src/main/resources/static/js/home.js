const baseURL = "https://d3sycde725g6va.cloudfront.net"
let allMatches = [];
let isFetching = false;

function fetchMatches(date) {
    if(isFetching) return;
    isFetching = true;

    document.getElementById("loadingOverlay").style.display = "flex"; // 로딩 화면 표시

    const region = document.getElementById("region").value;

    const url = `${baseURL}/matches/${encodeURIComponent(date)}?region=0`;

    fetch(url)
        .then(response => {
            if (!response.ok) throw new Error("Network response was not ok");
            return response.json();
        })
        .then(data => {
            allMatches = data.map(match => ({
                ...match,
                isFull: parseInt(match.cur_player) >= parseInt(match.max_player) //마감 여부 추가. '마감가리기' 필터용
            }));
            renderMatches();
        })
        .catch(error => {
            console.error("Fetch error:", error);
        })
        .finally(() => {
            isFetching = false;
            document.getElementById("loadingOverlay").style.display = "none"; // 로딩 화면 해제
        });
}

function renderMatches() {
    const container = document.getElementById("match-list");
    container.innerHTML = ""; // 기존 내용 비우기

    // 필터링 적용
    const filtered = applyFilters(allMatches);

    if (filtered.length === 0) {
        container.innerHTML = "<p>조건에 맞는 매치가 없습니다.</p>";
        return;
    }

    filtered.forEach(match => {
        container.appendChild(createMatchElement(match));
    });
}

function createMatchElement(match) {
    const level = match.level ?? "";
    const matchType = match.match_type ?? ""
    const matchVs = match.match_vs ? `${match.match_vs}vs${match.match_vs}` : "";
    const curPlayer = match.cur_player ?? "?"
    const maxPlayer = match.max_player ?? "?"

    const div = document.createElement("a");
    div.className = "match-item";
    div.href = `${match.link}`; // 예시: 이동할 링크
    div.target = "_blank";                     // 새 탭에서 열기
    div.rel = "noopener noreferrer";           // 보안 및 성능을 위한 권장 설정
    div.style.textDecoration = "none";
    div.style.color = "inherit";

    div.innerHTML = `
        <div class="match-left">
            <div class="match-time">${match.time}</div>
            <div class="platform-badge ${match.platform}">${match.platform}</div>
        </div>

        <div class="match-center">
            <div class="match-title">${match.match_title}</div>
            <div class="match-detail">${match.sex}·${level}·${matchType}·${matchVs}</div>
        </div>

        <div class="match-right">
            <div class="match-player-count">${curPlayer} / ${maxPlayer}</div>
        </div>
    `;

    return div;
}

function initializeDateUI() {
    resizeDayButtons();
    scrollToIndex(0);
}

function setupDateSelection() {
    document.querySelectorAll(".day").forEach(btn => {
        btn.addEventListener("click", () => {
            //다른 날짜 요청이 완료되지 않았으면 잠시 대기
            if(isFetching) return;

            // 이미 선택된 날짜면 무시
            if (btn.classList.contains("selected")) return;

            //이전에 선택된 날짜를 지운다.
            document.querySelectorAll(".day").forEach(b => b.classList.remove("selected"));

            //현재 클릭한 날짜를 선택한다.
            btn.classList.add("selected");

            //선택한 날짜로 fetch 한다.
            fetchMatches(btn.dataset.date);
        });
    });
}

function selectFirstDate() {
    const first = document.querySelector(".day");
    if (first) {
        first.click();
    }
}

// 실행 시점
window.addEventListener("DOMContentLoaded", () => {
    initializeDateUI();
    setupDateSelection();
    selectFirstDate();
});

window.addEventListener("resize", initializeDateUI);



