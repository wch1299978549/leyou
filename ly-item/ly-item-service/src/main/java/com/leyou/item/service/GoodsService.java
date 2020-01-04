package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.po.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.mapper.SpuDetailMapper;
import com.leyou.item.mapper.SpuMapper;
import com.leyou.item.pojo.Brand;
import com.leyou.item.pojo.Spu;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.List;

@Service
public class GoodsService {
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private BrandMapper brandMapper;

    public PageResult<Spu> querySpuByPage(String key, Boolean saleable, Integer page, Integer rows) {

        //开启分页
        PageHelper.startPage(page,rows);
        //或的对象
        Example example = new Example(Spu.class);
        //获取Criteria对象
        Example.Criteria criteria = example.createCriteria();
        if(StringUtils.isNotBlank(key)){
            criteria.andLike("title","%"+key+"%");
        }
        if(null!=saleable){
            criteria.andEqualTo("saleable",saleable);
        }
        // 查询结果
        Page<Spu> spuPage = (Page<Spu>) this.spuMapper.selectByExample(example);
        List<Spu> spuList = spuPage.getResult();
        spuList.forEach(spu -> {
            //通过分类id获取分类名称
            List<String> names = this.categoryService.queryNameByIds(Arrays.asList(spu.getCid1(),spu.getCid2(),spu.getCid3()));
            //拼接
            String join = StringUtils.join(names, "/");
            //分类名称
            spu.setCname(join);

            //根据品牌id获取品牌名称
            Brand brand = this.brandMapper.selectByPrimaryKey(spu.getBrandId());
            spu.setBname(brand.getName());
        });
        return new PageResult<>(spuPage.getTotal(),new Long(spuPage.getPages()),spuList);
    }
}
