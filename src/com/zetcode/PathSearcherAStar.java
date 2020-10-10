package com.zetcode;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;

public class PathSearcherAStar extends PathSearcher {

    public HashMap<MyPoint, Step> steps_map = new HashMap<>();

    @Override
    public void searchForPath() {

        Runtime time = Runtime.getRuntime();
        time.gc();
        startTime = System.nanoTime();

        findGoal();
        solveAStar();

        stopTime = System.nanoTime();
        duration = stopTime - startTime;
        timeInMs = (double)duration/1000000;
        totalUsedMemory = time.totalMemory() - time.freeMemory();
        totalUsedMemoryInBytes = totalUsedMemory/1024;

        System.out.println("___BFS___");
        System.out.println("Time in milliseconds: " + timeInMs);
        System.out.println("Memory in bytes: " + totalUsedMemoryInBytes);

        totalSteps = path.size();

        System.out.println("Steps: " + totalSteps);
    }


    private void solveAStar(){

        MyPoint crt, next;

        crt = new MyPoint(0, 0, MyPoint.Direction.RIGHT);

        // add initial node to the list
        path_queue.add(crt);
        steps_map.put(crt, new Step(crt, 0, manhattan_distance(crt, goal)));

        while (!path_queue.isEmpty()) {

            //get current position
            crt = (MyPoint) path_queue.removeFirst();
            System.out.println(crt);
            System.out.println("        " + steps_map.get(crt).getFrom());

            int g = steps_map.get(crt).getG();

            // to be sure if it reach the goal
            if (isGoalReached(crt)) {
//                path.add(crt);

                break;
            }

            //to remember all visited
            //path.add(crt);
            markVisited(crt);

            //put its neighbors in the queue
            next = crt.moveUp();    //move up
            if (isInMaze(next) && isClearUp(next) && !isVisited(next)) {
                path_queue.add(next);
                steps_map.put(next, new Step(crt, g+1, manhattan_distance(next, goal)));
            }
            next = crt.moveRight();    //move right
            if (isInMaze(next) && isClearRight(next) && !isVisited(next)) {
                path_queue.add(next);
                steps_map.put(next, new Step(crt, g+1, manhattan_distance(next, goal)));
            }
            next = crt.moveDown();   //move down
            if (isInMaze(next) && isClearDown(next) && !isVisited(next)) {
                path_queue.add(next);
                steps_map.put(next, new Step(crt, g+1, manhattan_distance(next, goal)));
            }
            next = crt.moveLeft();    //move left
            if (isInMaze(next) && isClearLeft(next) && !isVisited(next)) {
                path_queue.add(next);
                steps_map.put(next, new Step(crt, g+1, manhattan_distance(next, goal)));
            }
        }

        System.out.println("===========================================================");

        crt = goal;
        path.add(crt);

        do{
            next = steps_map.get(crt).getFrom();
            path.add(0, next);
            System.out.println(next);
            crt = next;
        }while(!next.equals(new MyPoint(0, 0, MyPoint.Direction.RIGHT)));

        path.add(0, next);
    }

}
