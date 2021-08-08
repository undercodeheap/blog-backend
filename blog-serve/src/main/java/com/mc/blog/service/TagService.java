package com.mc.blog.service;

import com.mc.blog.vo.Result;
import com.mc.blog.vo.TagVo;

import java.util.List;

public interface TagService {

    List<TagVo> findTagsByArticleId(Long articleId);

    Result hots(int limit);
}
