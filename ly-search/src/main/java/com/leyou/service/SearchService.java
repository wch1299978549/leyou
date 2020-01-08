package com.leyou.service;

import com.leyou.Repository.GoodsRepository;
import com.leyou.client.GoodsClient;
import com.leyou.common.po.PageResult;
import com.leyou.pojo.Goods;
import com.leyou.utils.SearchRequest;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    @Autowired
    private GoodsRepository goodsRepository;

    public PageResult<Goods> search(SearchRequest searchRequest) {
        //用户搜索的关键字
        String key = searchRequest.getKey();
        //第几页
        Integer page = searchRequest.getPage();
        //创建查询对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        nativeSearchQueryBuilder.withQuery(QueryBuilders.matchQuery("all",key).operator(Operator.AND));
        //分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(page-1,searchRequest.getSize()));

        Page<Goods> goodsPage = goodsRepository.search(nativeSearchQueryBuilder.build());

        return new PageResult<>(goodsPage.getTotalElements(),new Long(goodsPage.getTotalPages()),goodsPage.getContent());
    }
}
