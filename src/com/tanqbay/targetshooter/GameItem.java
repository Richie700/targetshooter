package com.tanqbay.targetshooter;

public class GameItem extends Item {
   
   public static final int GUN_TYPE = 1;
   public static final int TARGET_TYPE = 2;
   public static final int WEAPONFIRE_TYPE = 3;
   
   protected double life = 1;
   protected double maxLife = 1;
   protected double regeneration = 0;
   protected float collisionDamageDealt = 0;
   protected float collisionDamage = 0;
   
   public GameItem(DrawingSurface drawingSurface) {
      super(drawingSurface);
   }
   
   public void update(double timeDifference,DrawingSurface drawingSurface){
      super.update(timeDifference,drawingSurface);
      
      life += regeneration * timeDifference;
      life = Math.min(life,maxLife);
      
      if(life <= 0){
         setReadyToBeRemoved(true);
      }
      
   }
   
   public void collideWith(GameItem item){
      reduceLife(item.getCollisionDamageDealt());
      item.reduceLife(getCollisionDamageDealt());
      reduceLife(collisionDamage);
      collisionDamage = 0;
      collisionDamageDealt = 0;
   }
   
   public void reduceLife(double amount){
      life -= amount;
   }
   
   public double getCollisionDamageDealt(){
      return collisionDamageDealt;
   }
}