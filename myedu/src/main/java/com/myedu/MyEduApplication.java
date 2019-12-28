package com.myedu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

/**
 * 启动程序
 * 
 * @author ruoyi
 */
@EnableCaching
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class MyEduApplication
{
    public static void main(String[] args)
    {    System.setProperty("es.set.netty.runtime.available.processors", "false");
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(MyEduApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  微托管   ლ(´ڡ`ლ)ﾞ  \n" +
                " .-------.       ____     __        \n" +
                " |  _ _   \\      \\   \\   /  /    \n" +
                " | ( ' )  |       \\  _. /  '       \n" +
                " |(_ o _) /        _( )_ .'         \n" +
                " | (_,_).' __  ___(_ o _)'          \n" +
                " |  |\\ \\  |  ||   |(_,_)'         \n" +
                " |  | \\ `'   /|   `-'  /           \n" +
                " |  |  \\    /  \\      /           \n" +
                " ''-'   `'-'    `-..-'              ");
    }
}
