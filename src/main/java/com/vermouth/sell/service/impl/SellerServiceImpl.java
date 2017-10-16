package com.vermouth.sell.service.impl;

import com.vermouth.sell.dataobject.SellerInfo;
import com.vermouth.sell.repository.SellerInfoRepository;
import com.vermouth.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}
