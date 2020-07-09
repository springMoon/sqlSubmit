package com.rookie.submit.udf;

import org.apache.flink.table.catalog.DataTypeFactory;
import org.apache.flink.table.functions.ScalarFunction;
import org.apache.flink.table.types.inference.TypeInference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Decode extends ScalarFunction {

    private static Logger logger = LoggerFactory.getLogger(Decode.class);

    public Decode() {
    }

    public Object eval(Object... obj) throws Exception {
        int size = obj.length;
        if (size % 2 != 0) {
            logger.error("decode input parameter must pair.");
            throw new Exception("decode input parameter must pair.");
        }
        for (int i = 1; i < size; i += 2) {
            if (String.valueOf(obj[0]).equals(String.valueOf(obj[i]))) {
                return obj[i + 1];
            }
        }
        return obj[size - 1];
    }
    /*public TypeInformation<?> getResultType(Class<?>[] signature) {
        return Types.STRING;
    }*/

    @Override
    public TypeInference getTypeInference(DataTypeFactory typeFactory) {
        return TypeInference.newBuilder().build();
    }

    public static void main(String[] args) throws Exception {
        Decode decode = new Decode();
        Object[] arr = {null, "a", "1", "b", "2", 3};
        System.out.println(decode.eval(arr));
    }
}
