document.addEventListener("DOMContentLoaded", () => {
    // 열기 버튼
    document.querySelectorAll("[data-modal-target]").forEach(btn => {
        btn.addEventListener("click", () => {
            const selector = btn.getAttribute("data-modal-target");
            const modal = document.querySelector(selector);
            modal?.classList.add("active");
        });
    });

    // 닫기 버튼
    document.querySelectorAll(".modal-overlay .close-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const modal = btn.closest(".modal-overlay");
            modal?.classList.remove("active");
        });
    });

    // 바깥 클릭 시 닫기
    document.querySelectorAll(".modal-overlay").forEach(modal => {
        modal.addEventListener("click", (e) => {
            if (e.target === modal) {
                modal.classList.remove("active");
            }
        });
    });

    // 적용 버튼
    document.querySelectorAll(".modal-overlay .apply-btn").forEach(btn => {
        btn.addEventListener("click", () => {
            const modal = btn.closest(".modal-overlay");
            const values = Array.from(modal.querySelectorAll("input[type=checkbox]:checked"))
                .map(cb => cb.value);

            console.log(`[${modal.id}] 선택된 항목:`, values);
            // 여기에 실제 필터 적용 로직 추가 가능
            modal.classList.remove("active");
        });
    });
})

document.getElementById("hideFullToggle").addEventListener("click", function () {
    this.classList.toggle("active");

    const isActive = this.classList.contains("active");
    // 실제 필터링 로직이 있다면 여기서 호출
    // 예: toggleFullMatches(isActive);
});