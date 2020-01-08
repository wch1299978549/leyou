package com.leyou.item.api;

import com.leyou.common.po.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface GoodsApi {
    @GetMapping("spu/page")
    public PageResult<Spu> querySpuByPage(@RequestParam(value = "key" ,required = false) String key,
                                                          @RequestParam(value = "saleable" ,required = false) Boolean saleable,
                                                          @RequestParam(value = "page" ,defaultValue = "1") Integer page,
                                                          @RequestParam(value = "rows" ,defaultValue = "5") Integer rows
    );

    /**
     * 请求回显商品详情
     * @param sid
     * @return
     */
    @GetMapping("spu/detail/{sid}")
    public SpuDetail querySpuDetailBySpuId(@PathVariable("sid") Long sid);

    /**
     * 请求回显商品sku数据和库存数据
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public List<Sku> querySkuBySpuId(@RequestParam("id") Long id);
}
