package com.tanqbay.targetshooter;

import java.util.Random;

public class GunShip extends Ship {
   
   private LaserBank laserBank;
   private boolean movingDown = true;
   private Random rand;
   private float xDestination = 0;
    
   public GunShip(DrawingSurface drawingSurface) {
      super(drawingSurface);
      
      rand = new Random();
      
      collisionDamage = 10;
      collisionDamageDealt = 10;
      
      
      radius = 40;
      
      laserBank = new LaserBank(true,200,5.0);
   }
   
   public void update(double timeDifference,DrawingSurface drawingSurface){
      super.update(timeDifference,drawingSurface);
      
      laserBank.updateTime(timeDifference);
      		   
      laserBank.fire(drawingSurface,new double[]{(double) getPosition()[0],(double) getPosition()[1]},Math.PI);
   }
   
   
   protected void calculateDestination(TargetShooterGame game){
      
      if(movingDown){
         super.calculateDestination(game);
      }else{
         
         Destination = new float[]{xDestination,(float) (surfaceHeight / 7.0)};
      }
      
      
      
      if(Position[1] > surfaceHeight * (2.0/3.0)){
         movingDown = false;
         xDestination = rand.nextFloat() * surfaceWidth;
      }
      
      if(Position[1] < surfaceHeight / 6.0){
         movingDown = true;
      }
       
    }
	
}