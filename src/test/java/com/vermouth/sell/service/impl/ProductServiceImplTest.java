package com.vermouth.sell.service.impl;

import com.vermouth.sell.dataobject.ProductInfo;
import com.vermouth.sell.enums.ProductStatusEnum;
import com.vermouth.sell.enums.ResultEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductServiceImpl productService;

    @Test
    public void findOne() throws Exception {
        ProductInfo productInfo = productService.findOne("12312313123");
        Assert.assertTrue(productInfo.getProductId() == "12312313123");
    }

    @Test
    public void findUpAll() throws Exception {
        List<ProductInfo> list = productService.findUpAll();
        Assert.assertNotEquals(0, list.size());
    }

    @Test
    public void findALl() throws Exception {
        PageRequest request = new PageRequest(0, 3);        //PageRequest实现了Pageable
        Page<ProductInfo> productInfoPage = productService.findALl(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("1234567");
        productInfo.setProductName("麻辣小龙虾");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("很牛逼的虾");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result = productService.save(productInfo);
        Assert.assertNotNull(result);
    }

    @Test
    public void onSaleTest() {
        ProductInfo result = productService.onSale("12312313123");
        Assert.assertTrue(result.getProductStatus() == ProductStatusEnum.UP.getCode());
    }

    @Test
    public void offSaleTest() {
        ProductInfo result = productService.offSale("12312313123");
        Assert.assertTrue(result.getProductStatus() == ProductStatusEnum.DOWN.getCode());
    }

}