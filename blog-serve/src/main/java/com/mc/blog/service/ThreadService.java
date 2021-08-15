package com.mc.blog.service;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.mc.blog.entity.Article;
import com.mc.blog.mapper.ArticleMapper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class ThreadService {

    // 希望此操作在线程池中，不影响主线程
    @Async("taskExecutor")
    public void updateArticleViewCount(ArticleMapper articleMapper, Article article) {
        int viewCounts = article.getViewCounts();
        Article articleUpdate = new Article();
        articleUpdate.setViewCounts(viewCounts +1);
        LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Article::getId, article.getId());
        // 设置一个 为了多线程的安全
        updateWrapper.eq(Article::getViewCounts, viewCounts);
        // update article set view_count = xx+1 where view_count = xx and id = XX;
        articleMapper.update(articleUpdate, updateWrapper);
    }
}
