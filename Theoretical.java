import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Theoretical {
    public static void main(String[] args) {
        BinomialHeap heap = new BinomialHeap();
        int i = 5;
        int n = (int) Math.pow(3, i + 7) - 1;

        long startTime = System.currentTimeMillis();

        for (int j = n; j > 0; j--) {
            heap.insert(j, String.valueOf(j) + "_1");
        }

        for (int j = (int) Math.pow(2, 5) - 1; j < n; j++) {
            heap.deleteMin();
        }

        long stopTime = System.currentTimeMillis();

        System.out.println(stopTime - startTime);
//        System.out.println(BinomialHeap.linkCount);
        System.out.println(heap.numTrees());
//        System.out.println(BinomialHeap.deletedRankSum);
    }
}
