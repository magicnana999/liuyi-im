package com.creolophus.im.test;

import com.creolophus.im.boot.Start;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

/**
 * @author yuqinglei
 * @date 2017/12/27.
 * <p>
 * 描述：单元测试基类
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Start.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BaseTest {
    protected static final String USERAGENT = "junit";
    protected static final String token = "Bearer f7cc795a6ccc2a5732d3a7a4e4c8ba6a";
    @Autowired
    protected MockMvc mvc;

    @Test
    public void test() throws Exception {
        System.out.println("======================== 单元测试完成 ========================");
    }
}
