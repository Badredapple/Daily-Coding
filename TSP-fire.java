import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
* an example of Simulated Annealing Agorithm to solve TSP
 * 算法主要思路：
 *  1、邻域变换函数：随机交换当前的两个元素。
 *  2、评估函数：以当前方案的路程总和作为评估值。
 * 数据集来源：tsplib （http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/）上的数据att48去除头部。
 * 理想计算结果：10628 ，未取得。
 * 最佳计算结果10653时的参数选择：
 *     外层迭代：1000
 *     内层迭代次数：4000
 *     降温系数：0.991
 *     初始温度：1000
 * 名词解释
 *      伪欧式距离：....
 */
public class SimulatedAnnealingTSP {

    private int cityNum; // 城市数量，编码长度
    private int N;// 每个温度迭代步长
    private int T;// 降温次数
    private float a;// 降温系数
    private float t0;// 初始温度， 尽可能大，最后结果与初始温度无关

    private int[][] distance; // 距离矩阵
    private int bestT;// 最佳出现代数

    private int[] Ghh;// 初始路径编码，当前编码（相对于temGhh 是上次编码）
    private int GhhEvaluation;//评估值
    private int[] bestGh;// 最好的路径编码
    private int bestEvaluation;//最好的路径编码的评估值
    private int[] tempGhh;// 存放临时编码
    private int tempEvaluation;//评估值

    private Random random;

    /**
     * constructor
     *
     * @param cn
     *            城市数量
     * @param t
     *            降温次数
     * @param n
     *            每个温度迭代步长
     * @param tt
     *            初始温度
     * @param aa
     *            降温系数
     *
     **/
    public SimulatedAnnealingTSP(int cn, int n, int t, float tt, float aa) {
        cityNum = cn;
        N = n;
        T = t;
        t0 = tt;
        a = aa;
    }


