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

    public static void followCurve(ArrayList<CurvePoint> allPoints, double[] followAngles){
        for(int i =0;i<allPoints.size()-1;i++){
            ComputerDebugging.sendLine(new FloatPoint(allPoints.get(i).x,allPoints.get(i).y),new FloatPoint(allPoints.get(i+1).x,allPoints.get(i+1).y) );
        }
        findLocationAlongPath(allPoints,new Point(worldXPosition,worldYPosition));
        CurvePoint followMe = getFollowPointPath(allPoints, new Point(worldXPosition, worldYPosition), allPoints.get(0).followDistance, followAngles[nextPointNum]);

        ComputerDebugging.sendKeyPoint(new FloatPoint(followMe.x,followMe.y));

        goToPosition(followMe.x,followMe.y, followMe.moveSpeed, followAngles[nextPointNum], followMe.turnSpeed);
    }

    public static ArrayList<CurvePoint> findLocationAlongPath(ArrayList<CurvePoint> allPoints, Point robotPos){
        ArrayList<CurvePoint> relevantPoints = new ArrayList<>();
        double shortestDistanceToLine = 100000000;

        for (int i =0; i < allPoints.size()-1;i++){
            double[] dArray = pointLineIntersection(robotPos,allPoints.get(i),allPoints.get(i+1));
            //System.out.println(dArray[0]);
            //System.out.println("("+ dArray[1] + ", "+ dArray[2]+")");
            double dist = dArray[0];
            if (dist < shortestDistanceToLine){

                shortestDistanceToLine = dist;
                relevantPoints.clear();
                relevantPoints.add(allPoints.get(i));
                relevantPoints.add(allPoints.get(i+1));
                if (i< allPoints.size()-2){
                    relevantPoints.add(allPoints.get(i+2));
                }
                nextPointNum = i+1;
            }
        }


        return relevantPoints;
    }

    public static CurvePoint getFollowPointPath(ArrayList<CurvePoint> pathPoints, Point robotPos, double followradius, double preferedAngle){
        ArrayList<CurvePoint> relevantPoints = findLocationAlongPath(pathPoints,robotPos);
        CurvePoint followMe = new CurvePoint(relevantPoints.get(1));

        for (int i = 0; i < relevantPoints.size()-1;i++){
            CurvePoint startLine = relevantPoints.get(i);
            CurvePoint endLine = relevantPoints.get(i+1);

            ArrayList<Point> intersections = lineCircleIntersection(robotPos,followradius, startLine.toPoint(),endLine.toPoint());

            double closestangle = 100000000;
            for (Point thisIntersection: intersections){
                double angle = Math.atan2(thisIntersection.y-robotPos.y,thisIntersection.x-robotPos.x);
                double relativeAngle = MathFunctions.AngleWrap(angle - (worldAngle_rad - Math.toRadians(90)));
                double deltaAngle = Math.abs(MathFunctions.AngleWrap(relativeAngle- preferedAngle));
                if (deltaAngle < closestangle){
                    closestangle = deltaAngle;
                    System.out.println(closestangle);
                    followMe.setPoint(thisIntersection);
                }
            }
        }
        return followMe;
    }


    public static void goToPosition(double x, double y, double movementSpeed, double preferedAngle, double turnSpeed){

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

        if (distanceToTarget>=20){
            movement_turn = Range.clip(relativeTurnAngle/Math.toRadians(30),-1,1)* turnSpeed;
        }else{
            movement_turn = 0;
        }

    }

}

