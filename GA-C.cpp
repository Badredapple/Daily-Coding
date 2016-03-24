// 基于遗传算法的TSP问题，在C语言下的求解
//代码来自：http://www.codeforge.cn/read/262097/GA.cpp__html

/*			这下面是基于遗传算法的TSP问题求解，我的想法是
			看着这个解和那个js部分的，做出计算600点的TSP问
			题以后，再进行mark必过点。产生最短路径
			
*/

#include <iostream>
#include <iomanip>
#include <math.h>

using namespace std;
#define MAX 9									//客户数
#define POPSIZE 8								//种群个数		
#define NL 100000								//遗传循环数
typedef unsigned tcode[MAXC+1];					//遗传代码存储数组
typedef tcode tpop[POPSIZE+1];			  		//TSP问题最优解存储数组
typedef struct{
	unsigned x,y;
}tcoord;//坐标结构

float ran(void);
void inibuf(unsigned bl[MAXC+1]);  				//产生1~10的随机数
unsigned nrand(unsigned sta,unsigned end);		//产生0-1的随机数
void genrcode(tcode retc);						//产生遗传个体
void printcode(tcode co);
float distance(tcoord p1, tcoord p2);
float fitfun(tcode co);							//求总距离长度
void inipop(tpop pop);							//产生多组遗传数组
void sortpop(tpop pop);							//排序
void repo(tpop pf,tpop prep);
void cross(tpop pop);
void mutation(tpop pop);
void copypop(tpop po, tpop pd);
void copycode(tcode co, tcode cd);
tcoord location[MAXC+1];

main()
{
	FILE *f1, *f2;
	FILE *fo, *fn;
	unsigned ch, i, nloop = 0, n = 0;
}











