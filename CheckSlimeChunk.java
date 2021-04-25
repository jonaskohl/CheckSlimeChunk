/**
 * CheckSlimeChunk Utility 1.0
 * Written April 25, 2021
 * by Jonas Kohl
 * https://jonaskohl.de/
 * 
 * This code is released under the public domain.
 * Based on the code posted on the Minecraft Wiki
 * https://minecraft.fandom.com/wiki/Slime#Java_Edition
 * 
 * To compile, use the included batch file (for Windows)
 * or bash script (for *NIX systems), or use the following
 * two commands:
 * 
 * javac CheckSlimeChunk.java
 * jar cvfe CheckSlimeChunk.jar CheckSlimeChunk CheckSlimeChunk.class
 */
import java.util.Random;

public class CheckSlimeChunk {
  public static void main(String args[]) {
    boolean simpleOutput = false;
    if (args.length >= 5 && args[4].equals("-s")) {
      simpleOutput = true;
    }
    
    if (!simpleOutput) {
      System.out.println("CheckSlimeChunk Utility 1.0 - by Jonas Kohl <https://jonaskohl.de/>");
      System.out.println("");
    }
    
    if (args.length < 4) {
      System.out.println("Usage: java -jar CheckSlimeChunk.jar <Seed> <Option> <xCoord> <zCoord> [-s]");
      System.out.println("Options:");
      System.out.println("    -c   Indicates that xCoord and zCoord will use chunk coordinates.");
      System.out.println("    -b   Indicates that xCoord and zCoord will use block coordinates.");
      System.out.println("");
      System.out.println("    -s   Outputs a simple \"true\" or \"false\" instead of the more verbose output.");
      System.out.println("Sample usage:");
      System.out.println("    java -jar CheckSlimeChunk.jar 12345678 -c 5 2");
      System.out.println("    java -jar CheckSlimeChunk.jar 12345678 -b 86 33 -s");
      System.exit(0);
      return;
    }
    
    long seed;
    int xPosition;
    int zPosition;
    int division = 1;
    
    if (isLong(args[0])) {
      seed = Long.parseLong(args[0]);
      if (!simpleOutput)
        System.out.println("Using numeric seed as-is. Numeric seed is " + seed + ".");
    } else {
      seed = args[0].hashCode();
      if (!simpleOutput)
        System.out.println("Using derived seed from string. Numeric seed is " + seed + ".");
    }
    
    if (args[1].equals("-c")) {
      if (!simpleOutput)
        System.out.println("Using chunk coordinates.");
      division = 1;
    } else if (args[1].equals("-b")) {
      if (!simpleOutput)
        System.out.println("Using block coordinates.");
      division = 16;
    } else {
      System.err.println("Fatal: Invalid option \"" + args[1] + "\"");
      System.exit(1);
    }
    
    if (!isInt(args[2])) {
      System.err.println("Fatal: xCoord must be a valid integer.");
      System.exit(1);
    }
    
    if (!isInt(args[3])) {
      System.err.println("Fatal: zCoord must be a valid integer.");
      System.exit(1);
    }
    
    xPosition = Integer.parseInt(args[2]) / division;
    zPosition = Integer.parseInt(args[3]) / division;

    Random rnd = new Random(
      seed +
      (int)(xPosition * xPosition * 0x4c1906) +
      (int)(xPosition * 0x5ac0db) +
      (int)(zPosition * zPosition) * 0x4307a7L +
      (int)(zPosition * 0x5f24f) ^ 0x3ad8025fL
    );
    
    boolean isSlimeChunk = rnd.nextInt(10) == 0;

    if (simpleOutput) {
      System.out.print(isSlimeChunk ? "true" : "false");
    } else {
      System.out.println("The chunk at chunk coordinates " + xPosition + "," + zPosition + " is " + (isSlimeChunk ? "" : "not ") + "a slime chunk.");
    }
  }
  
  public static boolean isLong(String str) {
    try {
      Long.parseLong(str);
      return true;
    } catch(NumberFormatException e) {
      return false;
    }
  }
  
  public static boolean isInt(String str) {
    try {
      Integer.parseInt(str);
      return true;
    } catch(NumberFormatException e) {
      return false;
    }
  }
}
