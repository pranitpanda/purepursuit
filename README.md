# purepursuit
My take on the pure pursuit algorithm. This algorithm takes in an array of points and then allows a robot to follow a smooth path that goes through all those points.
Several times each second, the algorithm sets a "follow point". This is a point along the path that is determined to be ahead of the robot. The robot motor powers are then calculated according to move the robot to that point. The algorithm is self-correcting in that even if the robot is moved off the path, the motor powers calculated will be adjusted to set it back on the track.
Uses vector math to calculate where along the path the robot is
