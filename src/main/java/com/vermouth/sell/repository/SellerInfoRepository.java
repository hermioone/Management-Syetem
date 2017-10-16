package com.vermouth.sell.repository;

import com.vermouth.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerInfoRepository extends JpaRepository<SellerInfo, String>{
    SellerInfo findByOpenid(String openid);
}
