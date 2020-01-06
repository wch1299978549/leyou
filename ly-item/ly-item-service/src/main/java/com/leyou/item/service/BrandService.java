package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.po.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

@Service
public class BrandService {
    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Brand> pageQuery(Integer page, Integer rows, String sortBy, Boolean desc, String key) {
        //开启分页
        PageHelper.startPage(page,rows);

        Example example = new Example(Brand.class);

        if(StringUtil.isNotEmpty(key)){
            Example.Criteria criteria = example.createCriteria();
            criteria.andLike("name","%"+key+"%");
        }
        if(StringUtil.isNotEmpty(sortBy)){
            example.setOrderByClause(sortBy+(desc?" desc":" asc"));
        }
        Page<Brand> brandPage = (Page<Brand>) brandMapper.selectByExample(example);

        return new PageResult<>(brandPage.getTotal(),new Long(brandPage.getPages()),brandPage.getResult());
    }

    /**
     * 品牌增加业务
     * @param brand
     * @param cids
     */
    @Transactional
    public void addBrand(Brand brand, List<Long> cids) {
        //插入tb_brand
        brandMapper.insertSelective(brand);
        //循环插入关系表
        for (Long i : cids) {
            brandMapper.insertBrandCategory(i,brand.getId());
        }
    }

    @Transactional
    public void updateBrand(Brand brand, List<Long> cids) {
        //更新tb_grand
        this.brandMapper.updateByPrimaryKey(brand);
        //删除关系表
        this.brandMapper.deleteBrandCategory(brand.getId());
        //添加关系表
        cids.forEach(t -> {
            brandMapper.insertBrandCategory(t,brand.getId());
        });
    }

    public List<Brand> queryBrandByCategory(Long cid) {

        return this.brandMapper.queryBrandByCategory(cid);
    }
}
