package com.mc.blog.service;

import com.mc.blog.vo.CategoryVo;

public interface CategoryService {
    CategoryVo findCategoryById(Long categoryId);
}
