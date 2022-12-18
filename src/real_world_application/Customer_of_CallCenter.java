
package real_world_application;
import java.util.Date;



class Customer_of_CallCenter implements Runnable {

    int customerId;
    Date inTime;
 
    callCenter center;
 
    public Customer_of_CallCenter(callCenter center) {
    
        this.center = center;
    }

 
 
    public int getCustomerId() {										//getter and setter methods
        return customerId;
    }
 
    public Date getInTime() {
        return inTime;
    }
 
    public void setcustomerId(int customerId) {
        this.customerId = customerId;
    }
 
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
 
  
    public void run() {													//customer thread goes to the shop for the haircut
    
        goForHairCut();
    }
    private synchronized void goForHairCut() {							//customer is added to the list
    
        center.add(this);
    }
}
