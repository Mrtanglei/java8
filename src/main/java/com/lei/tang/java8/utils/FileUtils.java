package com.lei.tang.java8.utils;

import java.io.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author tanglei
 * @date 2019/12/9
 */
@Slf4j
public class FileUtils {

    /**
     * 按字节读取文件，一般用于读取图片、声音、影像等二进制文件
     *
     * @param fileName 文件全名
     * @param bytes    单次读取字节长度
     * @throws IOException
     */
    public static void readFileByBytes(String fileName, byte[] bytes) throws IOException {
        File file = new File(fileName);
        FileInputStream inputStream = null;
        if (file.isFile() && file.exists()) {
            try {
                inputStream = new FileInputStream(file);
                int result;
                while ((result = inputStream.read(bytes)) != -1) {
                    System.out.write(bytes, 0, result);
                }
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
            }
        }
    }

    /**
     * 按字符读取文件，一般用于读取文本文件，建议单字符读取
     *
     * @param fileName 文件全名
     * @param chars    单次读取字符长度
     * @throws IOException
     */
    public static void readFileByChar(String fileName, char[] chars) throws IOException {
        File file = new File(fileName);
        Reader reader = null;
        if (file.isFile() && file.exists()) {
            try {
                reader = new InputStreamReader(new FileInputStream(file));
                int result;
                while ((result = reader.read(chars)) != -1) {
                    System.out.print(chars);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }

    /**
     * 按行读取文件
     *
     * @param fileName 文件全名
     * @throws IOException
     */
    public static void readFileByLine(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader reader = null;
        if (file.isFile() && file.exists()) {
            try {
                reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String result;
                while (StringUtils.hasText((result = reader.readLine()))) {
                    System.out.println(result);
                }
            } finally {
                if (reader != null) {
                    reader.close();
                }
            }
        }
    }
}