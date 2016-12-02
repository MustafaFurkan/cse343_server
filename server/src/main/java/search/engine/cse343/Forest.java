package search.engine.cse343;

import java.util.ArrayList;

/**
 * Created by deniz on 06.11.2016.
 */
public class Forest {

    private ArrayList<Tree<String>> trees = new ArrayList<Tree<String>>();

    public Forest() {
        int headCar = 97;
        int startCar = 97;
        int height = 25;


        while (headCar < 123) {
            Tree<String> car = new Tree<String>();
            StringBuilder builder = new StringBuilder();
            builder.append((char) headCar);
            car.add(builder.toString());

            while (height >= 0) {

                while (startCar < 123) {

                    builder = new StringBuilder();
                    builder.append((char) startCar);

                    car.add(builder.toString());
                    ++startCar;

                }
                startCar = 97;
                --height;
            }
            height = 25;
            ++headCar;
            trees.add(car);

        }

    }

    //For Test two funciton
    public void test()
    {
        trees.get(0).setIndexUrl("ba",5);
    }

    public  void display() {
        System.out.println(trees.get(0).find("ba").get(0));

    }
}

