package com.test.service.imp;

import com.test.bean.CouponUserBean;
import com.test.maper.CouponUserMaper;
import com.test.service.CouponUserService;
import com.jryz.dao.service.BasicService;
import org.springframework.stereotype.Service;

@Service
public class CouponUserServiceImp extends BasicService<CouponUserBean> implements CouponUserService {

    /**
     * 继承需传入 maper
     */
    public CouponUserServiceImp() {
        super(CouponUserMaper.class);
    }
}
