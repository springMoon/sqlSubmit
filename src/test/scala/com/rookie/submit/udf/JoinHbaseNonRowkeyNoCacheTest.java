package scala.com.rookie.submit.udf;

import com.rookie.submit.udf.JoinHbaseNonRowkeyNoCache;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JoinHbaseNonRowkeyNoCacheTest {

    public static final Logger LOG = LoggerFactory.getLogger("JoinHbaseNonRowkey1Test");

    //    @Test
    public void testjoin1() {

        // new object
        JoinHbaseNonRowkeyNoCache joinHbase = new JoinHbaseNonRowkeyNoCache("cf", "c1,c2,c3,c4,c5,c6,c7,c8,c9,c10");

        // init join Hbase
        joinHbase.open(null);

        // query hbase
        joinHbase.eval("002");

    }
}
