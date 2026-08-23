/*     */ package partsgenerator;
/*     */ 
/*     */ 
/*     */ public enum CollatorPlates
/*     */ {
/*   6 */   BASE_DOWN_BRASS_9("9mm Base Down Brass", 9.0D, 7.0D, false, false, false, 0.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*   7 */   BASE_DOWN_BRASS_40(".40 S&W Base Down Brass", 10.2D, 7.0D, false, false, false, 0.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*   8 */   BASE_DOWN_BRASS_45(".45 ACP Base Down Brass", 11.5D, 8.0D, false, false, false, 0.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*   9 */   BASE_DOWN_BRASS_50(".50 Base Down Brass", 12.7D, 8.0D, false, false, false, 0.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  10 */   BASE_DOWN_BRASS_SMALL_PISTOL("Small Pisol Base Down Brass", 12.0D, 7.0D, false, false, false, 0.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  11 */   BASE_DOWN_BRASS_LARGE_PISTOL("Large Pistol Base Down Brass", 15.0D, 8.0D, false, false, false, 0.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  12 */   BASE_DOWN_BRASS_223(".223 Base Down Brass", 46.0D, 8.0D, false, false, true, 9.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  13 */   BASE_DOWN_BRASS_300("300 BLK Base Down Brass", 36.0D, 8.0D, false, false, true, 9.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  14 */   BASE_DOWN_BRASS_308(".308 Rifle Base Down Brass", 54.0D, 8.0D, false, false, true, 12.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  15 */   BASE_DOWN_BRASS_4570(".45-70 Base Down Brass", 56.0D, 8.0D, false, false, true, 12.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  16 */   BASE_DOWN_BRASS_SMALL_RIFLE("Small Rifle Base Down Brass", 46.0D, 8.0D, false, false, true, 9.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  17 */   BASE_DOWN_BRASS_LARGE_RIFLE("Large Rifle Base Down Brass", 54.0D, 8.0D, false, false, true, 12.0D, false, false, true, true, true, 2.5D, 30.0D, false, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  18 */   BASE_UP_BRASS_SMALL_PISTOL("Small Pisol Base Up Brass", 10.5D, 20.0D, false, false, false, 0.0D, true, false, true, true, true, 2.5D, 30.0D, true, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  19 */   BASE_UP_BRASS_LARGE_PISTOL("Large Pistol Base Up Brass", 12.5D, 22.0D, false, false, false, 0.0D, true, false, true, true, true, 2.5D, 30.0D, true, 1.6D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*     */   
/*  21 */   NOSE_UP_DOWN_BULLET_9("9mm Bullet", 9.0D, 13.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  22 */   NOSE_UP_DOWN_BULLET_40(".40 S&W Bullet", 10.2D, 13.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  23 */   NOSE_UP_DOWN_BULLET_45(".45 ACP Bullet", 11.5D, 13.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  24 */   NOSE_UP_DOWN_BULLET_50(".50 Bullet", 12.7D, 13.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  25 */   NOSE_UP_DOWN_BULLET_SMALL_PISTOL("Small Pistol Bullet", 9.0D, 13.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  26 */   NOSE_UP_DOWN_BULLET_LARGE_PISTOL("Large Pistol Bullet", 11.5D, 13.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  27 */   NOSE_UP_DOWN_BULLET_223(".223 Bullet", 5.69D, 18.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  28 */   NOSE_UP_DOWN_BULLET_65("6.5mm Bullet", 6.72D, 18.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  29 */   NOSE_UP_DOWN_BULLET_300("300 BLK Bullet", 7.82D, 22.0D, true, true, false, 0.0D, false, true, true, true, false, 3.0D, 10.0D, true, 1.4D, 3.0D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  30 */   NOSE_UP_DOWN_BULLET_308(".308 Bullet", 7.82D, 18.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  31 */   NOSE_UP_DOWN_BULLET_SMALL_RIFLE("Small Rifle Bullet", 5.7D, 18.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  32 */   NOSE_UP_DOWN_BULLET_LARGE_RIFLE("Large Rifle Bullet", 7.82D, 18.0D, false, false, false, 0.0D, false, true, true, true, true, 2.5D, 30.0D, true, 1.4D, 1.5D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D),
/*  33 */   NOSE_UP_DOWN_BULLET_LONG_RIFLE("Long Rifle Bullet", 7.82D, 22.0D, true, true, false, 0.0D, false, true, true, true, false, 3.0D, 10.0D, true, 1.4D, 3.0D, true, false, false, 5.0D, 12.4D, 10.1D, 29.0D, 5.0D, 100, 180.0D, 1.2D);
/*     */   
/*     */   private final String description;
/*     */   private final double caliber;
/*     */   private final double plateHeight;
/*     */   private final boolean isLongRifleBullet;
/*     */   private final boolean addRamps;
/*     */   private final boolean isRifleBrass;
/*     */   private final double rifleHoleWidth;
/*     */   private final boolean addPivots;
/*     */   private final boolean addSlides;
/*     */   private final boolean addRidges;
/*     */   private final boolean ridgeCenter;
/*     */   private final boolean ridgeAlternate;
/*     */   private final double ridgeHeight;
/*     */   private final double ridgeLength;
/*     */   private final boolean addBevel;
/*     */   private final double bevelSize;
/*     */   private final double holeMultiplier;
/*     */   private final boolean useClutch;
/*     */   private final boolean useHex;
/*     */   private final boolean addHexHandle;
/*     */   private final double hexHandleHeight;
/*     */   private final double hxw;
/*     */   private final double shaftHole;
/*     */   private final double shaftSlotLength;
/*     */   private final double shaftSlotWidth;
/*     */   private final int fn;
/*     */   private final double plateDiameter;
/*     */   private final double bulletCaliber;
/*     */   
/*     */   CollatorPlates(String description, double caliber, double plateHeight, boolean isLongRifleBullet, boolean addRamps, boolean isRifleBrass, double rifleHoleWidth, boolean addPivots, boolean addSlides, boolean addRidges, boolean ridgeCenter, boolean ridgeAlternate, double ridgeHeight, double ridgeLength, boolean addBevel, double bevelSize, double holeMultiplier, boolean useClutch, boolean useHex, boolean addHexHandle, double hexHandleHeight, double hxw, double shaftHole, double shaftSlotLength, double shaftSlotWidth, int fn, double plateDiameter, double bulletCaliber) {
/*  65 */     this.description = description;
/*  66 */     this.caliber = caliber;
/*  67 */     this.plateHeight = plateHeight;
/*  68 */     this.isLongRifleBullet = isLongRifleBullet;
/*  69 */     this.addRamps = addRamps;
/*  70 */     this.isRifleBrass = isRifleBrass;
/*  71 */     this.rifleHoleWidth = rifleHoleWidth;
/*  72 */     this.addPivots = addPivots;
/*  73 */     this.addSlides = addSlides;
/*  74 */     this.addRidges = addRidges;
/*  75 */     this.ridgeCenter = ridgeCenter;
/*  76 */     this.ridgeAlternate = ridgeAlternate;
/*  77 */     this.ridgeHeight = ridgeHeight;
/*  78 */     this.ridgeLength = ridgeLength;
/*  79 */     this.addBevel = addBevel;
/*  80 */     this.bevelSize = bevelSize;
/*  81 */     this.holeMultiplier = holeMultiplier;
/*  82 */     this.useClutch = useClutch;
/*  83 */     this.useHex = useHex;
/*  84 */     this.addHexHandle = addHexHandle;
/*  85 */     this.hexHandleHeight = hexHandleHeight;
/*  86 */     this.hxw = hxw;
/*  87 */     this.shaftHole = shaftHole;
/*  88 */     this.shaftSlotLength = shaftSlotLength;
/*  89 */     this.shaftSlotWidth = shaftSlotWidth;
/*  90 */     this.fn = fn;
/*  91 */     this.plateDiameter = plateDiameter;
/*  92 */     this.bulletCaliber = bulletCaliber;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  96 */     return this.description;
/*     */   }
/*     */   
/*     */   public double getCaliber() {
/* 100 */     return this.caliber;
/*     */   }
/*     */   
/*     */   public double getPlateHeight() {
/* 104 */     return this.plateHeight;
/*     */   }
/*     */   
/*     */   public boolean isIsLongRifleBullet() {
/* 108 */     return this.isLongRifleBullet;
/*     */   }
/*     */   
/*     */   public boolean isAddRamps() {
/* 112 */     return this.addRamps;
/*     */   }
/*     */   
/*     */   public boolean isIsRifleBrass() {
/* 116 */     return this.isRifleBrass;
/*     */   }
/*     */   
/*     */   public double getRifleHoleWidth() {
/* 120 */     return this.rifleHoleWidth;
/*     */   }
/*     */   
/*     */   public boolean isAddPivots() {
/* 124 */     return this.addPivots;
/*     */   }
/*     */   
/*     */   public boolean isAddSlides() {
/* 128 */     return this.addSlides;
/*     */   }
/*     */   
/*     */   public boolean isAddRidges() {
/* 132 */     return this.addRidges;
/*     */   }
/*     */   
/*     */   public boolean isRidgeCenter() {
/* 136 */     return this.ridgeCenter;
/*     */   }
/*     */   
/*     */   public boolean isRidgeAlternate() {
/* 140 */     return this.ridgeAlternate;
/*     */   }
/*     */   
/*     */   public double getRidgeHeight() {
/* 144 */     return this.ridgeHeight;
/*     */   }
/*     */   
/*     */   public double getRidgeLength() {
/* 148 */     return this.ridgeLength;
/*     */   }
/*     */   
/*     */   public boolean isAddBevel() {
/* 152 */     return this.addBevel;
/*     */   }
/*     */   
/*     */   public double getBevelSize() {
/* 156 */     return this.bevelSize;
/*     */   }
/*     */   
/*     */   public double getHoleMultiplier() {
/* 160 */     return this.holeMultiplier;
/*     */   }
/*     */   
/*     */   public boolean isUseClutch() {
/* 164 */     return this.useClutch;
/*     */   }
/*     */   
/*     */   public boolean isUseHex() {
/* 168 */     return this.useHex;
/*     */   }
/*     */   
/*     */   public boolean isAddHexHandle() {
/* 172 */     return this.addHexHandle;
/*     */   }
/*     */   
/*     */   public double getHexHandleHeight() {
/* 176 */     return this.hexHandleHeight;
/*     */   }
/*     */   
/*     */   public double getHxw() {
/* 180 */     return this.hxw;
/*     */   }
/*     */   
/*     */   public double getShaftHole() {
/* 184 */     return this.shaftHole;
/*     */   }
/*     */   
/*     */   public double getShaftSlotLength() {
/* 188 */     return this.shaftSlotLength;
/*     */   }
/*     */   
/*     */   public double getShaftSlotWidth() {
/* 192 */     return this.shaftSlotWidth;
/*     */   }
/*     */   
/*     */   public int getFn() {
/* 196 */     return this.fn;
/*     */   }
/*     */   
/*     */   public double getPlateDiameter() {
/* 200 */     return this.plateDiameter;
/*     */   }
/*     */   
/*     */   public double getBulletCaliber() {
/* 204 */     return this.bulletCaliber;
/*     */   }
/*     */ }


/* Location:              /Users/shanelupton/Downloads/Feeder-main/Parts Generator.jar!/partsgenerator/CollatorPlates.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */