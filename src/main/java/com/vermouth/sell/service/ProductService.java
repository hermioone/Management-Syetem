package com.vermouth.sell.service;

import com.vermouth.sell.dataobject.ProductInfo;
import com.vermouth.sell.datatransferobject.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    ProductInfo findOne(String productId);

    //查询所有在架商品列表
    List<ProductInfo> findUpAll();

    Page<ProductInfo> findALl(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //添加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //删除库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);

    //下架
    ProductInfo offSale(String productId);
}
