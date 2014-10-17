package com.tanqbay.targetshooter;

public class GunShip extends Ship {
   
   private LaserBank laserBank;
   
   public GunShip(DrawingSurface drawingSurface) {
      super(drawingSurface);
      
      radius = 35;
      
      laserBank = new LaserBank(200,0.500);
   }
   
   public void update(double timeDifference,DrawingSurface drawingSurface){
      super.update(timeDifference,drawingSurface);
      
      if(!drawingSurface.getPaused()){
      		   laserBank.updateTime(timeDifference);
      		   
      		   laserBank.fire(drawingSurface,getPosition(),0);
      }
   }
}