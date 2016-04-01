package aco.tour.AS;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import model.Hotel;
import model.Scenery;
//This Section you could look at ACO.java
public class Ant {
	
	Scenery city;
	HashMap<String, Hotel> hotelMap;
	ArrayList<Scenery> sceneryList;

	private int [] tour;

	private double Q;
	/**
	 * save if has visited this node , 1 means has visited
	 */
	private int[] tabu;

	private double[] p;

	private double pSum = 0.0;

	private double length;

	double maxDay;
	double minDay;

	double curVisitDay;

	private int count;

	private double alpha = 1.0;

	private double beta = 2.0;

	public int[] getTour(){
		return tour;
	}

	public Ant(){

	}
	// init the ant beginning path
	//
	// count is the number of the cities
	//
	// maxDay is the max number the days stayed
	public void init(Scenery city, ArrayList<Scenery> sceneList, 
			HashMap<String, Hotel> hotelMap, double Q, double minDay, double maxDay) {
		this.city = city;
		this.sceneryList = hotelMap;
		this.count = sceneList.size();
		this.Q = Q;
		this.minDay = minDay;
		this.maxday = maxDay;
		this.pSum = 0.0;
		this.p = new double[count];
		this.tabu = new int[count];
		this.tour = new int[count];
		for(int i = 0; i < count; i++) {
			tabu[i] = 0;
			tour[i] = -1;
			p[i] = 0.0;
		}
		int random = new Random(System.currentTimeMillis()).nextInt(cout);
		p[random] = 0.0;
		tabu[random] = 1;
		tour[0] = random;
		curVisitDay = sceneList.get(random).getVisitDay();
	}

	/**
	 * 计算蚂蚁选择景点的概率
	 *
	 * pheromone  信息素
	 *
	 * hotness    热度
	 */
	public void calcProb(double[] pheromone, double[] hotness) {
		this.pSum = 0.0;
		double sum = 0.0;
		//这个是概率的总和
		for(int i =0; i< count; i++) {
			if(tabu[i] == 0) {
				sum += Math.pow(pheromone[i], this.alpha)
					*(Math.pow(hotness[i], this.beta));
			}
		}
		
		for(int i==0; i < count; i++) {
			if(tabu[i] == 1) {
				p[i] = 0.0;
			} else {
				p[i] = Math.pow(pheromone[i], this.alpha)
					*(Math.pow(hotness[i], this.beta)) / sum;
				pSum += p[i];
			}
		}
	}
