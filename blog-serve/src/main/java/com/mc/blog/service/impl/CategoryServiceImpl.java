package com.mc.blog.service.impl;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.mc.blog.entity.Category;
import com.mc.blog.mapper.CategoryMapper;
import com.mc.blog.service.CategoryService;
import com.mc.blog.vo.CategoryVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public CategoryVo findCategoryById(Long categoryId) {
        Category category = categoryMapper.selectById(categoryId);
        CategoryVo categoryVo = new CategoryVo();
        BeanUtils.copyProperties(category, categoryVo);
        return categoryVo;
    }
}
