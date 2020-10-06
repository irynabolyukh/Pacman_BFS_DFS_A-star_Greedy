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

        // add initial node to the list
        path_queue.add(new MyPoint(0, 0, MyPoint.Direction.RIGHT));


        while (!path_queue.isEmpty()) {

            //get current position
            crt = (MyPoint) path_queue.removeFirst();

            // to check if goal reached
            if (isGoalReached(crt)) {
                path.add(crt);
                break;
            }

            //to remember all visited
            path.add(crt);
            markVisited(crt);

            //put its neighbors in the queue
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
