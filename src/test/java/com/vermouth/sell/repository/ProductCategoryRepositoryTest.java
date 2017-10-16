package com.vermouth.sell.repository;

import com.vermouth.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Test
    public void findOneTest() {
        ProductCategory productCategory = productCategoryRepository.findOne(1);
        System.out.println(productCategory);
    }

    @Test
    @Transactional                  //不管测试成功与否都会完全回滚
    public void saveTest() {
        ProductCategory productCategory = new ProductCategory("女生最爱", 6);
        ProductCategory result = productCategoryRepository.save(productCategory);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void updateTest() {
        ProductCategory productCategory = new ProductCategory("男生最爱", 3);
        ProductCategory result = productCategoryRepository.save(productCategory);
        Assert.assertNotEquals(null, result);
    }

    @Test
    public void findByCategoryTypeInTest() {
        Set<Integer> set = new HashSet<>();
        set.add(2);
        set.add(3);
        set.add(4);
        List<ProductCategory> result = productCategoryRepository.findByCategoryTypeIn(set);
        Assert.assertNotEquals(0, result.size());
    }
}