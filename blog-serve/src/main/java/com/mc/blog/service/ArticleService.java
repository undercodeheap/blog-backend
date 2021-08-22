package com.mc.blog.service;

import com.mc.blog.vo.Result;
import com.mc.blog.vo.params.ArticleParam;
import com.mc.blog.vo.params.PageParams;

public interface ArticleService {
    /**
     * 分页查询 文章列表
     * @param pageParams
     * @return
     */
    Result listArticle(PageParams pageParams);

    /**
     * 最热 文章
     * @param limit
     * @return
     */
    Result hotArticles(int limit);

    /**
     * 最新 文章
     * @param limit
     * @return
     */
    Result newArticles(int limit);

    /**
     * 文章归档
     * @return
     */
    Result listArchives();

    /**
     * 查看文章详情
     * @return
     */
    Result findArticleById(Long articleId);

    Result publish(ArticleParam articleParam);
}
