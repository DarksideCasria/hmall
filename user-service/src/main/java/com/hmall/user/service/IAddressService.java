package com.hmall.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hmall.user.domain.po.Address;
import com.hmall.user.mapper.AddressMapper;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 虎哥
 * @since 2023-05-05
 */
public interface IAddressService extends IService<Address> {

    // 新增方法定义，解决返回值类型冲突
    //AddressMapper getBaseMapper();
}
