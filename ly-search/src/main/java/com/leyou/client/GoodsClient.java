package com.leyou.client;

import com.leyou.item.api.GoodsApi;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient("item-service")
public interface GoodsClient extends GoodsApi {

    //http://item-service/spu/page
    //http://127.0.0.1:9081/spu/page

}
