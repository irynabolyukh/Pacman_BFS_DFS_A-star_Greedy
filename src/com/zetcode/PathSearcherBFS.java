package com.zetcode;

public class PathSearcherBFS extends PathSearcher{

    @Override
    public void searchForPath() {
        solveBFS();
    }
    public void solveBFS() {

        // add initial node to the list
        path_queue.add(new MyPoint(0, 0, MyPoint.Direction.RIGHT));

        MyPoint crt, next;
        while (!path_queue.isEmpty()) {

            //get current position
            crt = (MyPoint) path_queue.removeFirst();

            // to be sure if it reach the goal
            if (isGoalReached(crt)) {
                break;
            }

            //to remember all visited
            path.add(crt);

            //put its neighbors in the queue
            next = crt.moveUp();    //move up
            if (isInMaze(next) && isClearUp(next) && !isVisited(next)) {
                path_queue.add(next);
            }
            next = crt.moveRight();    //move right
            if (isInMaze(next) && isClearRight(next) && !isVisited(next)) {
                path_queue.add(next);
            }
            next = crt.moveLeft();    //move left
            if (isInMaze(next) && isClearLeft(next) && !isVisited(next)) {
                path_queue.add(next);
            }
            next = crt.moveDown();   //move down
            if (isInMaze(next) && isClearDown(next) && !isVisited(next)) {
                path_queue.add(next);
            }

        }

    }
}
