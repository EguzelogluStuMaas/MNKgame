package game;
import java.util.*;
public class Reward {
    

    HashMap<Integer, Integer> rewards = new HashMap<>();
    /*if game is won reward is 1, lose -1 ,draw 0 put in a hashmap */
    public Reward() {
        rewards.put(0, 0);
        rewards.put(1, 1);
        rewards.put(2, -1);
    }
    public int getReward(int result) {
        return rewards.get(result);
    }
    public void setReward(int result, int value) {
        rewards.put(result, value);
    }
    public void print() {
        for (Map.Entry<Integer, Integer> entry : rewards.entrySet()) {
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
        }
    }



}
