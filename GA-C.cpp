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
	float fopt = 100000;
	tpop pparent,pchild;
	tcode copt;
	rand();
	f1 = fopen("c:\\loc.txt", "rt");
	for(i=0;i<=MAXC;i++)
		fscanf(f1,"%d %d\n", &location[i].x,&location[i].y);
	fclose(f1);
	inipop(pparent);
	fo = fopen("c:\\fopt.txt","wt");
	fn = fopen("c:\\fn.txt","wt");
	while(1)
	{
		sortpop(paparent);
		n++;
		if(fopt>fitfun(pparent[1]))
		{
			fopt = fitfun(pparent[1]);
			nloop = 0;
			copycode(pparent[1],copt);
		}
		
	else nloop++;
	     fprintf(fo,"%f\n",fopt);
		 fprintf(fn,"%d\n",n);
		 if(nloop>NL)	break;
		 repo(pparent,pchild);					//根据轮盘赌博原理选择个体
		 cross(pchild);							//交叉
		 mutation(pchild);						//变异
		 copypop(pchild,pparent);			
		 
 }

	fclose(fo);
	fclose(fn);
	cout<<" The best unicode:"<<endl;
	printcode(copt);
	ch = getchar();
}


float ran(void)
{
	float r;
	r=rand()%32767+1;
	r=r/32768;
	return(r);
}

unsigned nrand(unsigned sta,unsigned)
{
	int ret;
	 ret = rand()%(end-sta+1);
	 ret += sta;
	 return(ret);
}


void inibuf(unsigned bl[MAXC+1])
{
	unsigned i;
	for(i=1;i<=MAXC;i++)
		bl[i] = i;
}


void genrcode(tcode retc)
{
	unsigned i,j,nr;
	 unsigned bl[MAXC+1];
	 inibuf(bl);
	 for( i=1;i<=MAXC;i++)
	 {
		 nr = nrand(1,MAXC+1-i)
			   retc[i] = bl[nr];
			   for(j=nr;j<MAXC;j++)
		 bl[j]=bl[j+1];
	 }
}


float fitfun(tcode co)
{
	float addall = 0;
	unsigned i;
	addall += distance(location[0], location[co[1]]);
	addall += distance(location[co[MAXC]], location[0]);
	for(i = 1; i< MAXC; i++)
	{
		addall += distance(location[co[i]], location[co[i+1]]);
	}
	return(addall);
}

void printcode(tcode co)
{
	unsigned i;
	for(i=1;i<=MAXC;i++)
	{	printf("%d ",co[i]);    }
	printf("适合的值为： %6.2f," fitfun(co));
	printf(" \n ");
}

float distance(tcode p1, tcoord p2)
{
	float dis;
	dis = sqrt((p1.x-p2.x)*(p1.x-p2.x)+(p1.y-p2.y)*(p1.y-p2.y));
	return(dis);	
}


void inipop(tpop pop)
{
	unsigned i;
	for(i=1;i<=POPSIZE;i++)
		genrcode(pop[i]);
}

void sortpop(tpop pop)
{
	unsigned i,j,k,temp;
	for(i=1;i<=POPSIZE-1;i++)
		for(j=i+1;j<=POPSIZE;j++)
		{
			if(fitfun(pop[i]) > fitfun(pop[j]))
				for(k=1;k<=MAXC;k++)
				{
					temp = pop[i][k];
					pop[i][k]=pop[j][k];
					pop[j][k]=temp;
				}
		}
}


void repro(tpop pf, tpop prep)
{
	unsigned i,j,k,choose;
	double q=1,a=0.9,fadd=0,eval[POPSIZE+1]
	,roulette[POPSIZE+1],r;
	for(i =1; i<=POPSIZE;i++)
	{
		q*=a;
		fadd+=q;
	}
	q=1;
	eval[0]=0;
	for(i=1;i<=POPSIZE;i++)
	{
		roulette[i]=0;
		for(j=0; j<=i;j++)
		roulette[i] += eval[j];
	}
	for(i=1;i<=POPSIZE;i++)
	{
		r = ran();
		   for(j=1;j<=POPSIZE;j++)
		   if(r<=roulette[j])
		   {
			   choose = j;
			   break;
		   }
		 copycode(pf[choose],prep[i]);
		 
	}
}
//上面的是选择出Popszie个体

void cross (tpop pop)
{
	unsigned i ,j, cpos = MAXC-1,c1,c2;
	for(i=1;i<=POPSIZE;i += 2)
	{
		if(pop[i][cpos] == pop[i+1][cpos])     continue;
		else 
		{
			c1 = pop[i][cpos];
			c2 = pop[i+1][cpos];
			for(j =1;j<=MAXC;j++)
			if(pop[i][j] == c2)
			{
				pop[i][j] = c1;
				pop[i][cros] = c2;
				break;
			}
			for(j=1;j<=MAXC;j++)
			if(pop[i+1][j] == c1)
			{
				pop[i+1][j] = c2;
				pop[i+1][cpos] = c1;
				break;
			}
		}
	}
}


void mutation(tpop pop)
{
	unsigned ii, p1,p2,temp;
	double r,a = 0.1;
	for(ii=1;ii<=POPSIZE;ii++)
	{
		r = ran();
		if(r<a)
		{
			do{
				p1 = nrand(1,MAXC);
				p2 = nrand(1,MAXC);
			}while(p1==p2);
			temp = pop[ii][p1];
			pop[ii][p1] = pop[ii][p2];
			pop[ii][p2] = temp;
		}
	}
}


void copypop(tpop po,tpop pd)
{
	unsigned i,j;
	for(i=1;i<=POPSIZE;i++)
		copycode(po[i],pd[i]);
}


void copycode(tcode co, tcode cd)
{
	unsigned j;
	for(j=1;j<=MAXC;j++)
		cd[j] = co[j];
}












