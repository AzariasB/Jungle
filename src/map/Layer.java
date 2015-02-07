/*
 Put the utility of the class here
 */
package map;

import java.util.Scanner;
import java.util.regex.Pattern;

public class Layer {

    public Layer(int l_width, int l_height) {
        mLayer = new int[l_height][l_width];
    }

    public void readArray(Scanner fToRead) {
        fToRead.useDelimiter(Pattern.compile(",|\\s"));
        for (int i = 0; i < mLayer.length; i++) {
            for (int j = 0; j < mLayer[i].length; j++) {
                //System.out.println("i = " + i + " j = " + j);
                mLayer[i][j] = fToRead.nextInt();
            }
            if (i < mLayer.length - 1 ) {
                //System.out.println("Avant");
                fToRead.nextLine();
                //System.out.println("Apres");
            }
            //System.out.println(i);
        }
        System.out.println("Layer lu");
        fToRead.useDelimiter(" ");
    }

//    @Override
//    public String toString() {
//        String to_give = "";
//        for (int ligne[] : mLayer) {
//            for (int i : ligne) {
//                to_give += i + ",";
//            }
//            to_give += "\n";
//        }
//        to_give += "\n";
//        return to_give;
//    }

    public int[][] getArray() {
        return mLayer;
    }

    private int mLayer[][];
}
