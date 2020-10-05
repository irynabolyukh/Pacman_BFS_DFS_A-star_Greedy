package com.zetcode;

public class PathSearcherDFS extends PathSearcher{


    @Override
    public void searchForPath() {
        for(int i=0; i<15; i++){
            for(int j=0; j<15;j++){
                isVisited[i][j] = 0;
            }
        }
        solveDFS();
    }
    public void solveDFS() {
        //insert the start
        path_stack.push(new MyPoint(0, 0, MyPoint.Direction.DOWN));

        MyPoint crt;   //current node
        MyPoint next;  //next node
        boolean wentSomewhear;

        while (!path_stack.empty()) {

            //get current position
            crt = (MyPoint) path_stack.pop();

            //to remember all visited
            path.add(crt);

            if (isGoalReached(crt)) {
                break;
            }

            markVisited(crt);

            wentSomewhear = false;

            //put its neighbours in the queue
            next = crt.moveDown();  // go down from the current node
            if (isInMaze(next) && isClearDown(next) && !isVisited(next)) {
                path_stack.push(next);
                wentSomewhear = true;
            }
            next = crt.moveRight();    //go right from the current node
            if (isInMaze(next) && isClearRight(next) && !isVisited(next)) {
                path_stack.push(next);
                wentSomewhear = true;
            }
            next = crt.moveUp();    // go up
            if (isInMaze(next) && isClearUp(next) && !isVisited(next)) {
                path_stack.push(next);
                wentSomewhear = true;
            }
            next = crt.moveLeft();    //go left from the current node
            if (isInMaze(next) && isClearLeft(next) && !isVisited(next)) {
                path_stack.push(next);
                wentSomewhear = true;
            }

            if(!wentSomewhear) {

                path.remove(crt);
            }
        }

    }


}
