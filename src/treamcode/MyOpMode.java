package treamcode;

import java.util.ArrayList;

public class MyOpMode extends OpMode {
    public static double[] followAngles = {Math.toRadians(90),Math.toRadians(90),Math.toRadians(90), Math.toRadians(-90), Math.toRadians(-90),
            Math.toRadians(-45),Math.toRadians(-60), Math.toRadians(-80), Math.toRadians(90), Math.toRadians(90), Math.toRadians(90), Math.toRadians(90),
            Math.toRadians(90), Math.toRadians(-90)};
    public void init(){

    }
    public void loop(){

        ArrayList<CurvePoint> allPoints = new ArrayList<>();
        allPoints.add(new CurvePoint(0 , 100,0.3 ,0.7 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(100 , 100,0.3 ,0.7 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(150 , 80,0.3,0.7 ,15, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(50 , 180,0.3 ,0.5 ,35, Math.toRadians(30), 0.5));
        allPoints.add(new CurvePoint(50 , 230,0.3 ,0.5 ,35, Math.toRadians(30), 0.2));

        allPoints.add(new CurvePoint(70, 250,0.3 ,0.5 ,25, Math.toRadians(20), 0.5));
        allPoints.add(new CurvePoint(90, 260,0.1,0.5,25, Math.toRadians(15),0.01));
        allPoints.add(new CurvePoint(120, 260, 0.1, 0.5, 25, Math.toRadians(20), 0.5));
        allPoints.add(new CurvePoint(90, 260,0.1,0.5,25, Math.toRadians(15),0.5));
        allPoints.add(new CurvePoint(70, 250,0.3 ,0.5 ,25, Math.toRadians(20), 0.5));
        allPoints.add(new CurvePoint(50 , 230,0.3 ,0.5 ,35, Math.toRadians(30), 0.2));
        allPoints.add(new CurvePoint(50 , 100,0.3 ,0.5 ,35, Math.toRadians(30), 0.2));
        allPoints.add(new CurvePoint(150 , 50,0.3 ,0.5 ,35, Math.toRadians(30), 0.2));
        allPoints.add(new CurvePoint(80 , 150,0.3 ,0.5 ,35, Math.toRadians(30), 0.2));


        RobotMovement.followCurve(allPoints, followAngles);
    }
}
