package com.leyou.item.mapper;

import com.leyou.item.pojo.Brand;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface BrandMapper extends Mapper<Brand> {

    @Insert("insert into tb_category_brand values(#{i},#{id})")
    void insertBrandCategory(@Param("i") Long i,@Param("id") Long id);

    @Delete("delete from tb_category_brand where brand_id=#{id}")
    void deleteBrandCategory(@Param("id") Long id);

    @Select("SELECT b.* from tb_brand b " +
            "LEFT JOIN tb_category_brand cb ON b.id = cb.brand_id " +
            "LEFT JOIN tb_category c ON c.id = cb.category_id " +
            "WHERE c.id = #{cid}")
    List<Brand> queryBrandByCategory(@Param("cid") Long cid);
}
