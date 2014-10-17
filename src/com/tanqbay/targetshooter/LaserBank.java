package com.tanqbay.targetshooter;

public class LaserBank {
   
   private float laserSpeed = 200;
   private double rateOfFire = 0.500;
   private double lastShot;
   private double timeSinceLastShot = 0;
	  private int laserType;
	  
   public LaserBank(int laserType,float laserSpeed,double rateOfFire) {
      this.laserType = laserType;
      this.laserSpeed = laserSpeed;
      this.rateOfFire = rateOfFire;
   }
   
   public void updateTime(double timeDifference){
      timeSinceLastShot += timeDifference;
   }
   
   public void fire(DrawingSurface drawingSurface,double[] start,double angle){
   		if(timeSinceLastShot >= rateOfFire){
   			LaserBeam laser = new LaserBeam(start,laserSpeed,angle,drawingSurface);
   			laser.setType(laserType);
   			drawingSurface.getItems().add(laser);
   			lastShot = System.currentTimeMillis();
   			timeSinceLastShot = 0;
   		}
   	}
}