package com.dch.commons.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dcherdyntsev on 30.08.2015.
 */
public class StreamUtils {

    private static final Logger logger = LoggerFactory.getLogger(StreamUtils.class);

    public static List<String> getStreamLines(InputStream stream, String encoding) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, encoding));
        String line = null;
        List<String> result = new ArrayList<>();
        while((line = br.readLine())!=null) {
            result.add(line);
        }
        br.close();
        return result;
    }

    public static String getStreamString(InputStream stream, String encoding) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream, encoding));
        String line = null;
        StringBuilder result = new StringBuilder();
        while((line = br.readLine())!=null) {
            result.append(line);
        }
        br.close();
        return result.toString();
    }

}
