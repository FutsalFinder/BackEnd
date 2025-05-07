const filterState = {}; // ex: { gender: { all: [...], unchecked: [...] }, platform: { ... } }

// 필터 상태 초기화 (최초 1회)
function initFilterState(modalId) {
    const modal = document.getElementById(modalId);
    const checkboxes = modal.querySelectorAll("input[type=checkbox]");
    const all = Array.from(checkboxes).map(cb => cb.value);
    const unchecked = all.filter(value => !modal.querySelector(`input[value="${value}"]`).checked);

    filterState[modalId] = { all, unchecked };
}

// 체크박스 렌더링 (모달 열기 시)
function renderFilterState(modalId) {
    const state = filterState[modalId];
    const modal = document.getElementById(modalId);
    if (!state) return;

    //unchecked 에 포함되지 않은 값들만 체크한다.
    modal.querySelectorAll("input[type=checkbox]").forEach(cb => {
        cb.checked = !state.unchecked.includes(cb.value);
    });
}

// 선택된 상태 저장 (적용하기 버튼)
function saveFilterState(modalId) {
    const modal = document.getElementById(modalId);
    const unchecked = [];

    modal.querySelectorAll("input[type=checkbox]").forEach(cb => {
        if (!cb.checked) unchecked.push(cb.value);
    });

    filterState[modalId].unchecked = unchecked;
}

document.addEventListener("DOMContentLoaded", () => {
    // 필터 모달 초기화 (모달 id 기준)
    // 모든 .modal-overlay를 자동 탐색하여 초기화
    document.querySelectorAll(".modal-overlay").forEach(modal => {
        initFilterState(modal.id);
    });

    // 모달 열기 버튼
    document.querySelectorAll("[data-modal-target]").forEach(btn => {
        btn.addEventListener("click", () => {
            const selector = btn.getAttribute("data-modal-target");
            const modal = document.querySelector(selector);
            if (modal) {
                renderFilterState(modal.id); // 상태 복원
                modal.classList.add("active");
                //CSS .modal-overlay.active -> 화면에 display 되도록, modal-overlay는 선택시 나타나는 전체 회색배경.
                //이 배경이 active 되면 내부 modal-content도 함께 나타난다.
            }
        });
    });

    // 모달 닫기 버튼
    document.querySelectorAll(".modal-overlay .close-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const modal = btn.closest(".modal-overlay");
            if (modal) {
                //닫기 버튼 클릭시 체크 박스 상태는 저장하지 않는다.
                modal.classList.remove("active");
            }
        });
    });

    // 모달 바깥 클릭 닫기
    document.querySelectorAll(".modal-overlay").forEach(modal => {
        modal.addEventListener("click", (e) => {
            if (e.target === modal) {
                //바깥 클릭으로 닫을시 체크 박스 상태는 저장하지 않는다.
                modal.classList.remove("active");
            }
        });
    });

    // 적용하기 버튼 클릭 → 저장
    document.querySelectorAll(".modal-overlay .apply-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const modal = btn.closest(".modal-overlay");
            if (modal) {
                saveFilterState(modal.id); // 상태 저장
                modal.classList.remove("active");

                renderMatches();
                console.log(`[${modal.id}] 필터 적용됨:`, getActiveValues(modal.id));
            }
        });
    });
})

function applyFilters(matchList) {
    const activeGenders = getActiveValues("genderModal");
    const activePlatforms = getActiveValues("platformModal");
    const hideFull = document.getElementById("hideFullToggle").classList.contains("active");

    if(activeGenders.length === 0 || activePlatforms.length === 0) return [];

    return matchList
        .filter(match => activeGenders.includes(match.sex))
        .filter(match => activePlatforms.includes(match.platform))
        .filter(match => !hideFull || !match.isFull);
}

// 현재 적용된 항목만 가져오는 헬퍼 함수
function getActiveValues(modalId) {
    const state = filterState[modalId];
    if (!state) return [];
    return state.all.filter(value => !state.unchecked.includes(value));
}

document.getElementById("hideFullToggle").addEventListener("click", function () {
    this.classList.toggle("active");

    renderMatches();
});

