public class HelloGoodbye {
    public static void main(String[] args) {
        // 获取两个命令行参数
        String name1 = args[0];
        String name2 = args[1];

        // 打印 Hello 消息（按照原顺序）
        System.out.println("Hello " + name1 + " and " + name2 + ".");

        // 打印 Goodbye 消息（倒序）
        System.out.println("Goodbye " + name2 + " and " + name1 + ".");
    }
}
