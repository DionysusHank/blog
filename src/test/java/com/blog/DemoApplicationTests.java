package com.blog;

import com.blog.dao.BlogDao;
import com.blog.pojo.Blog;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Autowired
    BlogDao blogDao;

    @Test
    void test1()
    {
        System.out.println(blogDao.findByViews());
    }

}
