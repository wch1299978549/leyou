package com.bigdata;

import com.leyou.LySearchService;
import com.leyou.Repository.GoodsRepository;
import com.leyou.client.GoodsClient;
import com.leyou.common.po.PageResult;
import com.leyou.item.pojo.Spu;
import com.leyou.pojo.Goods;
import com.leyou.service.IndexService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LySearchService.class)
public class IndexTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsClient goodsClient;

    @Autowired
    private IndexService indexService;
    @Test
    public void init(){
        //建库
        elasticsearchTemplate.createIndex(Goods.class);
        //建表
        elasticsearchTemplate.putMapping(Goods.class);
    }

    @Test
    public void loadData(){
        int page = 1;
        while(true){
            PageResult<Spu> spuPageResult = goodsClient.querySpuByPage(null, null, page, 50);
            if(null==spuPageResult){
                break;
            }
            page++;
            List<Spu> items = spuPageResult.getItems();
            List<Goods> goodsList = new ArrayList<>();
            items.forEach(spu -> {
                Goods goods = indexService.buildGoods(spu);
                goodsList.add(goods);
            });
            goodsRepository.saveAll(goodsList);
        }
    }
}
