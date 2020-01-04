package com.leyou.item.service;

import com.leyou.item.mapper.CategoryMapper;
import com.leyou.item.pojo.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    public List<Category> queryByParentId(Long id) {
        Category category = new Category();
        category.setParentId(id);
        return categoryMapper.select(category);
    }

    public List<Category> queryByBrandId(Long bid) {

        return categoryMapper.queryByBrandId(bid);
    }

    public List<String> queryNameByIds(List<Long> asList) {
        List<String> names = new ArrayList<>();
        List<Category> categories = this.categoryMapper.selectByIdList(asList);
        categories.forEach(category -> {
            names.add(category.getName());
        });
        return  names;
    }
}
