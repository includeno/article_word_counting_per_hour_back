package org.example.service.sql;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.ArticleVersion;
import org.example.mapper.ArticleVersionMapper;
import org.springframework.stereotype.Service;

@Service
public class ArticleVersionServiceImpl extends ServiceImpl<ArticleVersionMapper, ArticleVersion> implements ArticleVersionService {
}
