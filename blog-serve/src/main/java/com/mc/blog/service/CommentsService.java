package com.mc.blog.service;

import com.mc.blog.vo.Result;
import com.mc.blog.vo.params.CommentParam;

public interface CommentsService {
    /**
     * 根据文章Id获取评论
     * @param articleId
     * @return
     */
    Result commentsByArticleId(Long articleId);

    Result comment(CommentParam commentParam);
}
