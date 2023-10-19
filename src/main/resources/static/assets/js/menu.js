// Function to resize buttons
function resizeBtn() {
    const mealItems = $(".meal__item");
    const heightImg = mealItems.find(".meal__item__img").height();

    const btnWidth = heightImg * 0.17;
    const btnHeight = heightImg * 0.17;
    const btnTop = heightImg / 2 - btnHeight / 2;
    const btnRight = heightImg * 0.085;

    $(".control, .btn-back.control").css({
        "width": btnWidth + "px",
        "height": btnHeight + "px",
        "top": btnTop + "px",
        "right": -btnRight + "px"
    });

    console.log("heightImg: " + heightImg);
}

$(document).ready(function () {
    // Call resizeBtn function on page load and resize
    resizeBtn();
    $(window).on("resize", resizeBtn);

    // Function to handle carousel for given section
    function handleCarousel(sectionId) {
        const mealItems = $(`#${sectionId} .meal__item`);
        const mealSize = mealItems.length;
        let index = 0;
        const mealSizeMinus3 = mealSize - 3;

        function handleButtonClick(action) {
            if (action === "back" && index > 0) {
                index -= 1;
            }

            if (action === "next" && index < mealSizeMinus3) {
                index += 1;
            }

            console.log(action);

            mealItems.css("transform", `translateX(calc(-${index} * 100% - ${30 * index}px))`);
        }

        $(`#${sectionId} .control.btn-back`).on("click", function () {
            handleButtonClick("back");
        });

        $(`#${sectionId} .control:not(.btn-back)`).on("click", function () {
            handleButtonClick("next");
        });
    }

    // Call handleCarousel function for each section
    handleCarousel("Breakfast");
    handleCarousel("Lunch");
    handleCarousel("Dinner");
    handleCarousel("Starters");
});
