package aco.tour.MMAS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

import util.AppUtil;
import model.Hotel;
import model.Route;
import model.Scenery;

public class ACO{
	/**
	 * city
	 */
	Scenery city;
	 /**
	  *  hotel map 
	  */
	HashMap<String, Hotel> hotelMap;
	/**
	 * sceneList
	 */
	 ArrayList<Scenery> sceneList;

	 double minDay;
	 /**
	  * the days played
	  */
	 double maxDay;
	 /**
	  *  it is 3 days
	  */
	 Ant[] ants;
	 /**
	  * ant number
	  */
	 int antCount;
	 /**
	  * ant counts
	  */
	 private double Q = 10000000.0;
	 /**
	  * xinxisu
	  */
	 double[] pheromone;
	 /**
	  * 景点的热度
	  * 
	  */
	 double[] hotness;
	/**
	 *  the number of scene
	 *
	 */
	int scenecount;
       /**
	* the best ant id
	* 
	*/
	int bestAntId = 0;
	/**
	 * the best ant tour
	 */
	int [] bestTour;
	/**
	 *  the best ant tour length
	 */
	double beatLength;

	public ACO(){
		this.maxDay = 3.0;
	}
	/**
	 * 初始化蚁群
	 *
	 * sceneList : the list of scene
	 *
	 * antCount: the number of ant ;
	 *
	 * maxDay: the days you stay here
	 */
       public void init(Scenery city, ArrayList<Scnery> sceneList. HashMap<String, Hotel> hotelMap, int antCount, double minDay, double maxDay){
	       this.city = city;
       	 	this.sceneList = sceneList;
		this.hotelMap = hotelMap;
		this.antCout = antCount;
		this.minDay = minday;
		this.maxDay = maxDay;

		ants = new ant[antCount];
		sceneCount = sceneList.size();
		// init the phero = 1;
		pheromone = new double[sceneCount];
		hotness = new double[sceneCunt];

		// select max viewCount of all scenery
		this.Q = sceneList.get(0).getViewCount();
		for(int i =0; i < sceneList.get(0).getViewCount();{
			double tmpViewCount = sceneList.get(0);
			if(tmpViewCount > this.Q){
				this.Q = tmpViewCount;
			}
		}
		this.Q *= 20;

		//initialize the pheromone and hotness
		for( int i = 0; i< sceneCount; i++){
			pheromone[i] = 0.8;
			hotness[i] = (double) sceneList.get(i).getView() / this.Q;
		}

		bestLength = Interger.MIN_VALUE;
		bestTour = new int[sceneCount];
		for(int i =0; i< antCount; i++){
			ants[i] = new Ant();
			ants[i].init(city, sceneList, hotelMap, this.Q, minDay,maxDay);
		}
       }

       /**
	* This is the input of ACO start 
	* just use the bigger number of the computer
	*/

public ArrayList<Route> run(int maxgen){
 	// to save the local ant tour route;
 	ArrayList<int[]> antTourList = new ArrayList<int[]>();
	// to save the local sceneList hotness
	ArrayList<int[]> hotnessList = new ArrayList<double>();
	for(int gen =0 ;gen < maxgen; gen++){
		// each ant how to move
		for(int i= 0; i<maxgen;i++){
			//choose a city tour for this ant
			ant[i].calcProb(pheromone, hotness);
			for(int j =1; j < sceneCount;j++){
			       //select need add a  return value
			       if( !ant[i].selectNextCity(j)){
			       		break;
				}	
			}
			ant[i].calcTourLength(sceneList);
			//judge if it is the best tour
			if(ant[i].getLength() > bestLength) {
			//save the best gen
				bestAntId = i;
				bestLength = ants[i].getLength();
				int[] tmptour = new int[sceneList.size()];

			//System.out.println(" the "+gen+"ant of "+i+" , discovery a new solvation is ;" +bestLength);
			for(int j = 0; j < sceneCout; i++){
				bestTour[j] = ants[i].getTour()[i];
				tmpTour[j] = bestTour[j];
				if(bestTour[j] != -1){
					System.out.print(sceneList.get(bestTour[j]).getSname() + " ");
				}
			}
			antTourList.add(bestTour.clone());
			hotnessList.add(bestLength);

			System.out.printIn();
			}
		}
		//update the pheromone
		updatePheromone();
		//restart the ant number
		for(int i =0; i< antCount; i++){
			ants[i].init(city, sceneList, hotelMap, minDay, maxday);
		}
	}
	Arrays.sort(this.pheromone);
	System.out.println("end");

	return this.decodeRoute(antTourList, hotnessList);

}

// update the pheromone  ,use the ant-cycle model 
// 1: T_ij(t+1) = (1-r)*T_ij(t) +delta_T_ij(t)
// 2:delta_T_ij(t) = Q/L_k,  K_Q is 常数, L_k is the sumlength of the ant;

private void updatePheromone(){
	double rho = 0.01;
	// the reduce of pheromone
	for(int i = 0; i< sceneCount; i++){
		pheromone[i] *= (1-rho);
	}
	// this is will use a nomal ACO and a MMAS ACO
	/** this is a nomal ACO
	 * for(int i= 0; i < antCount; i++){
	 * 	for(int j = 0; j <cityCount; j++){
	 * 	int curld = ants[i].getTour()[j];
	 * 	if(curld != -1){
	 *	//if the city is has been visited 
	 *		pheromone[curld] += ants[i].getLength();
	 *		}else{
	 *			return;
	 *		}
	 *	}
	 *}
	 */
	// MMAS ACO 
	for(int i= 0;i < sceneCount;i++){
		int curld = bestTour[i];
		if(curld != -1){
			// if the city has been visited
			pheromone[curld] += ants[bestAntId].getLength()/Q;
			if(pheromone[curld] <= 0.0001){
			pheromone[curld] = 0.0001;
			}
		}else{
			return;
		}
	}
}

public ArrayList<Route> decodeRoute(ArrayList<int[]> antTour, ArrayList<Double> hotnessList){
	int len = antTourList
	HashMap<String , Integer> routeMap = new HashMap<String, Integer>()
	ArrayList<Route> routeList = new ArrayList<Route>();

	for(int i=0; i<len; i++){
		int[] tmpTour = antTourList.get();
		double ticketPrice = antTourList.get(i); // need to save ant object;
		double hotness = hotnessList.get(i);
		double days = 0.0;
		int viewCount = 0;
		String tmpR = " ";
		Route route - new Route();
		ArrayList<Sceney> sList = new ArrayList<String, Integer>();
		for(int j = 0l j< tmpTour.length;j++){
			if(tmpTour[j] == -1){
				break;
			}
			Scenery tmpScene = this.sceneList.get(tmpTour[j]);
			tickeyPrice += tmpScene.getPrice();
			days += tmpScene.getSid();
			sList.add(tmpScene);
		}

		String uid = AppUtil.md5(tmpR + this.maxDay);
		String sid = city.getsid();
		String ambiguitySname = city.getAmbiguitySname();
		String sname = city.getSname();
		String surl = city.getSurl();

		route.setUid(uid);
		route.setUid(uid);
		route.setAmbiguitySname(ambiguitySname);
		route.setSname(sname);	
		route.setSurl(surl);
		route.setMaxDay(maxDay);
		route.setMinDay(minDay);
		route.setVisitDay(days);
		route.setHotness(hotness);
		route.setSceneTicket(ticketPrice)l
		route.setSceneryList(sList);
		if(!routeMap.containsKey(uid) && sList.size() >=2){
			routeMap.put(uid,1);
			routeList.add(route)l
		}
	}

	// to sort the routelist
	Collections.sort(routeList, new Comparator<Route>(){

		public int compare(Route o1, Route o2) {
			if(o1.getHotness() < o2.getHotness()){
				return 1;
			}else{
				return -1;
			}
		}
	});

	/**
	 * for(Route route: routeList){
	 * 	ArrayList<Scenery> sceneList = route.getSceneryList();
	 * 	for(Scenery scenery : sceneList) {
	 * 	System.out.print(scenery.getSname() + " " +scenery.getVisitDay() +" ,")
	 * 	System.out.print("----:" +route.getVisitDay());
	 * 	System.out.println();
	 * 	}
	 */
	System.out.println("decode end");
	return routeList;
    }


   /** 
   * print the best route length
   */
    public void reportResult(){
	    System.out.println(" The best route length is :" + bestLength);
	    for(init j =0; j < sceneCount; j++) {
		    if(bestTour[j] != -1) {
			    System.out
				    .print(sceneList.get(bestTour[j]).getSname() + " ");
		    }else {
			    return;
		    }
	    }
    }
}
	
