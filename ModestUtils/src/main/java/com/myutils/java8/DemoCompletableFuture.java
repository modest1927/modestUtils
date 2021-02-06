package com.myutils.java8;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description
 * @Author OYQJ
 * @Date 2021-02-01
 */
@SpringBootTest
public class DemoCompletableFuture {
    @Test
    public void test1(){

        ExecutorService executorService = Executors.newFixedThreadPool(10);//线程池，一般放在静态成员变量中
        CompletableFuture<Integer> exceptionally = CompletableFuture.supplyAsync(() -> {
            //返回一个数据
            return 1;
        },executorService).whenComplete((res, throwable) -> {
            //完成异步时获取返回值
            System.out.println("返回值是："+res+",异常是："+throwable);
        }).exceptionally((throwable) -> {
            //接收异常，修改返回值
            System.out.println("获得到的异常是:"+throwable);
            return 10;//返回一个值替代原来的返回值
        });
    }
}
