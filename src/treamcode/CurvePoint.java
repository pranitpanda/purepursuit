package treamcode;

import org.opencv.core.Point;

public class CurvePoint {
    public double x;
    public double y;
    public double moveSpeed;
    public double turnSpeed;
    public double followDistance;
    public double pointLength;
    public double slowDownTurnRadians;
    public double slowDownTurnAmount;

    public CurvePoint(double x, double y, double moveSpeed, double turnSpeed, double followDistance, double slowDownTurnRadians, double slowDownTurnAmount){

        this.x = x;
        this.y = y;
        this.moveSpeed = moveSpeed;
        this.turnSpeed = turnSpeed;
        this.followDistance = followDistance;
        this.slowDownTurnRadians = slowDownTurnRadians;
        this.slowDownTurnAmount = slowDownTurnAmount;
    }

    public CurvePoint(CurvePoint thisPoint){
        this.x = thisPoint.x;
        this.y = thisPoint.y;
        this.moveSpeed = thisPoint.moveSpeed;
        this.turnSpeed = thisPoint.turnSpeed;
        this.followDistance = thisPoint.followDistance;
        this.slowDownTurnRadians = thisPoint.slowDownTurnRadians;
        this.slowDownTurnAmount = thisPoint.slowDownTurnAmount;
        this.pointLength = thisPoint.pointLength;

    }
    public Point toPoint(){
        return new Point(x,y);
    }
    public void setPoint(Point p){
        x = p.x;
        y = p.y;
    }

    public void setCharacteristics(CurvePoint c){
        moveSpeed = c.moveSpeed;
        turnSpeed = c.turnSpeed;
        followDistance = c.followDistance;
        pointLength = c.pointLength;
        slowDownTurnRadians = c.slowDownTurnRadians;
        slowDownTurnAmount = c.slowDownTurnAmount;
    }

}
