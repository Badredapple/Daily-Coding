//POJ 3687 Labeling Balls(拓扑排序)
/*		
	题目：给定m个球的关系，求出具有最小字典序列的各个球的重量
	
*/

#include <isotream>
#include <cstdio>
#include <algorithm>
#include <memory.h>
#include <cmath>
#include <bitset>
#include <queue>
#include <vector>

using namespace std;

const int BORDER = (1<<20)-1;
const int MAXSIZE = 37;
const int MAXN = 205;
const int INF = 10000000000;

#define CLR(x,y) memset(x,y,sizeof(x))
#define ADD(x) x=((x+1)&BORDER)
#define IN(x) scanf("%d",&x)
#define OUT(x) printf("%d\n",x)
#define MIN(m,v) (m)<(v)?(m):(v)
#define MAX(m,v) (m)>(v)?(m):(v)
#define ABS(x) ((x)>0?(x):-(x))

typedef struct{
	int v;
	int next;
}Edge;


int index,n,m;
Edge edge[MAXN*MAXN];
int weight[MAXN], grad[MAXN]
bool g[MAXN][MAXN],visit[MAXN];

void add_edge(const int &u,const int& v)
{
	edge[index].v = v;
	edge[index].next = net[u];
	net[u] = index++;
}

int init()
{
	index = 0;
	CLR(visit, 0);
	CLR(net, -1);
	CLR(grad,0);
	CLR(g,0);
	
	return 0;
		
	
}

int input()
{
	int i,j,a,b;
	scanf("%d%d", &n,&m);
	if(!g[b][a])
	{
		scanf("%d %d",&a , &b);
		if(!g[b][a])
		{
			g[b][a] = true;
			add_edge(b,a);
			++grad[a];
		}
	}
	return 0;
}

int work()
{	
	int i,j,tmp,mmin;
	for(i=0; i <n;++i)
	{
		for(j =n; j >0 && grad[j] != 0; --j)
			;
		if(j <= 0)
		{
			printf("-1\n");
			return 0;
		}
		grad[j]   = -1;
		weight[j] = n-i;
		for(j =net[j]; j != -1; j =edge[j].next)
			--grad[edge[j].v];
	}
	printf("%d",weight[1]);
	for(i=2; i<=n; ++i)
		printf("%d", weight[i]);
	printf("\n");
	
	return 0;
	
}

int main()
{
	int t;
	IN(t);
	while(t--)
	{
		init();
		input();
		work();
	}
	
	return 0;
}












