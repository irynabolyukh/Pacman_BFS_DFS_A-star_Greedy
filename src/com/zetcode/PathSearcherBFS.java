package com.zetcode;

public class PathSearcherBFS extends PathSearcher{

    @Override
    public void searchForPath() {

        Runtime time = Runtime.getRuntime();
        time.gc();
        startTime = System.nanoTime();

        solveBFS();

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

    public void solveBFS() {

        MyPoint crt, next;

        path_queue.add(new MyPoint(0, 0, MyPoint.Direction.RIGHT));


        while (!path_queue.isEmpty()) {

            crt = path_queue.removeFirst();

            if (isGoalReached(crt)) {
                path.add(crt);
                break;
            }

            path.add(crt);
            markVisited(crt);

            next = crt.moveUp();
            if (isInMaze(next) && isClearUp(next) && !isVisited(next)) {
                path_queue.add(next);
            }
            next = crt.moveRight();
            if (isInMaze(next) && isClearRight(next) && !isVisited(next)) {
                path_queue.add(next);
            }
            next = crt.moveDown();
            if (isInMaze(next) && isClearDown(next) && !isVisited(next)) {
                path_queue.add(next);
            }
            next = crt.moveLeft();
            if (isInMaze(next) && isClearLeft(next) && !isVisited(next)) {
                path_queue.add(next);
            }
        }
    }
}
