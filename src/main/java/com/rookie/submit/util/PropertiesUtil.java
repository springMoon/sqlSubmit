package com.rookie.submit.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    public static Properties loadProp(String path, boolean isDefaultProp) throws IOException {
        Properties prop = new Properties();

        boolean propExists = new File(path).exists();
        // check file exists
        if (isDefaultProp && !propExists) {
            // is default properties and file not exists, exit -1
            System.out.println("properties not exists : " + path);
            System.exit(-1);
        } else if (!propExists) {
            // job properties and file not exists, ignore it, and return empty prop
            System.out.println("input properties not exists, ignore it");
            return prop;
        }
        // file exists load it
        prop.load(new FileInputStream(path));

        // return prop
        return prop;
    }
}
