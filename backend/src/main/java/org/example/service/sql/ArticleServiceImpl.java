package org.example.service.sql;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Article;
import org.example.mapper.ArticleMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
}
