package com.mc.blog.service;

import com.mc.blog.vo.CategoryVo;
import com.mc.blog.vo.Result;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);

    Result findAll();

    Result findAllDetail();

    Result categoryDetailById(Long id);
}
