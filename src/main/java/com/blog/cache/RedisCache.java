package com.blog.cache;

import com.blog.dao.BlogDao;
import com.blog.pojo.Blog;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.catalina.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

/*
    使用redis作为热门文章的缓存
 */
@Service
public class RedisCache {

    @Autowired
    private BlogDao blogDao;

    @Autowired
    private StringRedisTemplate template;

    @Scheduled(cron = "0 0 12 * * ?")
    public void cacheBlog() throws JsonProcessingException {
        List<Blog> byViews = blogDao.findByViews();
        ObjectMapper mapper = new ObjectMapper();

        template.opsForValue().set("1", "1111");

        for(int i = 0; i < byViews.size(); i++)
        {
//            System.out.println(b.getId());
            Blog b = byViews.get(i);
            template.opsForValue().set(b.getId().toString(), mapper.writeValueAsString(b));
        }
    }

    public Blog getBlog(String id)
    {
        String s = template.opsForValue().get(id);
        ObjectMapper mapper = new ObjectMapper();
        Blog b = null;
        try{
            b = mapper.readValue(s, Blog.class);
        }catch (Exception e)
        {

        }
        return b;
    }

}
