package com.zetcode;

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
        MyPoint next;
        double dist;
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

            next = crt.moveLeft();
            if (isInMaze(next) && isClearLeft(next) && !isVisited(next)) {
                dist = manhattan_distance(next, goal);
                pointHeuristics.add(new PointHeuristic(next,dist));
                wentSomewhere = true;
            }

            next = crt.moveUp();
            if (isInMaze(next) && isClearUp(next) && !isVisited(next)) {
                dist = manhattan_distance(next, goal);
                pointHeuristics.add(new PointHeuristic(next,dist));
                wentSomewhere = true;
            }

            next = crt.moveRight();
            if (isInMaze(next) && isClearRight(next) && !isVisited(next)) {
                dist = manhattan_distance(next, goal);
                pointHeuristics.add(new PointHeuristic(next,dist));
                wentSomewhere = true;
            }

            next = crt.moveDown();
            if (isInMaze(next) && isClearDown(next) && !isVisited(next)) {
                dist = manhattan_distance(next, goal);
                pointHeuristics.add(new PointHeuristic(next,dist));
                wentSomewhere = true;
            }

            Collections.sort(pointHeuristics,Collections.reverseOrder());

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
