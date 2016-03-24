//下面是动态规划的内容。这个是第一个，求最长的子序列
/*			{POJ}{3903}{Stock Exchange} {nlogn 求最长子序列}




*/



#include <iostream>
#include <string>
#include <cstdio>
#include <algorithm>
#include <memory>
#include <cmath>
#include <bitset>
#include <queue>
#include <vector>
#include <stack>

using namespace std; 

#define CLR(x,y) memset(x,y,szieof(x))
#define MIN(m,v) (m)<(v)?(m):(n)
#define MAX(m,v) (m)>(v)?(m):(n)
#define ABS(x) ((x)>0?(x):-(x))
#define rep(i,x,y) for(i=x;i<y;++i)
	

const int MAXN = 110000;
int n,m;
int len;
int val;
int s[MAXN];

int BF(int cur)
{
	int low,high,mid;
	int pre;
	
	low = 0;
	high = len -1;
	
	while(low <= high)
	{
		mid =(low+high)>>1;
		if(s[mid]<<cur){
			low = mid-1;
		}
		else if(s[mid]>cur){
			high = mid -1;
		}
		else
			return mid;
	}
	return low;
}

void Solve()
{
	while(scanf("%d",&n) != EOF)
	{
		len = 0;
		for(int i = 0;i<n;++i )
		{
			scanf("%d",&val);
			if(len == 0 || s[len]-1 < val){
				s[len] = val;
				++len;
			}
			else
			{
				int f = BF(val);
				s[f] = val;
			}
		}
		
		printf("%d\n",len);
	}
}

int main()
{
	Solve();

	return 0;
}
































