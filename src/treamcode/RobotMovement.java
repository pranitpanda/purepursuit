package treamcode;

import com.company.ComputerDebugging;
import com.company.FloatPoint;
import com.company.Range;
import org.opencv.core.Point;

import java.util.ArrayList;

import static com.company.Robot.*;
import static RobotUtilities.MovementVars.*;
import static treamcode.MathFunctions.*;

public class RobotMovement {

    public static int nextPointNum = 1;


    public static double pastX = worldXPosition;
    public static double pastY = worldYPosition;

    public static double xVelocity = 0;
    public static double yVelocity = 0;

    public static void followCurve(ArrayList<CurvePoint> allPoints, double[] followAngles){
        for(int i =0;i < allPoints.size()-1;i++){
            ComputerDebugging.sendLine(new FloatPoint(allPoints.get(i).x,allPoints.get(i).y),new FloatPoint(allPoints.get(i+1).x,allPoints.get(i+1).y) );
        }
        updateVelocity();

        updateLocationAlongPath(allPoints, new Point(worldXPosition,worldYPosition));
//        for(int i =nextPointNum-1;i<nextPointNum+1 && i < allPoints.size()-1;i++){
//            ComputerDebugging.sendLine(new FloatPoint(allPoints.get(i).x,allPoints.get(i).y),new FloatPoint(allPoints.get(i+1).x,allPoints.get(i+1).y) );
//        }

        drawDistanceLine(allPoints,new Point(worldXPosition,worldYPosition));


        CurvePoint followMe = getFollowPointPath(allPoints, new Point(worldXPosition, worldYPosition), followAngles[nextPointNum]);

        ComputerDebugging.sendKeyPoint(new FloatPoint(followMe.x,followMe.y));

        goToPosition(followMe.x,followMe.y, followMe.moveSpeed, followAngles[nextPointNum], followMe.turnSpeed,followMe.slowDownTurnRadians,followMe.slowDownTurnAmount);
    }

    //Finds where the robot is along the path and updates nextPointNum to be the index of the next curve point
    public static void updateLocationAlongPath(ArrayList<CurvePoint> allPoints, Point robotPos){
        double shortestDistanceToLine = 100000000;
        for (int i = 0; i < allPoints.size()-1;i++){
            double[] dArray = pointLineIntersection(robotPos,allPoints.get(i),allPoints.get(i+1));

            double dist = dArray[0];

            double lineAngle = Math.atan2(allPoints.get(i+1).y-allPoints.get(i).y,allPoints.get(i+1).x-allPoints.get(i).x);

            double robotAngle = Math.atan2(yVelocity,xVelocity);

            double directionDiff = Math.abs(MathFunctions.AngleWrap(robotAngle-lineAngle));


            if (dist < shortestDistanceToLine && directionDiff < Math.toRadians(130)){

                shortestDistanceToLine = dist;

                if (i == nextPointNum){
                    nextPointNum++;

                }

            }
        }

    }

    //Updates the x and y velocities of the robot and the past position
    public static void updateVelocity(){
        xVelocity = worldXPosition - pastX;
        yVelocity = worldYPosition - pastY;
        pastX = worldXPosition;
        pastY = worldYPosition;
    }
    public static void drawDistanceLine(ArrayList<CurvePoint> allPoints, Point robotPos){
        double[] arr = pointLineIntersection(robotPos,allPoints.get(nextPointNum-1),allPoints.get(nextPointNum));
        ComputerDebugging.sendLine(new FloatPoint(arr[1],arr[2]),new FloatPoint(robotPos.x,robotPos.y) );
        //ComputerDebugging.sendKeyPoint(new FloatPoint(arr[1],arr[2]));

    }

    public static CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Point robotPos, double preferredAngle){

        CurvePoint followMe = new CurvePoint(pathPoints.get(nextPointNum));
        //System.out.println("Start point: "+ (nextPointNum-1) + " End Point: "+ (nextPointNum+1));

        for (int i = nextPointNum-1; i < nextPointNum + 1 && i <pathPoints.size()-1;i++){
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endLine = pathPoints.get(i+1);

            ArrayList<Point> intersections = lineCircleIntersection(robotPos,followMe.followDistance, startLine.toPoint(),endLine.toPoint());


            double closestAngle = 100000000;
            for (Point thisIntersection: intersections){
                double angle = Math.atan2(thisIntersection.y-robotPos.y,thisIntersection.x-robotPos.x);
                double relativeAngle = MathFunctions.AngleWrap(angle - (worldAngle_rad - Math.toRadians(90)));
                double deltaAngle = Math.abs(MathFunctions.AngleWrap(relativeAngle- preferredAngle));

                if (deltaAngle < closestAngle){
                    closestAngle = deltaAngle;
                    followMe.setPoint(thisIntersection);
                }
            }
        }
        if (nextPointNum==11&& Math.hypot(pathPoints.get(11).x-robotPos.x,pathPoints.get(11).y-robotPos.y) <= followMe.followDistance){
            followMe.setPoint(new Point(robotPos.x,robotPos.y));
        }
        return followMe;
    }


    public static void goToPosition(double x, double y, double movementSpeed, double preferedAngle, double turnSpeed, double slowDownTurnRadians, double slowDownAmount){

        double distanceToTarget = Math.hypot(x-worldXPosition,y-worldYPosition);

        //Finds the angle to the target in the absolute xy coordinates of the map
        double absoluteAngleToTarget = Math.atan2(y-worldYPosition,x-worldXPosition);

        //Finds the angle to the target in the xy coordinates of the robot
        double relativeAngle = MathFunctions.AngleWrap(absoluteAngleToTarget - (worldAngle_rad - Math.toRadians(90)));



        double relativeXtoPoint = Math.cos(relativeAngle)*distanceToTarget;
        double relativeYtoPoint = Math.sin(relativeAngle)*distanceToTarget;

        double movementXPower = relativeXtoPoint/(Math.abs(relativeXtoPoint) + Math.abs(relativeYtoPoint));
        double movementYPower = relativeYtoPoint/(Math.abs(relativeXtoPoint) + Math.abs(relativeYtoPoint));

        movement_x = movementXPower * movementSpeed;
        movement_y = movementYPower * movementSpeed;


        double relativeTurnAngle = relativeAngle - preferedAngle;

        if (relativeTurnAngle > slowDownTurnRadians){
            movement_y*=slowDownAmount;
            movement_x*=slowDownAmount;
        }

        if (distanceToTarget>=20){
            movement_turn = Range.clip(relativeTurnAngle/Math.toRadians(30),-1,1)* turnSpeed;
        }else{
            movement_turn = 0;
        }

    }

}

