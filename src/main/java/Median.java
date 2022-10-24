import com.rookie.submit.udaf.math.NumberAcc;
import org.apache.flink.table.functions.AggregateFunction;

import java.util.List;
import java.util.stream.Collectors;

/**
 * agg function: 计算中位数
 */
public class Median extends AggregateFunction<Double, NumberAcc> {
    // 获取 acc 的值
    @Override
    public Double getValue(NumberAcc acc) {
        // sort list
        List<Double> list = acc.list.stream().sorted().collect(Collectors.toList());
        // if list is empty, return null
        if (list.size() == 0) {
            return null;
        } else if (list.size() == 1) {
            // if list have one element, return it
            return list.get(0);
        }
        double val;
        int size = list.size();
        int half = size / 2;
        if (size % 2 == 0) {
            //even, use (size/2 - 1 + size/2) / 2
            val = (list.get(half - 1) + list.get(half)) / 2;
        } else {
            // odd， use size/2
            val = list.get(half);
        }
        return val;
    }
    // 累加元素
    public void accumulate(NumberAcc acc, Double d) {
        acc.list.add(d);
    }
    // 创建累加器
    @Override
    public NumberAcc createAccumulator() {
        return new NumberAcc();
    }

    // 窗口聚合
    public void merge(NumberAcc acc, Iterable<NumberAcc> it) {
        for (NumberAcc a : it) {
            acc.list.addAll(a.list);
        }
    }
}
