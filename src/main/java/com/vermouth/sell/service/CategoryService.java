package com.vermouth.sell.service;

import com.vermouth.sell.dataobject.ProductCategory;

import java.util.List;
import java.util.Set;

public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(Set<Integer> categoryTypeSet);

    ProductCategory save(ProductCategory productCategory);
}
