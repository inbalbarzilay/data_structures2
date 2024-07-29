public class Test {

    public static void main(String[] args) {
        BinomialHeap heap = new BinomialHeap();
        System.out.println(heap.empty());

        heap.insert(14, "14");
        heap.insert(5, "5");
        heap.insert(29, "29");
        heap.insert(4, "4_1");
        heap.insert(5, "5_1");

        System.out.println(heap);
        System.out.println(heap.size());
        System.out.println(heap.findMin().key);
        System.out.println(heap.empty());
        System.out.println(heap.numTrees());

        BinomialHeap heap2 = new BinomialHeap();
        heap2.insert(9, "9_2");
        heap2.insert(10, "10_2");
        heap2.insert(7, "7_2");
        heap2.insert(49, "49_2");
//        heap2.insert(80, "80_2");

        System.out.println(heap2);
////
//        heap.meld(heap2);
//        System.out.println(heap);
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
