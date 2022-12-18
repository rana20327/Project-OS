package real_world_application;
import static java.util.concurrent.TimeUnit.SECONDS;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CallCenter_service {
	
	public static void main (String a[]) throws InterruptedException {	
		
		int noOfAgent, customerId=1, noOfCustomers=20;	//initializing the number of Agent and customers
            try (Scanner sc = new Scanner(System.in)) {
                System.out.println("Enter the number of Agent(M):");			//input M barbers
                noOfAgent=sc.nextInt();
                

ExecutorService exec = Executors.newFixedThreadPool(6);

callCenter center = new callCenter(noOfAgent);				//initializing the barber shop with the number of barbers
Random r = new Random(); //0 to 1 										//a random number to calculate delays for customer arrivals and haircut

System.out.println("\nCallCenter shop opened with "
        +noOfAgent+" Agent(s)\n");

long startTime  = System.currentTimeMillis();					//start time of program

for(int i=1; i<=noOfAgent;i++) {								//generating the specified number of threads for barber
    
   callCenter_Agent agent = new callCenter_Agent (center, i);
    Thread thagent = new Thread(agent);//Allocates a new Thread object by taking runnable target.
    exec.execute(thagent);
}

for(int i=0;i<noOfCustomers;i++) {	//to not run forever							//customer generator; generating customer threads
    
    Customer_of_CallCenter customer = new Customer_of_CallCenter(center);
    customer.setInTime(new Date());
    Thread thcustomer = new Thread(customer);
    customer.setcustomerId(customerId++);
    exec.execute(thcustomer);
    
    try {
        
        double val = r.nextGaussian() * 2000 + 2000;			//'r':object of Random class, nextGaussian() generates a number with mean 2000 and
        int millisDelay = Math.abs((int) Math.round(val));		//standard deviation as 2000, thus customers arrive at mean of 2000 milliseconds
        Thread.sleep(millisDelay);								//and standard deviation of 2000 milliseconds
    }
    catch(InterruptedException iex) {
        
        iex.printStackTrace();
    }
    
}

exec.shutdown();												//shuts down the executor service and frees all the resources
exec.awaitTermination(6, SECONDS);								//waits for 12 seconds until all the threads finish their execution

long elapsedTime = System.currentTimeMillis() - startTime;		//to calculate the end time of program

System.out.println("\ncallCenter closed");
System.out.println("\nTotal time elapsed in seconds"
        + " for serving "+noOfCustomers+" customers by "
        +noOfAgent+" Agents with "
        +TimeUnit.MILLISECONDS
                .toSeconds(elapsedTime));
System.out.println("\nTotal customers: "+noOfCustomers+
        "\nTotal customers served: "+center.getTotalHairCuts()
        +"\nTotal customers lost: "+center.getCustomerLost());
            } //input M barbers
    }
}
 