package com.vermouth.sell.service.impl;

import com.vermouth.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Test
    public void findOne() throws Exception {
        ProductCategory productCategory = categoryService.findOne(1);
        Assert.assertEquals(new Integer(1), productCategory.getCategoryId());
    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> result = categoryService.findAll();
        Assert.assertNotEquals(0, result.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception {
        Set<Integer> set = new HashSet<>();
        set.add(2);
        set.add(3);
        set.add(4);
        List<ProductCategory> result = categoryService.findByCategoryTypeIn(set);
        Assert.assertEquals(3, result.size());
    }

    @Test
    public void save() throws Exception {
        ProductCategory productCategory = new ProductCategory("男生专享", 1);
        ProductCategory result = categoryService.save(productCategory);
        Assert.assertNotNull(result);
    }

}