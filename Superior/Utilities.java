package Superior;

import org.powerbot.script.Condition;

import java.util.Random;

public class Utilities {
    static Random rand = new Random();
    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public static void breathe() {
        int randInt1 = rand.nextInt(rand.nextInt(150));
        Condition.wait(() -> true,randInt1, 1);
    }

    public static void breathe(int pause) {
        int r;
        int variability;
        if(pause < 30 && pause > 2) {
            variability = pause - 2;
        } else if(pause > 30) {
            variability = 30;
        } else {
            return;
        }
        if(new Random().nextBoolean()){
            r = - rand.nextInt(variability);
        } else {
            r = rand.nextInt(variability);
        }
        Condition.wait(() -> true, pause + r, 1);
    }

    public static int random(int medium) {
        int r;
        if (rand.nextBoolean()) {
            r = -rand.nextInt(medium/10);
        } else {
            r = rand.nextInt(medium/10);
        }
        return r + medium;
    }
}
