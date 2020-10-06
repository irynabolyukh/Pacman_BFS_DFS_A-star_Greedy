package com.zetcode;

public class PathSearcherDFS extends PathSearcher{


    @Override
    public void searchForPath() {

        Runtime time = Runtime.getRuntime();
        time.gc();
        startTime = System.nanoTime();

        solveDFS();

        stopTime = System.nanoTime();
        duration = stopTime - startTime;
        timeInMs = (double)duration/1000000;
        totalUsedMemory = time.totalMemory() - time.freeMemory();
        totalUsedMemoryInBytes = totalUsedMemory/1024;

        System.out.println("___DFS___");
        System.out.println("Time in milliseconds: " + timeInMs);
        System.out.println("Memory in bytes: " + totalUsedMemoryInBytes);

        totalSteps = path.size();

        System.out.println("Steps: " + totalSteps);
    }

    public void solveDFS() {

        MyPoint crt;   //current node
        MyPoint next;  //next node
        boolean wentSomewhere;

        //insert the start
        path_stack.push(new MyPoint(0, 0, MyPoint.Direction.DOWN));

        while (!path_stack.empty()) {

            //get current position
            crt = (MyPoint) path_stack.pop();

            //to remember all visited
            path.add(crt);

            if (isGoalReached(crt)) {
                break;
            }

            markVisited(crt);

            wentSomewhere = false;

            //put its neighbours in the queue
            next = crt.moveDown();  // go down from the current node
            if (isInMaze(next) && isClearDown(next) && !isVisited(next)) {
                path_stack.push(next);
                wentSomewhere = true;
            }
            next = crt.moveRight();    //go right from the current node
            if (isInMaze(next) && isClearRight(next) && !isVisited(next)) {
                path_stack.push(next);
                wentSomewhere = true;
            }
            next = crt.moveUp();    // go up
            if (isInMaze(next) && isClearUp(next) && !isVisited(next)) {
                path_stack.push(next);
                wentSomewhere = true;
            }
            next = crt.moveLeft();    //go left from the current node
            if (isInMaze(next) && isClearLeft(next) && !isVisited(next)) {
                path_stack.push(next);
                wentSomewhere = true;
            }

            if(!wentSomewhere) {

                path.remove(crt);
            }
        }
    }
}
