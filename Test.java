import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Test {

    public static void main(String[] args) {
        BinomialHeap heap = new BinomialHeap();
        System.out.println(heap.empty());

        int [] arr1 = IntStream.generate(() -> new Random().nextInt(1,100)).limit(10).toArray();
        int [] arr2 = IntStream.generate(() -> new Random().nextInt(1,100)).limit(10).toArray();

        System.out.println(Arrays.toString(arr1));
        for (int j : arr1) {
            heap.insert(j, String.valueOf(j) + "_1");
        }

//        heap.insert(14, "14");
//        heap.insert(5, "5");
//        heap.insert(29, "29");
//        heap.insert(4, "4_1");
//        heap.insert(5, "5_1");
//        heap.insert(6, "6");
//        heap.insert(1, "1");
//        heap.insert(2, "2");
//        heap.insert(3, "3");
//        heap.insert(7, "7");
//        heap.insert(1, "1");

        heap.print();
        System.out.println("size: " + heap.size());
        System.out.println("min: " + heap.findMin().key);
        System.out.println("is empty: " + heap.empty());
        System.out.println("tree num: " + heap.numTrees());

        BinomialHeap heap2 = new BinomialHeap();
        System.out.println(Arrays.toString(arr2));

        for (int j : arr2) {
            heap2.insert(j, String.valueOf(j) + "_2");
        }

//        heap2.insert(9, "9_2");
//        heap2.insert(10, "10_2");
//        heap2.insert(7, "7_2");
//        heap2.insert(49, "49_2");
//        heap2.insert(80, "80_2");
//        heap2.insert(70, "70_2");

        heap2.print();

        System.out.println("------------------------------------------");
        heap.meld(heap2);
        heap.print();
        System.out.println("size: " + heap.size());
        System.out.println("min: " + heap.findMin().key);
        System.out.println("is empty: " + heap.empty());
        System.out.println("tree num: " + heap.numTrees());


//
//        System.out.println(heap.size());
//        System.out.println(heap.findMin().key);
//        System.out.println(heap.empty());
//        System.out.println(heap.numTrees());

        //System.out.println(heap.last.child.item.key);
       // heap.decreaseKey(heap.last.child.item, 2);
       // System.out.println(heap);
//System.out.println(heap.findMin().key);
////
//        System.out.println(heap.last.child.child.item.key); //4
//        heap.decreaseKey(heap.last.child.child.item, 3);
//        System.out.println(heap);
//        System.out.println(heap.findMin().key);

//        System.out.println(heap.last.item.key);
//        heap.decreaseKey(heap.last.item, 1);
//        System.out.println(heap);
//        System.out.println(heap.findMin().key);



    }
}
