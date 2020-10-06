package com.zetcode;

public class PathSearcherDFS extends PathSearcher{


    @Override
    public void searchForPath() {
        solveDFS();

        duration = stopTime - startTime;    //calculate the elapsed time

        dfsTime =  (double)duration / 1000000;   //convert to ms
        System.out.println(String.format("Time %1.3f ms", dfsTime));
    }

    public void solveDFS() {

        // start of the time
        startTime = System.nanoTime();

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
                stopTime = System.nanoTime();
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
