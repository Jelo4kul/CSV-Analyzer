/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stackedbarchart;

/**
 *
 * @author Jelo
 */
public class DuplicateEntryTest {

    private int[] input_array = {102, 3, 6, 1, 5, 4, 0, 3, 5};
    String[] stArray={"james", "gift", "james"};
    private int input_size = input_array.length;

    public static void main(String[] args) {
        System.out.println(new DuplicateEntryTest().testMethod());
    }

    public int testMethod() {
        int ans = 0;
        boolean flags[] = new boolean[103];
        
        for (int i = 0; i < 103; i++) {
            flags[i] = false;
        }

        for (int i = 0; i < input_size; i++) {
            if (flags[input_array[i]]) {
                ans = input_array[i];
            } else {
                flags[input_array[i]] = true;
            }
        }
        return ans;
    }
}
