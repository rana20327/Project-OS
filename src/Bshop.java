
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


class Bshop {

	private final AtomicInteger totalHairCuts = new AtomicInteger(0);
	private final AtomicInteger customersLost = new AtomicInteger(0);
	int nchair, noOfBarbers, availableBarbers;
    List<Customer> listCustomer;
    
    Random r = new Random();	 
    
    public Bshop(int noOfBarbers, int noOfChairs){
    
        this.nchair = noOfChairs;														//number of chairs in the waiting room
        listCustomer = new LinkedList<Customer>();						//list to store the arriving customers
        this.noOfBarbers = noOfBarbers;									//initializing the the total number of barbers
        availableBarbers = noOfBarbers;
    }
 
    public AtomicInteger getTotalHairCuts() {
    	
    	totalHairCuts.get();
    	return totalHairCuts;
    }
    
    public AtomicInteger getCustomerLost() {
    	
    	customersLost.get();
    	return customersLost;
    }
    
    public void cutHair(int barberId)
    {
        Customer customer;
        synchronized (listCustomer) {									//listCustomer is a shared resource so it has been synchronized to avoid any
        															 	//unexpected errors in the list when multiple threads access it
            while(listCustomer.size()==0) {
            
                System.out.println("\u001B[35m"+"\nBarber "+barberId+" is waiting "
                		+ "for the customer and sleeps in his chair"+"\u001B[0m");//purple
                
                try {
                
                    listCustomer.wait();								//barber sleeps if there are no customers in the shop
                }
                catch(InterruptedException iex) {
                
                    iex.printStackTrace();
                }
            }
            
            customer = (Customer)((LinkedList<?>)listCustomer).poll();	//takes the first customer from the head of the list for haircut
            
            System.out.println("\u001B[34m"+"Customer "+customer.getCustomerId()+
            		" finds the barber asleep and wakes up "
            		+ "the barber "+barberId+"\u001B[0m");//blue
        }
        
        int millisDelay=0;
                
        try {
        	
        	availableBarbers--; 										//decreases the count of the available barbers as one of them starts 
        																//cutting hair of the customer and the customer sleeps
            System.out.println("\u001B[32m"+"Barber "+barberId+" cutting hair of "+
            		customer.getCustomerId()+ " so customer sleeps"+"\u001B[0m");//green
        	
            double val = r.nextGaussian() * 2000 + 4000;				//time taken to cut the customer's hair has a mean of 4000 milliseconds and
        	millisDelay = Math.abs((int) Math.round(val));				//and standard deviation of 2000 milliseconds
        	Thread.sleep(millisDelay);
        	
        	System.out.println("\u001B[36m"+"\nCompleted Cutting hair of "+
        			customer.getCustomerId()+" by barber " + 
        			barberId +" in "+millisDelay+ " milliseconds."+"\u001B[0m");
        
        	totalHairCuts.incrementAndGet();
            															//exits through the door
            if(listCustomer.size()>0) {									
            	System.out.println("Barber "+barberId+					//barber finds a sleeping customer in the waiting room, wakes him up and
            			" wakes up a customer in the "					//and then goes to his chair and sleeps until a customer arrives
            			+ "waiting room");		
            }
            
            availableBarbers++;											//barber is available for haircut for the next customer
        }
        catch(InterruptedException iex) {
        
            iex.printStackTrace();
        }
        
    }
 
    public void add(Customer customer) {
    
        System.out.println("\nCustomer "+customer.getCustomerId()+
        		" enters through the entrance door in the the shop at "
        		+customer.getInTime());
 
        synchronized (listCustomer) {
        
         if (availableBarbers > 0) {							//If barber is available then the customer wakes up the barber and sits in
            															//the chair
            	((LinkedList<Customer>)listCustomer).offer(customer);
				listCustomer.notify();// Wakes up a single thread that is waiting on 
                                //this object's monitor
			}
            else {														//If barbers are busy and there are chairs in the waiting room then the customer
            															//sits on the chair in the waiting room
            	((LinkedList<Customer>)listCustomer).offer(customer);
                
            	System.out.println("\u001B[33m"+"All barber(s) are busy so "+
            			customer.getCustomerId()+
                		" takes a chair in the waiting room"+"\u001B[0m");
                 
                if(listCustomer.size()==1)
                    listCustomer.notify();
            }
        }
    }
}
