package com.mc.blog.service;

import com.mc.blog.vo.Result;

public interface CommentsService {
    /**
     * 根据文章Id获取评论
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);
}
