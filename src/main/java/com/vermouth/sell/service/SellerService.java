package com.vermouth.sell.service;

import com.vermouth.sell.dataobject.SellerInfo;

public interface SellerService {
    SellerInfo findSellerInfoByOpenid(String openid);
}
