package com.tanqbay.targetshooter;

public class GunShip extends Ship {
   
   private LaserBank laserBank;
   
   public GunShip(DrawingSurface drawingSurface) {
      super(drawingSurface);
      
      radius = 35;
      
      laserBank = new LaserBank(ENEMYLASERBEAM_TYPE,200,1.500);
   }
   
   public void update(double timeDifference,DrawingSurface drawingSurface){
      super.update(timeDifference,drawingSurface);
      
      laserBank.updateTime(timeDifference);
      		   
      	laserBank.fire(drawingSurface,new double[]{(double) getPosition()[0],(double) getPosition()[1]},Math.PI);
   }
}