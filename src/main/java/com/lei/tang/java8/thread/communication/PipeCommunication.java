package com.lei.tang.java8.thread.communication;

import java.io.*;

import com.lei.tang.java8.thread.threadpool.ThreadPoolManager;
import lombok.extern.slf4j.Slf4j;

/**
 * @author tanglei
 * @date 2019/8/2
 * <p>
 * 管道通信
 * PipedWriter:字符管道输出流，它继承于Writer
 * PipedReader:字符管道输入流，它继承于Writer
 * PipedWriter和PipedReader的作用是可以通过管道进行线程间的通讯。在使用管道通信时，必须将PipedWriter和PipedReader配套使用
 * PipedInputStream:字节管道输入流，继承于InputStream
 * PipedOutputStream:字节管道输出流，继承于OutputStream
 */
@Slf4j
public class PipeCommunication {

    private static final String[] strings = new String[]{"你好好好的", "好", "学", "生"};

    public static void main(String[] args) throws IOException, InterruptedException {
        PipedWriter writer = new PipedWriter();
        PipedReader reader = new PipedReader(writer);
        try {
            ThreadPoolManager.getThreadPool().execute(() -> {
                log.debug("writer start");
                for (int i = 0; i < strings.length; i++) {
                    try {
                        log.debug("index = " + i);
                        writer.write(strings[i]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                log.debug("writer end");
            });

            ThreadPoolManager.getThreadPool().execute(() -> {
                log.debug("reader start");
                int i = 0;
                try {
                    while ((i = reader.read()) != -1) {
                        log.debug("读取数据 " + (char) i);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                log.debug("reader end");
            });
        } finally {
            writer.close();
        }
    }
}
