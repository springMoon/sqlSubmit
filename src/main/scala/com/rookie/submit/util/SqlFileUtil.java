package com.rookie.submit.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.rookie.submit.common.Constant.DEFAULT_CONFIG_FILE;

public class SqlFileUtil {
   private final static Logger LOG = LoggerFactory.getLogger(SqlFileUtil.class);


    public static List<String> readFile(String fileName) throws IOException {
        // list store return sql
        List<String> sqlList = new ArrayList<>();
        // if file not exists, find in classpath
        File file = new File(fileName);
        if(!file.exists()){
            // find sql file in classpath
            LOG.info(fileName + " not exists, find in class path");
            fileName = SqlFileUtil.class.getClassLoader().getResource("").getPath() + fileName;
        }
        file = new File(fileName);
        // check file exists
        if (!file.exists()) {
            System.out.println("File not found: " + fileName);
            System.exit(-1);
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
