import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

/**
* an example of Simulated Annealing Agorithm to solve TSP
 * �㷨��Ҫ˼·��
 *  1������任���������������ǰ������Ԫ�ء�
 *  2�������������Ե�ǰ������·���ܺ���Ϊ����ֵ��
 * ���ݼ���Դ��tsplib ��http://comopt.ifi.uni-heidelberg.de/software/TSPLIB95/���ϵ�����att48ȥ��ͷ����
 * �����������10628 ��δȡ�á�
 * ��Ѽ�����10653ʱ�Ĳ���ѡ��
 *     ��������1000
 *     �ڲ����������4000
 *     ����ϵ����0.991
 *     ��ʼ�¶ȣ�1000
 * ���ʽ���
 *      αŷʽ���룺....
 */
public class SimulatedAnnealingTSP {

    private int cityNum; // �������������볤��
    private int N;// ÿ���¶ȵ�������
    private int T;// ���´���
    private float a;// ����ϵ��
    private float t0;// ��ʼ�¶ȣ� �����ܴ���������ʼ�¶��޹�

    private int[][] distance; // �������
    private int bestT;// ��ѳ��ִ���

    private int[] Ghh;// ��ʼ·�����룬��ǰ���루�����temGhh ���ϴα��룩
    private int GhhEvaluation;//����ֵ
    private int[] bestGh;// ��õ�·������
    private int bestEvaluation;//��õ�·�����������ֵ
    private int[] tempGhh;// �����ʱ����
    private int tempEvaluation;//����ֵ

    private Random random;

    /**
     * constructor
     *
     * @param cn
     *            ��������
     * @param t
     *            ���´���
     * @param n
     *            ÿ���¶ȵ�������
     * @param tt
     *            ��ʼ�¶�
     * @param aa
     *            ����ϵ��
     *
     **/
    public SimulatedAnnealingTSP(int cn, int n, int t, float tt, float aa) {
        cityNum = cn;
        N = n;
        T = t;
        t0 = tt;
        a = aa;
    }


    @SuppressWarnings("resource")// ��������һ��ָ��������Ա���ע�Ĵ���Ԫ���ڲ���ĳЩ���汣�־�Ĭ
    /**
     * ��ȡ���ݼ�����ʼ��cityNum, distance[][],
     * @param filename �����ļ��������ļ��洢���г��нڵ���������
     * @throws IOException
     */
    private void init(String filename) throws IOException {
        // ��ȡ����
        int[] x;
        int[] y;
        String strbuff;
        BufferedReader data = new BufferedReader(new InputStreamReader(
                new FileInputStream(filename)));
        distance = new int[cityNum][cityNum];
        x = new int[cityNum];
        y = new int[cityNum];
        for (int i = 0; i < cityNum; i++) {
            // ��ȡһ�����ݣ����ݸ�ʽ1 6734 1453 (��� x���� y����)
            strbuff = data.readLine();
            // �ַ��ָ�
            String[] strcol = strbuff.split(" ");
            x[i] = Integer.valueOf(strcol[1]);// x����
            y[i] = Integer.valueOf(strcol[2]);// y����
        }
        // ����������
        // ��Ծ������⣬������㷽��Ҳ��һ�����˴��õ���att48��Ϊ����������48�����У�������㷽��Ϊαŷ�Ͼ��룬����ֵΪ10628
        for (int i = 0; i < cityNum - 1; i++) {
            distance[i][i] = 0; // �Խ���Ϊ0
            for (int j = i + 1; j < cityNum; j++) {
                double rij = Math
                        .sqrt(((x[i] - x[j]) * (x[i] - x[j]) + (y[i] - y[j])
                                * (y[i] - y[j])) / 10.0);
                //С�����ֽ�λ�� 1.1-> 2  1.7->2
                int tij = (int) Math.round(rij);// ��������
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
        bestEvaluation = Integer.MAX_VALUE;//���ó�ʼֵλ���
        tempGhh = new int[cityNum];
        tempEvaluation = Integer.MAX_VALUE;//���ó�ʼֵλ���
        bestT = 0;//�����i��
        random = new Random(System.currentTimeMillis());

        System.out.println("��������"+ cityNum+","+
                    "ÿ���¶ȵ����������ڲ�������ޣ���"+N+","+
                "���´��������������ޣ���"+T+","+
                "����ϵ����"+a+","+
                "��ʼ�¶ȣ�"+t0);

    }

    /**
     * ��ʼ������Ghh
     * �������һ����к� 0��cityNum������
     * ���������ⷽʽָ�����˴��㷨�еĶ���ѭ����û�п���
     */
    void initGroup() {
        int i, j;
        Ghh[0] = random.nextInt(65535) % cityNum;
        for (i = 1; i < cityNum;)// ���볤��
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

    // ���Ʊ����壬���Ʊ���Gha��Ghb
    public void copyGh(int[] Gha, int[] Ghb) {
        for (int i = 0; i < cityNum; i++) {
            Ghb[i] = Gha[i];
        }
    }

    /**
     * ���ƺ���
     * @param chr ·�����룬��ʼ����,����1,����2...����n
     * @return ·���ܳ���
     */
    public int evaluate(int[] chr) {
        // 0123
        int len = 0;
        // ����·���ܳ���
        for (int i = 1; i < cityNum; i++) {
            len += distance[chr[i - 1]][chr[i]];
        }
        // ���ϴ����һ�����лص��������е�·��
        len += distance[chr[cityNum - 1]][chr[0]];
        return len;
    }

    /**
     * ���򽻻����õ���ǰ����Ghh���������tempGhh
     * �������Ghh����������λ
     */
    public void Linju(int[] Gh, int[] tempGh) {
        int i, temp;
        int ran1, ran2;
        //copy
        for (i = 0; i < cityNum; i++) {
            tempGh[i] = Gh[i];
        }
        //�������������ͬ�ı���λ�±�
        ran1 = random.nextInt(65535) % cityNum;
        ran2 = random.nextInt(65535) % cityNum;
        while (ran1 == ran2) {
            ran2 = random.nextInt(65535) % cityNum;
        }
        //������������λ
        temp = tempGh[ran1];
        tempGh[ran1] = tempGh[ran2];
        tempGh[ran2] = temp;
    }

    public void solve() {
        // ��ʼ������Ghh
        initGroup();
        copyGh(Ghh, bestGh);// ���Ƶ�ǰ����Ghh����ñ���bestGh
        bestEvaluation = evaluate(Ghh);
        GhhEvaluation = bestEvaluation;
        int k = 0;// ���´���
        int n = 0;// ��������
        float t = t0;
        float r = 0.0f;

        while (k < T) {//Tλ���´�������
            n = 0;
            while (n < N) {
                Linju(Ghh, tempGhh);// �õ���ǰ����Ghh���������tempGhh
                tempEvaluation = evaluate(tempGhh);//������ǰ·�������Ϊ�ܵ�·��
                if (tempEvaluation < bestEvaluation)//if the temp one is better
                {
                    copyGh(tempGhh, bestGh);//copy tempGhh to bestGh
                    bestT = k;
                    bestEvaluation = tempEvaluation;
                }
                //����Metropolis׼���ж��Ƿ���ܵ�ǰ��
                r = random.nextFloat();// ����ֵ����[0,1]
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

        System.out.println("�˴����е���ѳ��ȳ��ִ�����");
        System.out.println(bestT);
        System.out.println("�˴����е���ѳ��ȣ�����ֵΪ10628����ʷ��ѽ��10653��");
        System.out.println(bestEvaluation);
        System.out.println("�˴����е����·����");
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

