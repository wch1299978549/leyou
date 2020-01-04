package com.leyou.item.service;

import com.leyou.item.mapper.SpecGroupMapper;
import com.leyou.item.mapper.SpecParamMapper;
import com.leyou.item.pojo.SpecGroup;
import com.leyou.item.pojo.SpecParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecService {
    @Autowired
    private SpecGroupMapper specGroupMapper;
    @Autowired
    private SpecParamMapper specParamMapper;

    public List<SpecGroup> querySpecGroup(Long cid) {
        SpecGroup specGroup = new SpecGroup();
        specGroup.setCid(cid);
        //通过分类的id查询规格组
        List<SpecGroup> groupList = specGroupMapper.select(specGroup);
        //通过规格组id查询规格组里面的规格参数
        groupList.forEach(specGroup1 -> {
            SpecParam specParam = new SpecParam();
            specParam.setGroupId(specGroup1.getId());
            List<SpecParam> paramList = specParamMapper.select(specParam);
            //把查询出来的规格参数封装到规格组里面
            specGroup1.setSpecParams(paramList);
        });
        //返回规格组
        return groupList;
    }

    public List<SpecParam> querySpecParam(Long gid, Long cid, Boolean searching, Boolean generic) {
        SpecParam specParam = new SpecParam();
        specParam.setGroupId(gid);
        specParam.setCid(cid);
        specParam.setSearching(searching);
        specParam.setGeneric(generic);
        //通过规格组id查询规格参数
        List<SpecParam> paramList = specParamMapper.select(specParam);
        return paramList;
    }
}
