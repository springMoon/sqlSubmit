package com.rookie.submit.util;

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
            if (StringUtils.isEmpty(line) || line.trim().startsWith("--")) {
                continue;
            }
            // remove comment
            if (line.contains("--")) {
                line = line.substring(0, line.indexOf("--"));
            }
            // add current line to sqlBuffer
            sqlBuffer.append(line);
            sqlBuffer.append("\n");
            // check sql end
            if (line.endsWith(";")) {
                // add sql to sqlList
                String tmpSql = sqlBuffer.toString();
                // remove last ";"
                tmpSql = tmpSql.substring(0, tmpSql.lastIndexOf(";"));
                sqlList.add(tmpSql);
                // remove StringBuilder
                sqlBuffer.delete(0, sqlBuffer.length());
            }
        }
        // if last sql sentence not end with ";"
        if (sqlBuffer.length() != 0) {
            sqlList.add(sqlBuffer.toString());
        }
        return sqlList;


    }

}
