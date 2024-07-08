import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanCoding {

    public static void printHuffmanCodes(HuffmanNode root, String code, Map<Character, String> huffmanCode) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null && Character.isLetter(root.character)) {
            huffmanCode.put(root.character, code);
            return;
        }
        printHuffmanCodes(root.left, code + "0", huffmanCode);
        printHuffmanCodes(root.right, code + "1", huffmanCode);
    }

    public static void main(String[] args) {
        String text = "huffman coding in java";

        if (text == null || text.length() == 0) {
            System.out.println("Metin boş");
            return;
        }

        // Karakter sıklığını hesapla
        Map<Character, Integer> frequencyMap = new HashMap<>();
        for (char c : text.toCharArray()) {
            frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);
        }

        // Min-Heap (PriorityQueue) oluştur
        PriorityQueue<HuffmanNode> priorityQueue = new PriorityQueue<>(new FrequencyComparator());

        // Tüm karakterleri Huffman düğümlerine çevir ve kuyruğa ekle
        for (Map.Entry<Character, Integer> entry : frequencyMap.entrySet()) {
            HuffmanNode node = new HuffmanNode();
            node.character = entry.getKey();
            node.frequency = entry.getValue();
            node.left = null;
            node.right = null;

            priorityQueue.add(node);
        }

        // Kuyrukta en az bir düğüm olduğundan emin ol
        if (priorityQueue.isEmpty()) {
            System.out.println("Priority queue is empty");
            return;
        }

        // Huffman ağacını oluştur
        while (priorityQueue.size() > 1) {
            // En düşük frekansa sahip iki düğümü al
            HuffmanNode x = priorityQueue.poll();
            HuffmanNode y = priorityQueue.poll();

            // Yeni bir iç düğüm oluştur ve bu iki düğümü çocukları olarak ayarla
            HuffmanNode sumNode = new HuffmanNode();
            sumNode.frequency = x.frequency + y.frequency;
            sumNode.character = '-';
            sumNode.left = x;
            sumNode.right = y;

            // Yeni düğümü kuyruğa ekle
            priorityQueue.add(sumNode);
        }

        // Huffman ağacının kökü
        HuffmanNode root = priorityQueue.poll();

        // Root'un null olup olmadığını kontrol et
        if (root == null) {
            System.out.println("Huffman ağacının kökü null");
            return;
        }

        // Huffman kodlarını tutacak bir harita
        Map<Character, String> huffmanCode = new HashMap<>();
        printHuffmanCodes(root, "", huffmanCode);

        // Kodları yazdır
        for (Map.Entry<Character, String> entry : huffmanCode.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
}
