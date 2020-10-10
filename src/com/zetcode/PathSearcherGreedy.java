package com.zetcode;

public class PathSearcherGreedy extends PathSearcher {

    @Override
    public void searchForPath() {
//        solveGreedy();
    }

//    public void solveGreedy() {
//
//        MyPoint crt, next;
//
//        // add initial node to the list
//        path.add(new MyPoint(0, 0, MyPoint.Direction.RIGHT));
//
//        crt = (MyPoint) path_queue.removeFirst();
//
//        while (!isGoalReached(crt)) {
//
//            //get current position
//            crt = (MyPoint) path_queue.removeFirst();
//
//            // to be sure if it reach the goal
//            if (isGoalReached(crt)) {
//                path.add(crt);
//                break;
//            }
//
//            //to remember all visited
//            path.add(crt);
//            markVisited(crt);
//
//            //put its neighbors in the queue
//            next = crt.moveUp();    //move up
//            if (isInMaze(next) && isClearUp(next) && !isVisited(next)) {
//                path_queue.add(next);
//            }
//            next = crt.moveRight();    //move right
//            if (isInMaze(next) && isClearRight(next) && !isVisited(next)) {
//                path_queue.add(next);
//            }
//            next = crt.moveDown();   //move down
//            if (isInMaze(next) && isClearDown(next) && !isVisited(next)) {
//                path_queue.add(next);
//            }
//            next = crt.moveLeft();    //move left
//            if (isInMaze(next) && isClearLeft(next) && !isVisited(next)) {
//                path_queue.add(next);
//            }
//        }
//    }

    private double manhattan_distance(MyPoint next, MyPoint goal) {
        return Math.abs(next.getX() - goal.getX()) + Math.abs(next.getY() - goal.getY());
    }
}
