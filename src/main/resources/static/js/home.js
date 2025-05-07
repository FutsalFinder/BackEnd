function initializeDateUI() {
    resizeDayButtons();
    scrollToIndex(0);
}

function setupDateSelection() {
    document.querySelectorAll(".day").forEach(btn => {
        btn.addEventListener("click", () => {
            document.querySelectorAll(".day").forEach(b => b.classList.remove("selected"));
            btn.classList.add("selected");
            fetchMatches(btn.dataset.date);
        });
    });
}

function selectFirstDateIfNone() {
    const first = document.querySelector(".day");
    if (first) {
        first.classList.add("selected");
        fetchMatches(first.dataset.date);
    }
}

// 실행 시점
window.addEventListener("DOMContentLoaded", () => {
    initializeDateUI();
    setupDateSelection();
    selectFirstDateIfNone();
});

window.addEventListener("resize", initializeDateUI);
