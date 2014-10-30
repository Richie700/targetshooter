package com.tanqbay.targetshooter;

public class GunShip extends Ship {
   
   private LaserBank laserBank;
   private boolean movingDown = true;
      
   public GunShip(DrawingSurface drawingSurface) {
      super(drawingSurface);
      
      collisionDamage = 10;
      collisionDamageDealt = 10;
      
      radius = 35;
      
      laserBank = new LaserBank(true,200,5.0);
   }
   
   public void update(double timeDifference,DrawingSurface drawingSurface){
      super.update(timeDifference,drawingSurface);
      
      laserBank.updateTime(timeDifference);
      		   
      laserBank.fire(drawingSurface,new double[]{(double) getPosition()[0],(double) getPosition()[1]},Math.PI);
   }
   
   protected void calculateDestinationRotation(){
		    if(movingDown){
		       destinationRotation = (float) (2 * Math.PI);   
		    }else{
		       destinationRotation = (float) 0;
		    }
		 
		 }
   
   protected void calculateDestination(TargetShooterGame game){
      
      if(movingDown){
         super.calculateDestination(game);
      }else{
         Destination = new float[]{Position[0],-10};
      }
      
      
      
      if(Position[1] > surfaceHeight * (2.0/3.0)){
         movingDown = false;
      }
      
      if(Position[1] < 0){
         movingDown = true;
      }
       
    }
	
}