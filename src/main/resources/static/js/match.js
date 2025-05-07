const itemsPerPage = 7;
const itemsPerSlide = 1;
let currentIndex = 0; // 이동 인덱스

function getStepSize() {
    const allButtons = document.querySelectorAll("#dateButtonList .day");
    if (allButtons.length < 2) return allButtons[0]?.offsetWidth || 0;

    const first = allButtons[0];
    const second = allButtons[1];

    const step = second.getBoundingClientRect().left - first.getBoundingClientRect().left;
    return step;
}

function updateDatePage() {
    const allButtons = document.querySelectorAll("#dateButtonList .day");
    const container = document.querySelector("#dateButtonList");

    const step = getStepSize();
    const offset = currentIndex * step;
    container.style.transform = `translateX(-${offset}px)`;

    const maxIndex = allButtons.length - itemsPerPage;
    document.getElementById("prevBtn").disabled = currentIndex <= 0;
    document.getElementById("nextBtn").disabled = currentIndex >= maxIndex;
}

document.getElementById("prevBtn").addEventListener("click", () => {
    if (currentIndex > 0) {
        currentIndex -= itemsPerSlide;
        updateDatePage();
    }
});

document.getElementById("nextBtn").addEventListener("click", () => {
    const allButtons = document.querySelectorAll("#dateButtonList .day");
    const maxIndex = allButtons.length - itemsPerPage;
    if (currentIndex < maxIndex) {
        currentIndex += itemsPerSlide;
        updateDatePage();
    }
});

window.onload = () => {
    updateDatePage();

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
