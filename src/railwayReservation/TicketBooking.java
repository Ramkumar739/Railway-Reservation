package railwayReservation;

import java.util.*;

public class TicketBooking {
	//63 berths(upper,lower,middle) + (18 RAC passengers)
	//10 waiting list tickets -> 21 L, 21 M,21 U,18 RAC,10 WL
	static int availableLOwerBerths=21; //normaly 21
	static int availableMiddleBerths=21; //normaly 21
	static int availableUpperBerths=21; //normaly 21
	static int availableRacTickets=18; //normaly 21
	static int availableWaitingList=10; //normaly 21
	
	static Queue<Integer> waitingList=new LinkedList<>();//queue of wl passenger
	static Queue<Integer> racList=new LinkedList<>(); //queue of rac passenger
	static List<Integer> bookedTicketList=new ArrayList<>(); //list of bookedticket passenger
	
	static List<Integer>lowerBerthsPositions =new ArrayList<>(Arrays.asList(21));// normaly 1,2,3...21
	static List<Integer>middleBerthsPositions =new ArrayList<>(Arrays.asList(21));// normaly 1,2,3...21
	static List<Integer>upperBerthsPositions =new ArrayList<>(Arrays.asList(21));// normaly 1,2,3...21
	static List<Integer>racPositions =new ArrayList<>(Arrays.asList(18));// normaly 1,2,3...18
	static List<Integer>waitingListPositions =new ArrayList<>(Arrays.asList(18));// normaly 1,2,3...10
	
	static Map<Integer,Passenger> passengers=new HashMap<>();
	
	//Book Ticket
	public void bookTicket(Passenger p,int berthInfo , String allotedBerth) {
		p.number= berthInfo;
		p.alloted=allotedBerth;
		passengers.put(p.passengerId,p);
		bookedTicketList.add(p.passengerId);
		System.out.println("---Booked SUccessfully---");
		
	}
	// adding to RAC
	public void addToRAC(Passenger p,int racInfo,String allotedRAC) {
		p.number=racInfo;
		p.alloted=allotedRAC;
		passengers.put(p.passengerId,p);
		racList.add(p.passengerId);
		availableRacTickets--;
		racPositions.remove(0);
		System.out.println("---Added to RAC Successfully---");
		
	}
	// adding to WL
	public void addToWaitingList(Passenger p, int waitingListInfo,String allotedWL) {
		p.number=waitingListInfo;
		p.alloted=allotedWL;
		passengers.put(p.passengerId,p);
		waitingList.add(p.passengerId);
		availableWaitingList--;
		waitingListPositions.remove(0);
		System.out.println("---added to waiting list Successfully---");
		
	}
	//cancel ticket
	public void cancelTicket(int passengerId) {
		    Passenger p = passengers.get(passengerId);
	        passengers.remove(passengerId);
	        passengers.remove(Integer.valueOf(passengerId));
	        //remove the booked ticket from the list
	        bookedTicketList.remove(passengerId);
	        bookedTicketList.remove(Integer.valueOf(passengerId));

	        //take the booked position which is now free
	        int positionBooked = p.number;
	        System.out.println("---------------cancelled Successfully");
	        //add the free position to the correspoding type of list (either L,M,U)
	        if(p.alloted.equals("L")) 
	        { 
	          availableLOwerBerths++;
	          lowerBerthsPositions.add(positionBooked);
	        }
	        else if(p.alloted.equals("M"))
	        { 
	        	availableMiddleBerths++;
	          middleBerthsPositions.add(positionBooked);
	        }
	        else if(p.alloted.equals("U"))
	        { 
	          availableUpperBerths++;
	          upperBerthsPositions.add(positionBooked);
	        }
	      //check if any RAC is there
	        if(racList.size() > 0)
	        {
	            //take passenger from RAC and increase the free space in RAC list and increase available RAC tickets
	            Passenger passengerFromRAC = passengers.get(racList.poll());
	            int positionRac = passengerFromRAC.number;
	            racPositions.add(positionRac);
	            racList.remove(passengerFromRAC.passengerId);
	            racList.remove(Integer.valueOf(passengerFromRAC.passengerId));
	            availableRacTickets++;

	            //check if any WL is there
	            if(waitingList.size()>0)
	            {
	                //take the passenger from WL and add them to RAC , increase the free space in waiting list and 
	                //increase available WL and decrease available RAC by 1
	                Passenger passengerFromWaitingList = passengers.get(waitingList.poll());
	                int positionWL = passengerFromWaitingList.number;
	                waitingListPositions.add(positionWL);
	                waitingList.remove(passengerFromWaitingList.passengerId);
	                waitingList.remove(Integer.valueOf(passengerFromWaitingList.passengerId));

	                passengerFromWaitingList.number = racPositions.get(0);
	                passengerFromWaitingList.alloted = "RAC";
	                racPositions.remove(0);
	                racList.add(passengerFromWaitingList.passengerId);
	                
	                availableWaitingList++;
	                availableRacTickets--;
	            }
	            // now we have a passenger from RAc to whom we can book a ticket, 
	            //so book the cancelled ticket to the RAC passenger
	            Main.bookTicket(passengerFromRAC);
	        }
	    
	    }
	    //print all available seats
	    public void printAvailable()
	    {
	        System.out.println("Available Lower Berths "  + availableLOwerBerths);
	        System.out.println("Available Middle Berths "  + availableMiddleBerths);
	        System.out.println("Available Upper Berths "  + availableUpperBerths);
	        System.out.println("Availabel RACs " + availableRacTickets);
	        System.out.println("Available Waiting List " + availableWaitingList);
	        System.out.println("--------------------------");
	    }
	    //print all occupied passengers from all types including WL
	    public void printPassengers()
	    {
	        if(passengers.size() == 0)
	        {
	            System.out.println("No details of passengers");
	            return;
	        }
	        for(Passenger p : passengers.values())
	        {
	            System.out.println("PASSENGER ID " + p.passengerId );
	            System.out.println(" Name " + p.name );
	            System.out.println(" Age " + p.age );
	            System.out.println(" Status " + p.number + p.alloted);
	            System.out.println("--------------------------");
	      }    
	    }
	}
