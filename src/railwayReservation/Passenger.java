package railwayReservation;

public class Passenger {
	
	  static int id=1; //static variable to give id for every new passenger
	  int passengerId; //id of passenger created automatically
	  String name; 
	  int age;
	  String berthPreference; //U or L or M 
	  String alloted; //alloted type(L,U,M,RAC,WL)
	  int number; // seat number
	
	public Passenger(String name,int age,String berthPreference) {
		 this.passengerId=++id;
		 this.name=name;
		 this.age=age;
		 this.berthPreference=berthPreference;
		 alloted="";
		 number=-1;
	 }

	
	
}
