package com.splitcorp.first.tamplateCallback;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

public class Calculator {
    public Integer calcSum(String filePath) throws IOException {
        /*BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            Integer sum = 0;
            String line = null;
            while((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
            }
            return sum;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return -1;*/
        BufferedReaderCallback sumCallback = br -> {
            Integer sum = 0;
            String line = null;
            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
            }
            return sum;
        };
        return fileReadTemplate(filePath, sumCallback);
    }


    public Integer calcMultiply(String filePath) throws IOException {
        LineCallback<Integer> multiplyCallback = (line, value) -> value * Integer.valueOf(line);
        return lineReadTemplate(filePath, multiplyCallback, 1);
    }

    public Integer fileReadTemplate(String filePath, BufferedReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            int ret = callback.doSomethingWithReader(br);
            return ret;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public <T> T lineReadTemplate(String filePath, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filePath));
            T res = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
