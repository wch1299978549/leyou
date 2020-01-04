package com.leyou.item.controller;

import com.leyou.common.po.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.item.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
