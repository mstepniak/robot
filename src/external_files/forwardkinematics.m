function FK = forwardkinematics(theta1,theta2,theta3,theta4)

RotZ1 = ([cosd(theta1) -sind(theta1) 0 0;
       sind(theta1) cosd(theta1) 0 0;
       0 0 1 0;
       0 0 0 1;]);
TransD1 = ([1 0 0 0;
           0 1 0 0;
           0 0 1 320;
           0 0 0 1;]);
RotX1 = ([1 0 0 0;
       0 cosd(-90) -sind(-90) 0;
       0 sind(-90) cosd(-90) 0;
       0 0 0 1;]);

TransX1 = ([1 0 0 0;
           0 1 0 0;
           0 0 1 0;
           0 0 0 1;]);
       
RotZ2 = ([cosd(theta2-90) -sind(theta2-90) 0 0;
       sind(theta2-90) cosd(theta2-90) 0 0;
       0 0 1 0;
       0 0 0 1;]);
TransD2 = ([1 0 0 0;
           0 1 0 0;
           0 0 1 35;
           0 0 0 1;]);
RotX2 = ([1 0 0 0;
       0 cosd(0) -sind(0) 0;
       0 sind(0) cosd(0) 0;
       0 0 0 1;]);

TransX2 = ([1 0 0 225;
           0 1 0 0;
           0 0 1 0;
           0 0 0 1;]);
       
RotZ3 = ([cosd(theta3+90) -sind(theta3+90) 0 0;
       sind(theta3+90) cosd(theta3+90) 0 0;
       0 0 1 0;
       0 0 0 1;]);
TransD3 = ([1 0 0 0;
           0 1 0 0;
           0 0 1 0;
           0 0 0 1;]);
RotX3 = ([1 0 0 0;
       0 cosd(90) -sind(90) 0;
       0 sind(90) cosd(90) 0;
       0 0 0 1;]);

TransX3 = ([1 0 0 0;
           0 1 0 0;
           0 0 1 0;
           0 0 0 1;]);
       
 RotZ4 = ([cosd(theta4) -sind(theta4) 0 0;
       sind(theta4) cosd(theta4) 0 0;
       0 0 1 0;
       0 0 0 1;]);
TransD4 = ([1 0 0 0;
           0 1 0 0;
           0 0 1 225;
           0 0 0 1;]);
RotX4 = ([1 0 0 0;
       0 cosd(-90) -sind(-90) 0;
       0 sind(-90) cosd(-90) 0;
       0 0 0 1;]);

TransX4 = ([1 0 0 0;
           0 1 0 0;
           0 0 1 0;
           0 0 0 1;]);      
       
 RotZ5 = ([cosd(0) -sind(0) 0 0;
       sind(0) cosd(0) 0 0;
       0 0 1 0;
       0 0 0 1;]);
TransD5 = ([1 0 0 0;
           0 1 0 0;
           0 0 1 46.5;
           0 0 0 1;]);
RotX5 = ([1 0 0 0;
       0 cosd(90) -sind(90) 0;
       0 sind(90) cosd(90) 0;
       0 0 0 1;]);

TransX5 = ([1 0 0 0;
           0 1 0 0;
           0 0 1 0;
           0 0 0 1;]);  
 

A1 = RotZ1*TransD1*TransX1*RotX1;
A2 = RotZ2*TransD2*TransX2*RotX2;
A3 = RotZ3*TransD3*TransX3*RotX3;
A4 = RotZ4*TransD4*TransX4*RotX4;
A5 = RotZ5*TransD5*TransX5*RotX5;

T15 = A1*A2*A3*A4*A5;

pitch = asind(-T15(3,1));
roll= asind(T15(2,1)/cosd(pitch));
yaw = acosd(T15(3,3)/cosd(pitch));

FK = [pitch, roll, yaw];
end