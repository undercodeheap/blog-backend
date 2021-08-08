package com.mc.blog.service.impl;

import com.mc.blog.entity.Tag;
import com.mc.blog.mapper.TagMapper;
import com.mc.blog.service.TagService;
import com.mc.blog.vo.Result;
import com.mc.blog.vo.TagVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    
    @Autowired
    private TagMapper tagMapper;
    
    @Override
    public List<TagVo> findTagsByArticleId(Long articleId) {
        // mybatisPlus 无法进行多表查询
        List<Tag> tags = tagMapper.findTagsByArticleId(articleId);
        return copyList(tags);
    }

    /**
     * 1、标签拥有的文章数最多，称为最热标签
     * 2、查询 根据 tag_id 分组计数，从大到小，排序取前 limit 个
     * @param limit
     * @return
     */
    @Override
    public Result hots(int limit) {
        List<Long> hotTagIds = tagMapper.findHotsTagIds(limit);
        if(CollectionUtils.isEmpty(hotTagIds)){
            return Result.success(Collections.emptyList());
        }
        List<Tag> tagList = tagMapper.findTagsByTagIds(hotTagIds);
        return Result.success(tagList);
    }

    public List<TagVo> copyList(List<Tag> tagList){
        List<TagVo> tagVoList = new ArrayList<>();
        for (Tag tag : tagList) {
            tagVoList.add(copy(tag));
        }
        return tagVoList;
    }

    public TagVo copy(Tag tag){
        TagVo tagVo = new TagVo();
        BeanUtils.copyProperties(tag,tagVo);
        tagVo.setId(String.valueOf(tag.getId()));
        return tagVo;
    }

}
