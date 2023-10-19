var activeSelect = null;
$(document).ready(function () {


    function updateNumber(action, container) {
        const numberInput = container.find(".form-number-input");
        const maxValue = parseInt(numberInput.attr("data-maxValue"));
        console.log(maxValue)
        let currentValue = parseInt(numberInput.val());

        const priceElement = container.find(".Price");
        const price = parseFloat(priceElement.attr("data-Price"));

        const subtotalElement = container.find(".subtotal");

        if (action === "decrease" && currentValue > 1) {
            numberInput.val(currentValue - 1);
        } else if (action === "increase" && currentValue < maxValue) {
            numberInput.val(currentValue + 1);
        }

        const updatedValue = parseInt(numberInput.val());

        const subtotal = (updatedValue * price).toFixed(2);
        subtotalElement.text("$" + subtotal);
        subtotalElement.attr("data-subtotal", subtotal);
        // updateTotal(); // Update the total whenever the quantity changes
    }

    function updateTotal() {
        let total = 0;
        $(".subtotal").each(function () {
            const subtotal = parseFloat($(this).attr("data-subtotal"));
            total += subtotal;
        });
        $("#cart-product__totals").text("$" + total.toFixed(2));
        $("#cart-product__totals").attr("data-totals",total);
        const voucher = $("#voucherInfo").text()
        if (voucher!="" ){
            const number = voucher.match(/[0-9]/)[0];

            if (voucher.includes("%")){
                total = total - ((total*number*10)/100);
            } else
                total = total - number;
            $("#totals").text("$" + total.toFixed(2));

        } else
            $("#totals").text("$" + total.toFixed(2));




    }

    $(".btn").on("click", function () {
        const btnId = $(this).attr("id");
        const container = $(this).closest(".cart-product__item");
        if (btnId === "btnMinus") {
            updateNumber("decrease", container);
        } else if (btnId === "btnPlus") {
            updateNumber("increase", container);
        }
    });

    $(".form-number-input").on("change", function () {
        const currentValue = parseInt($(this).val());
        const maxValue = parseInt($(this).attr("data-maxValue"));
        if (isNaN(currentValue) || currentValue < 1) {
            $(this).val(1);
        } else if (currentValue > maxValue) {
            $(this).val(maxValue);
        }

        const container = $(this).closest(".cart-product__item");
        updateNumber("change", container);
    });

    updateTotal();

    $(".delete-cart-item").on("click", function () {
        event.preventDefault(); // Ngăn chặn việc tải lại trang sau khi nhấn liên kết xóa

        const id = $(this).data("id");
        const container = $(this).closest(".cart-product__item");

        $.ajax({
            type: "POST",
            url: "/deletecart/" + id,
            success: function (response) {
                // Xử lý thành công (nếu cần)
                container.remove(); // Xóa sản phẩm khỏi giao diện
                updateTotal();
                // Cập nhật giao diện sau khi xóa sản phẩm thành công
                alert(response); // Hiển thị thông báo từ server

            },
            error: function () {
                // Xử lý lỗi (nếu cần)
                alert('An error occurred. Please try again.'); // Hiển thị thông báo lỗi
            }
        });
    });
    $("#updateCart").on("click", function () {
        // Lấy danh sách ID sản phẩm và số lượng tương ứng từ giao diện
        var productIds = [];
        var quantities = [];

        $(".cart-product__item").each(function () {
            var productId = $(this).find(".delete-cart-item").data("id");
            var quantity = $(this).find(".form-number-input").val();
            productIds.push(productId);
            quantities.push(quantity);
        });

        // Gửi yêu cầu POST đến endpoint "updatecart" với dữ liệu danh sách ID và số lượng
        $.ajax({
            url: "/updatecart",
            type: "POST",
            data: {
                productIds: productIds,
                quantities: quantities
            },
            traditional: true,
            success: function (data) {
                updateTotal();
                alert(data); // Hiển thị thông báo thành công

            },
            error: function (xhr, status, error) {
                // Xử lý lỗi nếu có
                console.error("Error:", error);
                alert("An error occurred while updating the cart.");
            }
        });
    });

    $("#applyCoupon").click(function () {
        // Lấy giá trị từ ô input có tên là "code"
        var couponCode = $("#couponCode").val();
        var voucherSectionElement = $('#voucherSection');
        // Gửi yêu cầu Ajax đến máy chủ
        const count = $(".cart-product__item").length;
        if (count==0 ){
            alert("Vui long them san pham de applyCoupon");
        } else {
            $.ajax({
                type: "get",
                url: "/checkcode",
                data: {code: couponCode},
                success: function (response) {
                    if (response.error) {
                        // Xử lý lỗi
                        voucherSectionElement.css('display', 'none');
                        $("totals").text("");

                        alert("Error applying coupon: " + response.message);

                    } else {
                        // Xử lý thành công
                        alert("Coupon applied successfully!");

                        // Kiểm tra nếu có voucher trong response
                        var voucherValue = response.voucher;
                        if (voucherValue != null && voucherValue !== "") {
                            // Hiển thị thông tin voucher
                            var voucherInfoElement = $('#voucherInfo');
                            voucherInfoElement.text('-' + voucherValue.value + ' ' + voucherValue.unit)  ;

                            // Hiển thị phần tử chứa voucher
                            $("#idVoucher").val(voucherValue.id);
                            voucherSectionElement.css('display', 'flex');

                            // Cập nhật giá trị tổng đơn hàng
                            var totalsElement = $("#cart-product__totals");
                            var totals = parseFloat(totalsElement.attr("data-totals"));
                            console.log("totals"+totals);
                            var value = parseFloat(voucherValue.value);
                            console.log(totals + "  " + value)
                            if (voucherValue.unit == "$") {
                                $("#totals").text('$ ' + (totals - value).toFixed(2));
                            } else {
                                $("#totals").text('$ ' + (totals - (value * totals / 100)).toFixed(2));
                            }
                        }
                    }
                },
                error: function (e) {
                    console.log("Error applying coupon: ");
                    alert("Error applying coupon. Please try again later.");
                }
            });

        }
    });

    function toggleDropdown(id,value) {
        var customSelect = $("#" + id)
        console.log(customSelect);
        if (id=="province")
        getAddress(id, "all")
        if (activeSelect && activeSelect !== customSelect) {
            activeSelect.removeClass("open");
            activeSelect=null;
        }
        customSelect.toggleClass("open");
        activeSelect = customSelect;
    }
    function getAddress(category, id) {
        const url = '/api/' + category + '/' + id;
        console.log(url);
        $.ajax({
            url: url,
            method: 'GET',
            dataType: 'json',
            success: function (data) {
                populateAddress(category, data);
            },
            error: function (error) {
                console.log('Error fetching menu items by category:', error);
            }
        });
    }

    function populateAddress(id, data) {
        const id_select = '#' + id;
        const customSelect = $(id_select);
        var text = customSelect.find(".selected-option").text();

        // Tạo chuỗi HTML chứa cả span và ul
        var htmlString = `<span class="selected-option section-desc" style="text-transform: capitalize">${id}</span><ul class="options section-desc">`;
        if(id=='province'){
            $("#ward .selected-option").text("Ward");
        }
        // Thêm các phần tử li vào chuỗi HTML
        $.each(data, function (index, item) {
            htmlString += `<li class="section-desc" data-value="${item.id}">${item.name}</li>`;
        });

        // Đóng thẻ ul trong chuỗi HTML
        htmlString += '</ul>';

        // Thêm chuỗi HTML vào customSelect
        customSelect.html(htmlString);

        // Thêm input hidden sau ul
        customSelect.append('<input type="hidden" name="' + id + '" />');


        console.log(customSelect);
    }



    $(".custom-select").on('click', function () {
        var id = $(this).attr("id");
        console.log(id);
        toggleDropdown(id);
        check = false;
    });
    var shippingString = "";
    $(".custom-select").on("click", "li", function (event) {
        // Ngăn chặn sự kiện click của li lan ra và không tác động đến sự kiện click của .custom-select
        event.stopPropagation();
        var value = $(this).attr("data-value");
        var text = $(this).text();
        var parentCustomSelect = $(this).closest(".custom-select");
        var id = parentCustomSelect.attr("id");

        var shippingStringElement = $(".Shipping-string");

        if (shippingStringElement.length) {
            shippingString = shippingStringElement.text();
            console.log("Shipping String:", shippingString);
        } else {
            console.log("Shipping String element not found!");
        }
        console.log(id);
        shippingString = shippingStringElement.text();

        console.log(shippingString);

        if(id=="province"){
            shippingString ="";
            getAddress("city",value);

        }
        if (id == "city"){
           getAddress("ward",value);
        }
        if (id == 'province'){
            shippingString += text;

        } else {
            shippingString +=", "+ text ;
        }
        console.log(shippingString);
        shippingStringElement.text(shippingString);
        parentCustomSelect.find(".selected-option").text(text);
        parentCustomSelect.find("input[name='select-option']").val(value);
        parentCustomSelect.removeClass("open");
        if (id=='ward'){
            $("#specificAddress").prop('readonly',false);
        }
    });




    $(document).on("click", function (event) {
        if (
            !$(event.target).closest(".custom-select").length &&
            activeSelect !== null
        ) {
            activeSelect.removeClass("open");
            activeSelect = null;
        }
    });

    $("#updateAddress").on("click", function (event) {
        event.preventDefault()
        // Lấy giá trị từ các trường trong biểu mẫu
        var specificAddress = $("#specificAddress").val();
        var address = $(".Shipping-string").text();
        console
            .log(specificAddress);
        if(specificAddress==""){
            alert("Vui long nhap het du lieu");
        } else{
            $.ajax({
                url: "/update-address", // Đường dẫn đến API của ứng dụng Spring Boot
                type: "POST",
                data: {
                    address: address
                },
                success: function (response) {
                    // Xử lý phản hồi từ server sau khi gửi dữ liệu thành công (nếu cần)
                    console.log("Update address successfully!!!");
                    alert("Update address successfully!!!");
                },
                error: function (error) {
                    // Xử lý lỗi (nếu có) khi gửi dữ liệu không thành công
                    console.error("Gửi dữ liệu thất bại:", error);
                },
            });

        }
        // Gửi dữ liệu đến API bằng Ajax
    });
    $("#specificAddress").on('input',function (){
        const  text = $(this).val();
        $(".Shipping-string").text(shippingString+", "+text);
    })

    $("#checkoutButton").on("click", function() {
        const voucher = $("#voucherInfo").text();
        const Subtotal = $("#cart-product__totals").text();
        const totals = $("#totals").text();

        // Gán giá trị cho các trường ẩn
        $("#textVoucher").val(voucher);
        $("#textSubtotal").val(Subtotal);
        $("#textTotals").val(totals);

        const count = $(".cart-product__item").length;
        if(count>0)
            $("#form-checkout").submit();
        else{
            alert("Vui long them san pham");
        }
    });

});

