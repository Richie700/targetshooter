package com.tanqbay.targetshooter;

public class GameItem extends Item {
   
   public static final int GUN_TYPE = 1;
   public static final int TARGET_TYPE = 2;
   public static final int WEAPONFIRE_TYPE = 3;
   
   
   public GameItem(DrawingSurface drawingSurface) {
      super(drawingSurface);
   }
}