package com.leyou.item.controller;

import com.leyou.item.pojo.Category;
import com.leyou.item.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("list")
    public ResponseEntity<List<Category>> queryByParentId(@RequestParam("pid") Long id){
        List<Category> list = categoryService.queryByParentId(id);
        if(list!=null&&list.size()>0){
            return ResponseEntity.ok(list);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("bid/{bid}")
    public ResponseEntity<List<Category>> queryByBrandId(@PathVariable("bid")Long bid){
        List<Category> categories = categoryService.queryByBrandId(bid);
        if(null!=categories && categories.size()>0){
            return ResponseEntity.ok(categories);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
    @GetMapping("names")
    public ResponseEntity<List<String>> queryNamesByIds(@RequestParam("ids") List<Long> ids){
        List<String> stringList = this.categoryService.queryNameByIds(ids);
        if(null!=stringList && stringList.size()>0){
            return ResponseEntity.ok(stringList);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
