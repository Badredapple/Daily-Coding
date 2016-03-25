
// �µ�˼·�����ݱհ�+warshell�㷨+ʮ������

// �һ���д�㷨
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

/*������һ������ȷ��warshell�㷨������������������ʲô��warshell�㷨��
 * ԭ��ܼ򵥣����Ǹ����м�ֵkȻ����dist[i,k]+dist[k,j]�ǲ���С��dist[i,j]
 * �ǽ�dist[i,j]�ú����ֵ��ֵ�����ˣ��������������أ�
 * ����Ҫ�бع��ڵ㣬��ô���ǣ�  
 * �������������ǣ���ô��warshell�㷨�����������ͼ
 * ������������ǣ���ô��warshell�㷨�������ع��㣻
 * ���⣬warshell�㷨ֻ��������·������ô����������·��
 * /
