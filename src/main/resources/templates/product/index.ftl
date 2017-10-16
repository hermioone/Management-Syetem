<html>
<#include "../common/header.ftl">

<body>
<div id="wrapper" class="toggled">

<#--边栏sidebar-->
<#include "../common/nav.ftl">

<#--主要内容content-->
    <div id="page-content-wrapper">
        <div class="container-fluid">
            <div class="row clearfix">
                <div class="col-md-12 column">
                    <form role="form" action="/sell/seller/product/save" method="post">
                        <input type="hidden" name="productId" value="${(productInfo.productId)!""}">
                        <div class="form-group">
                            <label>名称</label>
                            <input type="text" class="form-control" name="productName" value="${(productInfo.productName)!""}"/>
                        </div>
                        <label>价格</label>
                        <div class="input-group">
                            <input type="text" class="form-control" name="productPrice" value="${(productInfo.productPrice)!""}"/>
                            <span class="input-group-addon"><i class="glyphicon glyphicon-usd"></i></span>
                        </div>
                        <div class="form-group">
                            <label>库存</label>
                            <input type="number" class="form-control" name="productStock" value="${(productInfo.productStock)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>描述</label>
                            <input type="text" class="form-control" name="productDescription" value="${(productInfo.productDescription)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>图片</label>
                            <img src="${(productInfo.productIcon)!""}" alt="">
                            <input type="text" class="form-control" name="productIcon" value="${(productInfo.productIcon)!""}"/>
                        </div>
                        <div class="form-group">
                            <label>类目</label>
                            <select name="categoryType" class="form-control">
                            <#list productCategoryList as category>

                                    <option value="${category.categoryType}"
                                        <#if (productInfo.categoryType)?? && (productInfo.categoryType) == category.categoryType>
                                            selected
                                        </#if>
                                        >${category.categoryName}
                                </option>
                            </#list>
                            </select>

                        </div> <button type="submit" class="btn btn-default">Submit</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

</div>
</body>
</html>