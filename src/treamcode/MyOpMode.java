package treamcode;

import java.util.ArrayList;

public class MyOpMode extends OpMode {
    public static double[] followAngles = {Math.toRadians(90),Math.toRadians(90),Math.toRadians(90), Math.toRadians(-90)};
    public void init(){

    }
    public void loop(){
        ArrayList<CurvePoint> allPoints = new ArrayList<>();
        allPoints.add(new CurvePoint(0 , 100,1.0 ,0.5 ,40, 30, 0.5));
        allPoints.add(new CurvePoint(100 , 100,1.0 ,0.5 ,40, 30, 0.5));
        allPoints.add(new CurvePoint(150 , 80,0.25 ,0.5 ,40, 30, 0.5));
        allPoints.add(new CurvePoint(50 , 180,0.25 ,0.5 ,40, 30, 0.5));

        RobotMovement.followCurve(allPoints, followAngles);
    }
}
