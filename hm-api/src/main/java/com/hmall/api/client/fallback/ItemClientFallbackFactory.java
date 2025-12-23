package com.hmall.api.client.fallback;

import com.hmall.api.client.ItemClient;
import com.hmall.api.dto.ItemDTO;
import com.hmall.api.dto.OrderDetailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
public class ItemClientFallbackFactory implements FallbackFactory<ItemClient> {
    @Override
    public ItemClient create(Throwable throwable) {
        return new ItemClient() {
            @Override
            public List<ItemDTO> queryItemByIds(Collection<Long> ids) {
                log.error("查询商品失败", throwable);
                return Collections.emptyList();
            }

            @Override
            public void deductStock(List<OrderDetailDTO> items) {
                log.error("扣减商品库存失败", throwable);
                throw new RuntimeException(throwable);
            }

            @Override
            public void restoreStock(List<OrderDetailDTO> dtos) {
                log.error("恢复商品库存失败", throwable);
                // 恢复库存失败必须抛出异常，否则事务会提交，导致库存没加回去但订单却取消了
                throw new RuntimeException(throwable);
            }
        };
    }
}
