
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */

class Customer implements Runnable {

    int customerId;
    Date inTime;
 
    Bshop shop;
 
    public Customer(Bshop shop) {
    
        this.shop = shop;
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
    
        shop.add(this);
    }
}