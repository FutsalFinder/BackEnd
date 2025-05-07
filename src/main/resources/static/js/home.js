window.onload = () => {
    updateDatePage();
    resizeDayButtons();

    document.querySelectorAll(".day").forEach(btn => {
        btn.addEventListener("click", () => {
            document.querySelectorAll(".day").forEach(b => b.classList.remove("selected"));
            btn.classList.add("selected");
            fetchMatches(btn.dataset.date);
        });
    });

    // 최초 선택
    const first = document.querySelector(".day");
    if (first) {
        first.classList.add("selected");
        fetchMatches(first.dataset.date);
    }
};

window.addEventListener("resize", () => {
    resizeDayButtons();
    updateDatePage(); // 이동 위치도 다시 맞추기
});

window.addEventListener("DOMContentLoaded", () => {
    resizeDayButtons();
    updateDatePage();
});


