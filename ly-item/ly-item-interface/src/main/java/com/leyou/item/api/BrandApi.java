package com.leyou.item.api;

import com.leyou.common.po.PageResult;
import com.leyou.item.pojo.Brand;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface BrandApi {
    @GetMapping("brand/page")
    public PageResult<Brand> pageQuery(@RequestParam(value = "page",defaultValue = "1") Integer page,
                                                       @RequestParam(value = "rows",defaultValue = "5") Integer rows,
                                                       @RequestParam(value = "sortBy",required = false) String sortBy,
                                                       @RequestParam(value = "desc",required = false) Boolean desc,
                                                       @RequestParam(value = "key",required = false) String key
    );
    @GetMapping("brand/cid/{cid}")
    public List<Brand> queryBrandByCategory(@PathVariable("cid") Long cid);
}
