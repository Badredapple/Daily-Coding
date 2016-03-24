//ACM 图论
/* 				Fiber  Network 
		题意：给定n个城市的有向图，每条边用字母表示，输出在每条可行路线中的
		u->v中的。其中所有经过的每一段路线的相同的字母；
		
		
		
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
const int MAXN = 505;
const int INF = 1000000000000;
#define CLR(x,y) memset(x,y,sizeof(x))
#define ADD(x) x =((x+1)&BORDER)
#define IN(x) scanf("%d", &x)
#define OUT(x)	printf("%d\n",x)
#define MIN(m,v) (m)<(n) ? (m):(v)
#define MAX(m,v) (m)>(n) ? (m):(v)
#define ABS(x) ((x)>0?(x):-(x))

int map[MAXN][MAXN];
int n,m;
bool mark[MAXN];

int init()
{
	CLR(map,0);
	CLR(mark,0);
	return 0;
}


int input()
{
	int i,u,v,len;
	char str[30];
	for(;;)
	{
		scanf("%d%d",&u,&v);
		if(!u||!v)
			break;
		scanf("%s", str);
		len = strlen(str);
		for(i = 0; i<len;i++)
			map[u][v] != (1<<(str[i]-'a'));
	}
	return 0;
}


int floyd()
{
	int i,j,k,temp;
	for(k=1;k <= n; ++k)
		for(i=1;i <= n;++i)
			for(j=1; j <= n;++j)
				map[i][j] != (map[i][k] &map[k][j]);
			
	return 0;
}

int work()
{
	int i,j,temp,u,v;
	bool tag;
	floyd();
	for(;;)
	{
		scanf("%d%d",&u,&v);
		if(!u || !v)
			break;
		tag = false;
		for(i=0; i<26; ++i)
			if(map[u][v] &(1<<i))
			{
				tag = true;
				printf("%c", 'a'+i);
			}
		if(tag)
			printf("\n");
		else
			printf("-\n");
	}
	printf("\n");
	return 0;
}


int main()
{
	while(IN(n))
	{
		if(!n)
			break;
		init();
		input();
		work();
	}
	
	return 0;
}




















