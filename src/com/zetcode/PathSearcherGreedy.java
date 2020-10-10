package com.zetcode;

import java.lang.reflect.Array;
import java.util.*;

public class PathSearcherGreedy extends PathSearcher {

    @Override
    public void searchForPath() {
        Runtime time = Runtime.getRuntime();
        time.gc();
        startTime = System.nanoTime();

        findGoal();
        solveGreedy();

        stopTime = System.nanoTime();
        duration = stopTime - startTime;
        timeInMs = (double)duration/1000000;
        totalUsedMemory = time.totalMemory() - time.freeMemory();
        totalUsedMemoryInBytes = totalUsedMemory/1024;

        System.out.println("___Greedy___");
        System.out.println("Time in milliseconds: " + timeInMs);
        System.out.println("Memory in bytes: " + totalUsedMemoryInBytes);

        totalSteps = path.size();

        System.out.println("Steps: " + totalSteps);
    }

    public void solveGreedy() {

        MyPoint crt;
        MyPoint nextD, nextR, nextU, nextL;
        double distD, distR, distU, distL;
        boolean wentSomewhere;
        List<PointHeuristic> pointHeuristics = new ArrayList<PointHeuristic>();

        path_stack.push(new MyPoint(0, 0, MyPoint.Direction.DOWN));

        while (!path_stack.empty()) {

            crt = path_stack.pop();

            path.add(crt);

            if (isGoalReached(crt)) {
                break;
            }

            markVisited(crt);

            wentSomewhere = false;

            nextL = crt.moveLeft();
            if (isInMaze(nextL) && isClearLeft(nextL) && !isVisited(nextL)) {
                distL = manhattan_distance(nextL, goal);
                pointHeuristics.add(new PointHeuristic(nextL,distL));
                wentSomewhere = true;
            }

            nextU = crt.moveUp();
            if (isInMaze(nextU) && isClearUp(nextU) && !isVisited(nextU)) {
                distU = manhattan_distance(nextU, goal);
                pointHeuristics.add(new PointHeuristic(nextU,distU));
                wentSomewhere = true;
            }

            nextR = crt.moveRight();
            if (isInMaze(nextR) && isClearRight(nextR) && !isVisited(nextR)) {
                distR = manhattan_distance(nextR, goal);
                pointHeuristics.add(new PointHeuristic(nextR,distR));
                wentSomewhere = true;
            }

            nextD = crt.moveDown();
            if (isInMaze(nextD) && isClearDown(nextD) && !isVisited(nextD)) {
                distD = manhattan_distance(nextD, goal);
                pointHeuristics.add(new PointHeuristic(nextD,distD));
                wentSomewhere = true;
            }

//            System.out.println("__________________________________________");
//            System.out.println(pointHeuristics);
            Collections.sort(pointHeuristics,Collections.reverseOrder());
//            System.out.println(pointHeuristics);
//            System.out.println("__________________________________________");

            for (PointHeuristic point : pointHeuristics){
                path_stack.push(point.getP());
            }

            pointHeuristics.clear();

            if(!wentSomewhere) {
                path.remove(crt);
            }
        }
    }


}
