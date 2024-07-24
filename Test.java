public class Test {

    public static void main(String[] args) {
        BinomialHeap heap = new BinomialHeap();
        System.out.println(heap.empty());

        heap.insert(3, "3");
        heap.insert(4, "4");
        heap.insert(2, "2");
        heap.insert(4, "4");
        heap.insert(5, "5");

        System.out.println(heap);
        System.out.println(heap.size());
        System.out.println(heap.findMin().key);
        System.out.println(heap.empty());
        System.out.println(heap.numTrees());
    }
}
