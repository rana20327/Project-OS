/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package real_world_application;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author dell
 */

class callCenter_Agent implements Runnable {										// initializing the barber

    callCenter center;
    int AgentId;
 
    public callCenter_Agent (callCenter center, int AgentId) {
    
        this.center = center;
        this.AgentId = AgentId;
    }
    
    public void run() {
    
        while(true) {
        
            center.cutHair(AgentId);
        }
    }
}
