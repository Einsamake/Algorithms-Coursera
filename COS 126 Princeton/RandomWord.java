import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        int count = 0;

        // 当标准输入中还有字符串时循环
        while (!StdIn.isEmpty()) {
            String word = StdIn.readString();
            count++;

            // Knuth's method: 以 1/i 的概率选择当前单词作为新的 champion
            // StdRandom.bernoulli(p) 返回 true 的概率为 p，返回 false 的概率为 1-p
            if (StdRandom.bernoulli(1.0 / count)) {
                champion = word;
            }
        }

        // 打印最终存活下来的 champion
        StdOut.println(champion);
    }
}
