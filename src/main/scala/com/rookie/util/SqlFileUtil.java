package com.rookie.util;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SqlFileUtil {

    public static List<String> readFile(String fileName) throws IOException {
        // list store return sql
        List<String> sqlList = new ArrayList<String>();

        File file = new File(fileName);
        // check file exists
        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
        }
        // read file
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        StringBuilder sqlBuffer = new StringBuilder();
        while ((line = br.readLine()) != null) {
            // ignore empty line and comment line
            if (StringUtils.isEmpty(line) || line.startsWith("--")) {
                continue;
            }
            // remove comment
            line = line.substring(line.indexOf("--"));
            // add current line to sqlBuffer
            sqlBuffer.append(line);
            // check sql end
            if (line.endsWith(";")) {
                // add sql to sqlList
                sqlList.add(sqlBuffer.toString());
                // remove StringBuilder
                sqlBuffer.delete(0, sqlBuffer.length() - 1);
            }
        }
        // if last sql sentence not end with ";"
        if (sqlBuffer.length() != 0) {
            sqlList.add(sqlBuffer.toString());
        }
        return sqlList;


    }

}
