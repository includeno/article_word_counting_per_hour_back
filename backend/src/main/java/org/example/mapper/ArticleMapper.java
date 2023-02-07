package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.example.entity.Article;
import org.springframework.stereotype.Component;

@Component
public interface ArticleMapper extends BaseMapper<Article> {
}