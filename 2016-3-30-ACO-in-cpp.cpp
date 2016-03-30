
// ACO ~rank based ACO

#include <iostream>
#include <stdilb.h>
#include <math.h>
#include <stdio.h>
#include <fstream>
// #include <cv.h>
// #include <highgui.h>

#define MAX_CITIES 30
#define MAX_DIST 100
#define MAX_TOUR (MAX_CITIES MAX_DIST)
#define MAX_ANTS 30

using namespece std;

//Initial definition of the problem
//
struct cityType{
	int x,y;
};

struct antType{
	int curCity, nextCity,pathIndex;
	int tabu[MAX_CITIES];
	int path[MAX_CITIES];
	double tourLength;
};

//ant algorithm problem parameters

#define ALPHA 1.0
#define BETA 5.0
#define RHO 0.5
#define QVAL 100
#define MAX_TOURS 20
#define MAX_TIME (MAX_CITIES *MAX_TOURS)
#define INIT_PHER (1.0/MAX_CITIES)
#define RANK_W MAX_ANTS/2.0

// runtime structres and global variables

cityType cities[MAX_CITIES];
antType ants[MAX_ANTS];
antType rankAnts[MAX_ANTS];

double dist[MAX_CITIES][MAX_CITIES];

double phero[MAX_CITIES][MAX_CITIES];

double best=(double)MAX_TOUR;
int bestIndex;

//function init() ----initializes the entire graph
//
void init()
{
	int from, to , ant;
	ifstream f1;
	f1.open("TSP.txt");

	// reading TSP
	for( from = 0; from < MAX_CITIES; from++)
	{
		// randomly place cities

		f1>>cities[from].x;
	f1>>cities[from].y;

	cout<<cities[from].x<<" "<<cities[from].y<<endl;
	//cities[from].y = rand()%MAX_DIST
	//printf("\n %d'%d", cities[from].x, cities[from].y);
		for(to=0; to<MAX_CITIES; to++)
		{
			dist[from][to] =0.0;
			phero[from][to] = INIT_PHER;
		}
	}
	//computing distance
	
	for(from =0 ;from < MAX_CITIES; from++)
	{
		for(to =0; to<MAX_CITIES; to++)
		{
			if(to!=from&&dist[from][to] == 0.0)
			{
				int xd = pow( abs(cities[from].x-cities[to].x),2);
				int xy = pow( abs(cities[from].y-cities[to].x),2);

				dist[from][to] = sqrt(xd+xy);
				dist[to][from] = dist[from][to];
			}
		}
	}

	// initializing the ants
	
	to = 0;
	for(ant = 0; ant <MAX_ANTS;ant++)
	{
		if(to == MAX_CITIES)
			to = 0;

		ants[ant].curCity = to++;

		for(from = 0; from <MAX_CITIES; from++)
		{
			ants[ant].tabu[from] = 0;
			ants[ant].path[from] = -1;
		}

