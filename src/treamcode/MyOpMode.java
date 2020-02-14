package treamcode;

import java.util.ArrayList;

public class MyOpMode extends OpMode {
    public void init(){

    }
    public void loop(){
        ArrayList<CurvePoint> allPoints = new ArrayList<>();
        allPoints.add(new CurvePoint(0 , 100,0.25 ,0.5 ,50, 30, 0.5));
        allPoints.add(new CurvePoint(100 , 100,0.25 ,0.5 ,50, 30, 0.5));
        allPoints.add(new CurvePoint(150 , 80,0.25 ,0.5 ,50, 30, 0.5));
        allPoints.add(new CurvePoint(50 , 180,0.25 ,0.5 ,50, 30, 0.5));

        RobotMovement.followCurve(allPoints, Math.toRadians(90));
    }
}
