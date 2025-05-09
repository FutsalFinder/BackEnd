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

function scrollToIndex(index) {
    const wrapper = document.querySelector(".date-button-wrapper");
    const step = getStepSize();
    const offset = index * step;

    wrapper.scrollTo({
        left: offset,
        behavior: 'smooth'
    });
}

document.getElementById("prevBtn").addEventListener("click", () => {
    if (currentIndex > 0) {
        currentIndex -= itemsPerSlide;
        scrollToIndex(currentIndex)
    }
});

document.getElementById("nextBtn").addEventListener("click", () => {
    const allButtons = document.querySelectorAll("#dateButtonList .day");
    const maxIndex = allButtons.length - itemsPerPage;
    if (currentIndex < maxIndex) {
        currentIndex += itemsPerSlide;
        scrollToIndex(currentIndex)
    }
});

function resizeDayButtons() {
    const wrapper = document.querySelector(".date-button-wrapper");
    const buttons = document.querySelectorAll("#dateButtonList .day");

    if (!wrapper || buttons.length === 0) return;

    const wrapperWidth = wrapper.offsetWidth;
    const gap = 30; // 버튼 사이 간격 (CSS의 .date-buttons gap과 동일)
    const totalGap = gap * (7 - 1); // 7개의 버튼 사이에 6개의 gap
    const buttonWidth = Math.floor((wrapperWidth - totalGap) / 7);

    buttons.forEach(btn => {
        btn.style.width = `${buttonWidth}px`;
    });
}