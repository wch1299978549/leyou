package com.leyou.item.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.leyou.common.po.PageResult;
import com.leyou.item.mapper.*;
import com.leyou.item.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.Arrays;
import java.util.Date;
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
    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private StockMapper stockMapper;

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

    @Transactional
    public void saveGoods(Spu spu) {
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(new Date());
        //保存数据到spu表
        this.spuMapper.insertSelective(spu);

        Long id = spu.getId();
        SpuDetail spuDetail = spu.getSpuDetail();
        spuDetail.setSpuId(id);
        //保存数据到spuDetail表
        this.spuDetailMapper.insert(spuDetail);

        List<Sku> skus = spu.getSkus();
        saveSkus(spu,skus);
    }

    private void saveSkus(Spu spu, List<Sku> skus) {
        skus.forEach(sku -> {
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(new Date());
            //保存数据到sku表
            this.skuMapper.insertSelective(sku);

            Stock stock = new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            //保存数据到stock表
            this.stockMapper.insert(stock);
        });
    }

    /**
     * 根据商品id查询商品详情表
     * @param sid
     * @return
     */
    public SpuDetail querySpuDetailBySpuId(Long sid) {
        SpuDetail spuDetail = this.spuDetailMapper.selectByPrimaryKey(sid);
        return spuDetail;
    }

    /**
     * 将数据回显到浏览器
     * @param id
     * @return
     */
    public List<Sku> querySkuBySpuId(Long id) {
        Sku sku = new Sku();
        sku.setSpuId(id);
        List<Sku> skuList = this.skuMapper.select(sku);
        skuList.forEach(sku1 -> {
            Stock stock = this.stockMapper.selectByPrimaryKey(sku1.getId());
            sku1.setStock(stock.getStock());
        });
        return skuList;
    }

    @Transactional
    public void updateGoods(Spu spu) {
        spu.setLastUpdateTime(new Date());
        //更新商品表
        this.spuMapper.updateByPrimaryKey(spu);
        //更新商品详情表
        this.spuDetailMapper.updateByPrimaryKey(spu.getSpuDetail());
        //更新sku表
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        List<Sku> skuList = this.skuMapper.select(sku);
        skuList.forEach(sku1 -> {
            this.stockMapper.deleteByPrimaryKey(sku1.getId());
            this.skuMapper.delete(sku1);
        });
        saveSkus(spu,spu.getSkus());
    }
}
