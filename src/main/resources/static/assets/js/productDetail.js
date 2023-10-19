$(document).ready(function () {
    var quantityNumber =-1
    function checkProduct(id){
     console.log('/api/product/quantity/'+id)
        $.ajax({
            url: '/api/product/quantity/'+id,
            type: 'get',
            success: function (response) {
                quantityNumber= parseInt(response.quantity);
                console.log(response.quantity);
                console.log(quantityNumber);
                addToCart();
            },
            error: function () {
                // Xử lý lỗi (nếu cần)
                alert('An error occurred. Please try again.'); // Hiển thị thông báo lỗi
            }
        });
        return quantity;
    }
    function addToCart() {

        const quantityCart=$("#numberInput").val();
        const  kq = quantityNumber - quantityCart;
        console.log(quantityNumber+"-" +quantityCart);
        console.log("kq="+kq);
        if (kq<0){
            if (quantity==0){
                alert("San pham da het hang");
            } else {
                alert("San pham chi con "+quantity+" sam pham");
            }
            $("#quantity").text(quantity);
        } else {
            var formData = $('#addToCartForm').serialize();
            $.ajax({
                url: '/addcart',
                type: 'POST',
                data: formData,
                success: function (response) {
                    // Xử lý thành công (nếu cần)
                    alert(response); // Hiển thị thông báo từ server
                },
                error: function () {
                    // Xử lý lỗi (nếu cần)
                    alert('An error occurred. Please try again.'); // Hiển thị thông báo lỗi
                }
            });
        }
        // Gửi yêu cầu AJAX

    }

    $("#buttonaddToCart").click(function () {
        var IdProduct = $("#idProduct").val();
        checkProduct(IdProduct);
    });

    // Xử lý sự kiện khi người dùng click vào sao đánh giá
    $(".Evaluate-starts .fa-star").click(function () {
        const id = "#" + $(this).attr("for");
        const index = $(id).val(); // Lấy giá trị của input radio dựa trên id
        console.log(index);
        numberOfCheckedStars = parseInt(index) ; // Chuyển đổi sang số và cập nhật số sao đã chọn
        updateStars();
    });

    function updateStars() {
        $(".Evaluate-starts .fa-star").each(function (index) {
            if (index < numberOfCheckedStars) {
                $(this).addClass("checked");
            } else {
                $(this).removeClass("checked");
            }
        });
    }

    // Xử lý sự kiện khi người dùng thay đổi số lượng sản phẩm
    const maxQuantity = $("#numberInput").data("maxquantity");
    console.log(maxQuantity)

    function updateNumber(action) {
        const numberInput = $("#numberInput");
        let currentValue = parseInt(numberInput.val());

        if (action === "decrease" && currentValue > 1) {
            numberInput.val(currentValue - 1);
        } else if (action === "increase" && currentValue < maxQuantity) {
            // Kiểm tra nếu giá trị tăng vượt quá giá trị max, thì hiển thị giá trị max thay vì tăng tiếp
            const newValue = currentValue + 1;
            numberInput.val(newValue <= maxQuantity ? newValue : maxQuantity);
        }
    }

    // Gán sự kiện cho nút giảm số lượng sản phẩm
    $("#btnMinus").click(function () {
        updateNumber("decrease");
    });

    // Gán sự kiện cho nút tăng số lượng sản phẩm
    $("#btnPlus").click(function () {
        updateNumber("increase");
    });

    $("#numberInput").on("input", function () {
        const numberInput = $(this);
        let currentValue = parseInt(numberInput.val());
        // Nếu giá trị nhập vào vượt quá giá trị max, hiển thị giá trị max
        numberInput.val(currentValue <= maxQuantity ? currentValue : maxQuantity);
    });

    // Xử lý sự kiện khi người dùng chọn tab mô tả sản phẩm
    function showDescriptionTab() {
        event.preventDefault()
        $("#descriptionTab").show();
        $("#reviewsTab").hide();

        // Cập nhật lớp active trong navbar
        $(".navbar__Tab__link.nav-active").removeClass("nav-active");
        $(this).addClass("nav-active");
    }

    // Xử lý sự kiện khi người dùng chọn tab đánh giá sản phẩm
    function showReviewsTab() {
        event.preventDefault()
        $("#descriptionTab").hide();
        $("#reviewsTab").show();

        // Cập nhật lớp active trong navbar
        $(".navbar__Tab__link.nav-active").removeClass("nav-active");
        $(this).addClass("nav-active");
    }

    $("#submitComment").on('click', function(event) {
        event.preventDefault(); // Ngăn chặn hành vi mặc định của nút submit
        console.log("da click");
        // var saveReviewValue = $("#checkbox-session").is(':checked');
        var formData = $(".from-review").serialize();
        // Sử dụng AJAX để gửi dữ liệu đến server
        if (!$("#checkbox-session").is(':checked')) {
            formData += "&saveReview=false"; // Bổ sung giá trị của checkbox vào dữ liệu gửi đi
        }

        $.ajax({
            type: "POST",
            url: "/addReview", // Đặt URL xử lý biểu mẫu ở đây
            data:formData ,
            success: function(response) {
                if (response.hasOwnProperty("Error")) {
                    // Xử lý thông báo lỗi nếu cần
                    alert(response.Error);
                } else {
                    // Xây dựng mã HTML cho danh sách đánh giá từ dữ liệu JSON
                    var reviewListHTML = "";
                    for (var i = 0; i < response.reviews.length; i++) {
                        var review = response.reviews[i];
                        reviewListHTML += `
                        <section class="review__item">
                            <figure class="review__item__thumb">
                                <img src="/assets/imgs/project__deatails/av1.png" alt="" class="review__item__img" />
                            </figure>
                            <div class="review__item__info">
                                <div class="review__item__row">
                                    <span class="review__item__name">${review.name}</span>
                                    <div class="icon">
                                        <svg xmlns="http://www.w3.org/2000/svg" width="10" height="2" viewBox="0 0 10 2" fill="none">
                                            <path d="M1 1H9" stroke="white" stroke-linecap="round" stroke-linejoin="round"/>
                                        </svg>
                                    </div>
                                    <span class="review__item__date">${review.date}</span>
                                </div>
                                <p class="review__item__review section-desc">${review.message}</p>
                                <div class="stars review__item__stars">
                                    ${createStarsHTML(review.review)}
                                </div>
                            </div>
                        </section>
                    `;
                    }

                    // Cập nhật danh sách đánh giá với mã HTML mới
                    $(".review__list").html(reviewListHTML);

                    // Hiển thị thông báo hoặc thực hiện các tác vụ khác
                    alert("sumbit thanh cong")
                }
            },
            error: function(error) {
                // Xử lý lỗi (nếu có)
                alert("loii"+ error.toString())
            }
        });
    });

// Hàm tạo HTML cho số lượng sao dựa trên đánh giá
    function createStarsHTML(reviewCount) {
        var starsHTML = "";
        for (var i = 0; i < reviewCount; i++) {
            starsHTML += '<span class="fa fa-star checked"></span>';
        }
        for (var i = 0; i < (5 - reviewCount); i++) {
            starsHTML += '<span class="fa fa-star"></span>';
        }
        return starsHTML;
    }


    // Gán sự kiện cho tab mô tả và tab đánh giá
    $("#descriptionTabLink").click(showDescriptionTab);
    $("#reviewsTabLink").click(showReviewsTab);
});
