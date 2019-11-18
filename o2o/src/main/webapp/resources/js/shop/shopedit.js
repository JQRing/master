$(function () {
    var shopId = getQueryString('shopId');
    var isEdit = shopId ? true : false;
    var initUrl = '/o2o/shopadmin/getshopinitinfo';
    var registerShopUrl = '/o2o/shopadmin/registershop';
    var shopInfoUrl = '/o2o/shopadmin/getshopbyid?shopId=' + shopId;
    var editShopUrl = '/o2o/shopadmin/modifyshop';
    if (isEdit) {
        getShopInfo(shopId);
    } else {
        getShopInitInfo();
    }

    function getShopInfo(shopId) {
        $.getJSON(shopInfoUrl, function(data) {
            if (data.success) {
                var shop = data.shop;
                $('#shop-name').val(shop.shopName);
                $('#shop-addr').val(shop.shopAddr);
                $('#shop-phone').val(shop.phone);
                $('#shop-desc').val(shop.shopDesc);
                var shopCategory = '<option data-id="'
                    + shop.shopCategory.shopCategoryId + '" selected>'
                    + shop.shopCategory.shopCategoryName + '</option>';
                var tempAreaHtml = '';
                data.areaList.map(function(item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">'
                        + item.areaName + '</option>';
                });
                $('#shop-category').html(shopCategory);
                $('#shop-category').attr('disabled','disabled');
                $('#area').html(tempAreaHtml);
                $("#area option[data-id = '" + shop.area.areaId + "']").attr('data-id',shop.areaId);
            }
        });
    }

    function getShopInitInfo() {
        $.getJSON(initUrl, function (data) {
            if (data.success) {
                var tempHtml = '';
                var tempAreaHtml = '';

                data.shopCategoryList.map(function (item, index) {
                    tempHtml += '<option data-id="' + item.shopCategoryId + '">' + item.shopCategoryName + '</option>';
                });
                data.areaList.map(function (item, index) {
                    tempAreaHtml += '<option data-id="' + item.areaId + '">' + item.areaName + '</option>';
                });

                $("#shop-category").html(tempHtml);
                $("#area").html(tempAreaHtml);
            }
        });
    }

    $("#submit").click(function () {
        if($('#shop-name').val() == null || $('#shop-name').val() == '') {
            $.toast('请填写商铺名称');
            return false;
        }
        if ($('#shop-addr').val() == null || $('#shop-addr').val() == '') {
            $.toast('请填写商铺地址');
            return;
        }
        if ($('#shop-phone').val() == null || $('#shop-phone').val() == '') {
            $.toast('请填写电话号码');
            return;
        }
        if($('#shop-name').val() == null || $('#shop-name').val() == '') {
            $.toast('请选择商铺图片');
            return false;
        }
        if ($('#j_captcha').val() == null || $('#j_captcha').val() == '') {
            $.toast('请输入验证码');
            return false;
        }
        var shop = {};
        if (isEdit) {
            shop.shopId = shopId;
        }
        shop.shopName = $('#shop-name').val();
        shop.shopAddr = $('#shop-addr').val();
        shop.phone = $('#shop-phone').val();
        shop.shopDesc = $('#shop-desc').val();
        shop.shopCategory = {
            shopCategoryId: $('#shop-category').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        shop.area = {
            areaId: $('#area').find('option').not(function () {
                return !this.selected;
            }).data('id')
        };
        var shopImg = $('#shop-img')[0].files[0];
        var formData = new FormData();
        formData.append('shopImg', shopImg);
        formData.append('shopStr', JSON.stringify(shop));
        var verifyCodeActual = $('#j_captcha').val();
        formData.append('verifyCodeActual', verifyCodeActual);

        $.ajax({
            url: (isEdit?editShopUrl:registerShopUrl),
            type:'POST',
            data: formData,
            contentType: false,
            processData: false,
            cache: false,
            success: function (data) {
                if (data.success) {
                    $.toast('提交成功！');
                } else {
                    $.toast('提交失败！' + data.errMsg);
                }
                $('#captcha_img').click();
            }
        })

    })
});
function check() {
    if($('#shop-name').val() == null || $('#shop-name').val() == '') {
        $.toast('请填写商铺名称');
        return false;
    }
    if($('#shop-name').val() == null || $('#shop-name').val() == '') {
        $.toast('请选择商铺图片');
        return false;
    }
    if ($('#j_captcha').val() == null || $('#j_captcha').val() == '') {
        $.toast('请输入验证码');
        return false;
    }
}