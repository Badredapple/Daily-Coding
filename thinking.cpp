
// 新的思路，传递闭包+warshell算法+十字链表

// 找机会写算法
#include<stdio.h>
#include<iostream>
using namespace std;
#define INF 9999999
void floydWarshell (int graph[200][200],int n);
void floydWarshell (int graph[200][200],int n)
{
    int dist[200][200], i, j, k,a,q,b;

    for (i = 0; i < n; i++)
        for (j = 0; j < n; j++)
            dist[i][j] = graph[i][j];

    for (k = 0; k < n; k++)
    {
        for (i = 0; i < n; i++)
        {
            for (j = 0; j < n; j++)
            {
                if (dist[i][k] + dist[k][j] < dist[i][j])
                    dist[i][j] = dist[i][k] + dist[k][j];
            }
        }
    }
    cin>>q;
        while(q--)
        {
            cin>>a>>b;
            if(a==b)cout<<"0\n";
            else
            cout<<dist[a][b]<<endl;
        }
}
int main()
{
    int t,n,q,i,j;
    char c;
    int graph[200][200];
    cin>>t;
    while(t--)
    {
        cin>>n;
        for(i=0;i<n;i++)
        {
            for(j=0;j<n;j++)
            {
                cin>>c;
                if(c=='Y')graph[i][j]=1;
                else graph[i][j]=INF;
            }
        }
        floydWarshell(graph,n);
    }
    return 0;
}

/*上面是一个很明确的warshell算法，现在我们来分析下什么是warshell算法，
 * 原理很简单，就是给个中间值k然后求dist[i,k]+dist[k,j]是不是小于dist[i,j]
 * 是将dist[i,j]用后面的值赋值，好了，问题在于哪里呢？
 * 首先要有必过节点，怎么考虑？  
 * 首先我们来考虑，怎么在warshell算法里面加入有向图
 * 其次我们来考虑，怎么在warshell算法里面加入必过点；
 * 另外，warshell算法只能输出最短路径，怎么考虑输出最短路径
 * /
