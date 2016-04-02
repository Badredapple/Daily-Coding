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
	/**
	 *通过信息素和距离计算赌注概率，选择下一个城市
	 * index
	 *  the next cityId is in tour[]
	 *  if it is suited in every localstaion  return true; else return false;
	 */
      public boolean selectNextCity(int index) {
      		int select = getRandomCity(p);
  		double	day = this.sceneryList.get(select).getVisitDay();
		if (this.curVisitDay + day > maxDay) {
			return false;
		}
		this.curVisitDay += day;
		tour[index] = select;
		tabu[select] = 1;
		pSum -= p[select];
		p[select] = 0.0;
		return true;
      }

      
      // 
      // 使用轮盘赌注来选择城市；

      private int getRandomCity(double[] p) {
	      double selectP = new Random(System.currentTimeMills()).nextDouble() * pSum;
	      double sumSel = 0.0;
	      for(int i = 0; i< count; i++) {
		      sumSel += p[i];
		      if(sumSel > selectP)
			      return i;
	      }
	      return -1;
      }

      // The sum distance of the ant has arrived;
      public void calcTourLength(ArrayList<Scenery> sceneList) {
	      length = 0;
	      double tickerPice = 0.0;
	      double viewCount = 0.0;
	      double days = 0.0;
	      for( int i =0; i<count; i++) {
		      int tourId = tour[i];
		      if(tourId == -1) {
			      break;
		      }
		      Screnery scene = sceneList.get(tourId);
		      viewCount += scene.getViewCount();
		      ticketPrice += scene.getPrice();
		      days += scene.getVisitDay();
	      }

	      ArrayList<Hotel> curHotels = new ArrayList<Hotel>();

	      if(days <= minDay || days > maxDay) {
		      return;
	      }

	      Collections.sort(curHotels);
	      double hotelPrice = 0.0;
	      // 判断酒店的个数是否大于需要入住的天数，如果大于则按照入住的天数计算价格，如果小于则计算所有酒店的价格，剩余的天数按照最低的价格计算
	      //
	      String hotelIds = " ";
	      int len = Math.min(curHotels.size(), (int) minDay);
	      if (len != 0) {
		      for(int i = 0; i< len;i++) {
			      hotelPrice += curHotels.get(i).getPrice();
			      hotelIds += curHotels.get(i).getSid() + ",";
		      }
		      int span = (int) (minDay - curHotels.size());
		      for( int i = 0;i < span; i++) {
			      hotelPrice += curHotels.get(0).getPrice();
			      hotelIds += curHotels.get(0).getSid() +" ,";
		      }
	      } else {
		      for(int i = 0; i< (int) minDay; i++) {
			      hotelPrice += 80.0;
		      }
	      }

	      if(!hotelIds.equals(" ")) {
		      hotelIds = hotelIds.substring(0, hotelIds.length() -1);
		      double price = hotelPrice + ticketPrice;
		      double rho = 0.9;
		   // double fx = (1.0 - rho) * Math.pow(1.0/(10.0 +price), 1.0);
		   // double gx = rho *Math.pw(1.0 / (10.0 + this.Q - viewCount), 1.0/3.0);
		      double fx = (1 - rho)*(10000.0 /(price +10.0));
		      double gx = rho * Math.pow(viewCount, 1.0/3.0);
		      this.length = fx + gx;
		   //  this.length = viewCount;
		     }
      }


