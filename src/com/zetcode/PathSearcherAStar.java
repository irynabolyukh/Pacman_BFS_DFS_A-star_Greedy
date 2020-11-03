package com.zetcode;

import java.util.HashMap;

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

        path_queue.add(crt);
        steps_map.put(crt, new Step(crt, 0, manhattan_distance(crt, goal)));

        while (!path_queue.isEmpty()) {

            crt = path_queue.removeFirst();

            int g = steps_map.get(crt).getG();

            if (isGoalReached(crt)) {
                break;
            }

            markVisited(crt);

            next = crt.moveUp();
            if (isInMaze(next) && isClearUp(next) && !isVisited(next)) {
                path_queue.add(next);
                Step toNext = steps_map.get(next);

                if(toNext == null || (toNext != null && toNext.getG()>=g+1)) {
                    steps_map.put(next, new Step(crt, g + 1, manhattan_distance(next, goal)));
                }
            }
            next = crt.moveRight();
            if (isInMaze(next) && isClearRight(next) && !isVisited(next)) {
                path_queue.add(next);
                Step toNext = steps_map.get(next);

                if(toNext == null || (toNext != null && toNext.getG()>=g+1)) {
                    steps_map.put(next, new Step(crt, g + 1, manhattan_distance(next, goal)));
                }
            }
            next = crt.moveDown();
            if (isInMaze(next) && isClearDown(next) && !isVisited(next)) {
                path_queue.add(next);
                Step toNext = steps_map.get(next);

                if(toNext == null || (toNext != null && toNext.getG()>=g+1)) {
                    steps_map.put(next, new Step(crt, g + 1, manhattan_distance(next, goal)));
                }
            }
            next = crt.moveLeft();
            if (isInMaze(next) && isClearLeft(next) && !isVisited(next)) {
                path_queue.add(next);
                Step toNext = steps_map.get(next);

                if(toNext == null || (toNext != null && toNext.getG()>=g+1)) {
                    steps_map.put(next, new Step(crt, g + 1, manhattan_distance(next, goal)));
                }
            }
        }

        crt = goal;
        path.add(crt);

        do{
            next = steps_map.get(crt).getFrom();
            path.add(0, next);
            crt = next;
        }while(!next.equals(new MyPoint(0, 0, MyPoint.Direction.RIGHT)));

        path.add(0, next);
    }

}
