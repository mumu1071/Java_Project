package arithmetic;

/**
 * @author yangjie
 * @date 2020-03-04 16:04
 * Description: No Description
 */
public class DpCoins {


    public static void change(int money) {
        //面值
        int[] value = new int[]{25, 21, 10, 5, 1};
        //对应找零张数
        int[] count = new int[value.length];
        for (int i = 0; i < value.length; i++) {
            while (money >= value[i]) {
                money -= value[i];
                count[i]++;
            }
            //输出
            if (count[i] != 0) {
                System.out.println(count[i] + "张" + value[i] + "元");
            }
        }
    }

    /**
     * 硬币找零：动态规划算法
     *
     * @param values     :保存不同硬币的币值的数组
     * @param valueKinds :values数组的大小
     * @param money      :需要找零的面值
     * @param coinsUsed  :保存面值为i的纸币找零所需的最小硬币数
     */
    public static void makeChange(int[] values, int valueKinds, int money,
                                  int[] coinsUsed) {

        coinsUsed[0] = 0;
        // 对所有数额都找零(从小到大)，即保存子问题的解以备用，即填表
        for (int cents = 1; cents <= money; cents++) {

            // 当用最小币值的硬币找零时，所需硬币数量最多
            //这里最小面值为1，那n元就需要n个硬币，所以是最多
            int minCoins = cents;

            // 遍历每一种面值的硬币，看是否可作为找零的其中之一
            //[25, 21, 10, 5, 1]
            for (int kind = 0; kind < valueKinds; kind++) {
                // 若当前面值的硬币小于当前的cents则分解问题并查表
                if (values[kind] <= cents) {
                    int temp = coinsUsed[cents - values[kind]] + 1;
                    if (temp < minCoins) {
                        minCoins = temp;
                    }
                }
            }
            // 保存最小硬币数
            coinsUsed[cents] = minCoins;

            System.out.println("面值为 " + (cents) + " 的最小硬币数 : "
                    + coinsUsed[cents]);
        }
    }

    public static void main(String[] args) {

        // 硬币面值预先已经按降序排列
        int[] coinValue = new int[]{25, 21, 10, 5, 1};
        // 需要找零的面值
        int money = 63;
        // 保存每一个面值找零所需的最小硬币数，0号单元舍弃不用，所以要多加1
        int[] coinsUsed = new int[money + 1];

        makeChange(coinValue, coinValue.length, money, coinsUsed);

        change(63);
    }
}
