package com.heng;/**
 * @author shkstart
 * @create 2023-03-01 19:03
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 *@Auther:Jarvis
 *@Date:2023年03月2023/3/1日19:03
 *@Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FoundationAppStart.class)
public class BaseTest {

    @Test
    public void test() throws Exception{
        System.out.println(new Date());
    }
}
