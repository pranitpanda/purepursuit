package treamcode;

import java.util.ArrayList;

public class MyOpMode extends OpMode {
    public static double[] followAngles = {Math.toRadians(90),Math.toRadians(90),Math.toRadians(90), Math.toRadians(-90), Math.toRadians(-90), Math.toRadians(-45), Math.toRadians(90),
            Math.toRadians(90), Math.toRadians(90), Math.toRadians(-90),Math.toRadians(-90),Math.toRadians(90)};
    public void init(){

    }
    public void loop(){

        ArrayList<CurvePoint> allPoints = new ArrayList<>();
        allPoints.add(new CurvePoint(0 , 100,1.0 ,0.7 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(100 , 100,1.0 ,0.7 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(150 , 80,0.25 ,0.7 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(50 , 180,0.25 ,0.5 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(50 , 230,0.25 ,0.5 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(100 , 280,0.25 ,0.5 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(50 , 230,0.25 ,0.5 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(50 , 80,0.25 ,0.5 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(150 , 50,0.25 ,0.5 ,25, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(80 , 150,0.25 ,0.7 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(80 , 250,0.25 ,1.0 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(80 , 170,0.9 ,1.0 ,35, Math.toRadians(30), 0.5));






        RobotMovement.followCurve(allPoints, followAngles);
    }
}
