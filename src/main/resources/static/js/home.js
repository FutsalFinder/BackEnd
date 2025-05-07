const baseURL = "d3sycde725g6va.cloudfront.net"
let allMatches = [];
let isFetching = false;

function fetchMatches(date) {
    if(isFetching) return;
    isFetching = true;

    const region = document.getElementById("region").value;

    const url = `/matches/${encodeURIComponent(date)}?region=1`;

    fetch(url)
        .then(response => {
            if (!response.ok) throw new Error("Network response was not ok");
            return response.json();
        })
        .then(data => {
            allMatches = data.map(match => ({
                ...match,
                isFull: parseInt(match.cur_player) >= parseInt(match.max_player)
            }));
            renderMatches();
        })
        .catch(error => {
            console.error("Fetch error:", error);
        })
        .finally(() => {
            isFetching = false;
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
        const div = document.createElement("div");
        div.className = "match-item";
        div.innerText = `${match.time} - ${match.platform} @ ${match.location}`;
        container.appendChild(div);
    });
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