    @SuppressWarnings("resource")// 给编译器一条指令，告诉它对被批注的代码元素内部的某些警告保持静默
    /**
     * 读取数据集，初始化cityNum, distance[][],
     * @param filename 数据文件名，该文件存储所有城市节点坐标数据
     * @throws IOException
     */
    private void init(String filename) throws IOException {
        // 读取数据
        int[] x;
        int[] y;
        String strbuff;
        BufferedReader data = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename)));
        distance = new int[cityNum][cityNum];
        x = new int[cityNum];
        y = new int[cityNum];
        for (int i = 0; i < cityNum; i++) {
            // 读取一行数据，数据格式1 6734 1453 (编号 x坐标 y坐标)
            strbuff = data.readLine();
            // 字符分割
            String[] strcol = strbuff.split(" ");
            x[i] = Integer.valueOf(strcol[1]);// x坐标
            y[i] = Integer.valueOf(strcol[2]);// y坐标
        }
        // 计算距离矩阵
        // 针对具体问题，距离计算方法也不一样，此处用的是att48作为案例，它有48个城市，距离计算方法为伪欧氏距离，最优值为10628
        for (int i = 0; i < cityNum - 1; i++) {
            distance[i][i] = 0; // 对角线为0
            for (int j = i + 1; j < cityNum; j++) {
                double rij = Math
                        .sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
                                * (y[i] - y[j])) / 10.0);
                //小数部分进位， 1.1-> 2  1.7->2
                int tij = (int) Math.round(rij);// 四舍五入
                if (tij < rij) {
                    distance[i][j] = tij + 1;
                    distance[j][i] = distance[i][j];
                } else {
                    distance[i][j] = tij;
                    distance[j][i] = distance[i][j];
                }
            }
        }
        distance[cityNum - 1][cityNum - 1] = 0;

        Ghh = new int[cityNum];
        bestGh = new int[cityNum];
        bestEvaluation = Integer.MAX_VALUE;//设置初始值位最大
        tempGhh = new int[cityNum];
        tempEvaluation = Integer.MAX_VALUE;//设置初始值位最大
        bestT = 0;//迭代歩数
        random = new Random(System.currentTimeMillis());

        System.out.println("城市数："+ cityNum+","+
                    "每个温度迭代步长（内层迭代上限）："+N+","+
                "降温次数（外层迭代上限）："+T+","+
                "降温系数："+a+","+
                "初始温度："+t0);

    }

    /**
     * 初始化编码Ghh
     * 随机产生一组城市号 0到cityNum的序列
     * 可以用任意方式指定，此处算法中的二重循环我没有看懂
     */
    void initGroup() {
        int i, j;
        Ghh[0] = random.nextInt(65535) % cityNum;
        for (i = 1; i < cityNum;)// 编码长度
        {
            Ghh[i] = random.nextInt(65535) % cityNum;
            for (j = 0; j < i; j++) {
                if (Ghh[i] == Ghh[j]) {
                    break;
                }
            }
            if (j == i) {
                i++;
            }
        }
    }

    // 复制编码体，复制编码Gha到Ghb
    public void copyGh(int[] Gha, int[] Ghb) {
        for (int i = 0; i < cityNum; i++) {
            Ghb[i] = Gha[i];
        }
    }

    /**
     * 估计函数
     * @param chr 路径编码，起始城市,城市1,城市2...城市n
     * @return 路径总长度
     */
    public int evaluate(int[] chr) {
        // 0123
        int len = 0;
        // 计算路径总长度
        for (int i = 1; i < cityNum; i++) {
            len += distance[chr[i - 1]][chr[i]];
        }
        // 加上从最后一个城市回到出发城市的路程
        len += distance[chr[cityNum - 1]][chr[0]];
        return len;
    }

    /**
     * 邻域交换，得到当前编码Ghh的邻域编码tempGhh
     * 随机交换Ghh的两个编码位
     */
    public void Linju(int[] Gh, int[] tempGh) {
        int i, temp;
        int ran1, ran2;
        //copy
        for (i = 0; i < cityNum; i++) {
            tempGh[i] = Gh[i];
        }
        //随机生成两个不同的编码位下标
        ran1 = random.nextInt(65535) % cityNum;
        ran2 = random.nextInt(65535) % cityNum;
        while (ran1 == ran2) {
            ran2 = random.nextInt(65535) % cityNum;
        }
        //交换两个编码位
        temp = tempGh[ran1];
        tempGh[ran1] = tempGh[ran2];
        tempGh[ran2] = temp;
    }

    public void solve() {
        // 初始化编码Ghh
        initGroup();
        copyGh(Ghh, bestGh);// 复制当前编码Ghh到最好编码bestGh
        bestEvaluation = evaluate(Ghh);
        GhhEvaluation = bestEvaluation;
        int k = 0;// 降温次数
        int n = 0;// 迭代步数
        float t = t0;
        float r = 0.0f;

        while (k < T) {//T位降温次数上限
            n = 0;
            while (n < N) {
                Linju(Ghh, tempGhh);// 得到当前编码Ghh的邻域编码tempGhh
                tempEvaluation = evaluate(tempGhh);//评估当前路劲，结果为总的路程
                if (tempEvaluation < bestEvaluation)//if the temp one is better
                {
                    copyGh(tempGhh, bestGh);//copy tempGhh to bestGh
                    bestT = k;
                    bestEvaluation = tempEvaluation;
                }
                //根据Metropolis准则判断是否接受当前解
                r = random.nextFloat();// 返回值属于[0,1]
                if (tempEvaluation < GhhEvaluation
                        || Math.exp((GhhEvaluation - tempEvaluation) / t) > r) {// t = current temperature
                    copyGh(tempGhh, Ghh);
                    GhhEvaluation = tempEvaluation;
                }
                n++;
            }
            t = a * t;
            k++;
        }

        System.out.println("此次运行的最佳长度出现代数：");
        System.out.println(bestT);
        System.out.println("此次运行的最佳长度（理想值为10628，历史最佳结果10653）");
        System.out.println(bestEvaluation);
        System.out.println("此次运行的最佳路径：");
        for (int i = 0; i < cityNum; i++) {
            System.out.print(bestGh[i] + ",");
            if (i % 10 == 0 && i != 0) {
                System.out.println();
            }
        }
    }

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        System.out.println("Start....");
        SimulatedAnnealingTSP sa = new SimulatedAnnealingTSP(48, 4000, 1000, 10000.0f, 0.992f);
        sa.init("f://data.txt");
        sa.solve();
    }
}

