package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;

/**
 * The model represents the data that the app uses.
 * @author Roger Jaffe
 * @version 1.0
 */
public class Model implements MessageHandler {

  // Messaging system for the MVC
  private final Messenger mvcMessaging;

  // Model's data variables
  private int maxAge;
  private int minAge;
  private int age;

  /**
   * Model constructor: Create the data representation of the program
   * @param messages Messaging class instantiated by the Controller for 
   *   local messages between Model, View, and controller
   */
  public Model(Messenger messages) {
    mvcMessaging = messages;
  }
  
  /**
   * Initialize the model here and subscribe to any required messages
   */
  public void init() {
    mvcMessaging.subscribe("view:ageChanged", this);
    setAge(this);
  }
  
  @Override
  public void messageHandler(String messageName, Object messagePayload) {
    if (messagePayload != null) {
      System.out.println("MSG: received by model: "+messageName+" | "+messagePayload.toString());
    } else {
      System.out.println("MSG: received by model: "+messageName+" | No data sent");
    }
    MessagePayload payload = (MessagePayload)messagePayload;
    int field = payload.getField();
    int direction = payload.getDirection();
    
        setMinAge(calculateMin());
        setMaxAge(calculateMax());
        
        if(minAge > maxAge) { //If the person is too young to date, tell view not to let them date
            mvcMessaging.notify("model:noPermissableDating", null, true);
        }     
  }
  
    /** Calculates the maximum permissable age for dating
     * @return value of maximum permissable age
     */
  public int calculateMax() {
      int v = (age + 7) * 2;
      return v;
  }
  
     /** Calculates the minimum permissable age for dating
     * @return value of minimum permissable age
     */
  public int calculateMin() {
      if(age%2 != 0) { //so that age divided by two is a whole number
         age--;
      }
      int v = (age/2) + 7;
      return v;
  }

   /** Getter function for minAge
   * @return Value of variable1
   */
  public int getMinAge() {
    return minAge;
  }

  /** Setter function for minAge
   * @param v New value of minAge
   */
  public void setMinAge(int v) {
    minAge = v;
    // When we set a new value to variable 1 we need to also send a
    // message to let other modules know that the variable value
    // was changed
    mvcMessaging.notify("model:minAgeChanged", minAge, true);
  }
  
  /** Getter function for maxAge
   * @return Value of maxAge
   */
  public int getMaxAge() {
    return maxAge;
  }
  
  /** Setter function for maxAge
   * @param v New value of maxAge
   */
  public void setMaxAge(int v) {
     maxAge = v;
    // When we set a new value to variable 2 we need to also send a
    // message to let other modules know that the variable value
    // was changed
    mvcMessaging.notify("model:maxAgeChanged", maxAge, true);
  }
  
  /** Setter function for Age
   * @param v new value for age
   */
  public void setAge(int v) {
  age = v;  //the other 
  }
}
