package com.leyou.item.mapper;

import com.leyou.item.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface CategoryMapper extends Mapper<Category> {

    @Select("select c.* from tb_category c " +
            "LEFT JOIN tb_category_brand cb ON c.id = cb.category_id " +
            "LEFT JOIN tb_brand b ON b.id = cb.brand_id " +
            "where b.id=#{bid}")
    List<Category> queryByBrandId(@Param("bid") Long bid);
}
