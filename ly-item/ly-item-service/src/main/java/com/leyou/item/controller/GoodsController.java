package com.leyou.item.controller;

import com.leyou.common.po.PageResult;
import com.leyou.item.pojo.Sku;
import com.leyou.item.pojo.Spu;
import com.leyou.item.pojo.SpuDetail;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("spu/page")
    public ResponseEntity<PageResult<Spu>> querySpuByPage(@RequestParam(value = "key" ,required = false) String key,
                                                          @RequestParam(value = "saleable" ,required = false) Boolean saleable,
                                                          @RequestParam(value = "page" ,defaultValue = "1") Integer page,
                                                          @RequestParam(value = "rows" ,defaultValue = "5") Integer rows
                                                          ){
        PageResult<Spu> spuPageResult =  goodsService.querySpuByPage(key,saleable,page,rows);
        if(null!=spuPageResult && spuPageResult.getItems().size()>0){
            return ResponseEntity.ok(spuPageResult);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * 请求新增商品
     * @param spu
     * @return
     */
    @PostMapping
    public ResponseEntity<Void> saveGoods(@RequestBody Spu spu){
        this.goodsService.saveGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    /**
     * 请求回显商品详情
     * @param sid
     * @return
     */
    @GetMapping("spu/detail/{sid}")
    public ResponseEntity<SpuDetail> querySpuDetailBySpuId(@PathVariable("sid") Long sid){
        SpuDetail spuDetail = this.goodsService.querySpuDetailBySpuId(sid);
        if(null!=spuDetail){
            return ResponseEntity.ok(spuDetail);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * 请求回显商品sku数据和库存数据
     * @param id
     * @return
     */
    @GetMapping("sku/list")
    public ResponseEntity<List<Sku>> querySkuBySpuId(@RequestParam("id") Long id){
        List<Sku> skuList = this.goodsService.querySkuBySpuId(id);
        if(null!=skuList && skuList.size()>0){
            return ResponseEntity.ok(skuList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    /**
     * 请求修改商品
     * @param spu
     * @return
     */
    @PutMapping
    public ResponseEntity<Void> updateGoods(@RequestBody Spu spu){
        this.goodsService.updateGoods(spu);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
