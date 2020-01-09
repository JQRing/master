$(function () {
    var productId = getQueryString('productId');
    var productUrl = '/o2o/frontend/listproductdetailpageinfo?productId=' + productId;

    // 访问后获取该商品的信息并进行渲染
    $.getJSON(productUrl, function (data) {
        if (data.success) {
        	// 获取商品信息
            var product = data.product;
            // 给商品信息相关HTML控件赋值
			// 商品缩略图
            $('#product-img').attr('src', product.imgAddr);
            // 商品更新时间
            $('#product-time').text(
                new Date(product.lastEditTime)
                    .Format("yyyy-MM-dd"));
            if (product.point != undefined) {
				$('#product-point').text('购买可得' + product.point + '积分');
			}
            $('#product-name').text(product.productName);
            $('#product-desc').text(product.productDesc);
            // 商品价格展示逻辑，判断原价现价是否为空，若都为空则不显示价格
			if (product.normalPrice != undefined && product.promotionPrice != undefined) {
				// 如果现价和原价都不为空则展示， 并且给原价加个删除符号
				$("#price").attr("hidden", "false");
				$("#normalPrice").text(product.normalPrice);
				$("#promotionPrice").text(product.promotionPrice);
			}

            var imgListHtml = '';
            product.productImgList.map(function (item, index) {
                imgListHtml += '<div> <img src="'
                    + item.imgAddr + '"/></div>';
            });
            // 生成购买商品的二维码供商家扫描
            imgListHtml += '<div> <img src="/o2o/frontend/generateqrcode4product?productId='
                + product.productId + '"/></div>';
            $('#imgList').html(imgListHtml);
        }
    });
    $('#me').click(function () {
        $.openPanel('#panel-left-demo');
    });
    $.init();
});
