clc
clear

%import of data
filename = 'robot.log';
delimiterIn = ' ';
headerlinesIn = 0;
Robot = importdata(filename,delimiterIn,headerlinesIn);
filename = 'sensor.log';
delimiterIn = ' ';
headerlinesIn = 0;
Sensor = importdata(filename,delimiterIn,headerlinesIn);

%left for future reference - to be deleted in final version

% for i = 1:robotSizeV
%     %A(i,:) = forwardkinematics(data(i,1),data(i,2),data(i,3),data(i,4));
%     A(i,:) = forwardkinematics(Robot.data(i,1),Robot.data(i,2),Robot.data(i,3),Robot.data(i,4));
%     Robot.data(i,7) = real(A (i,1));
%     Robot.data(i,8) = real(A (i,2));
%     Robot.data(i,9) = real(A (i,3));   
% end

%synchronisation of results from both sources
robotSyncpos = 0;
sensorSyncpos = 0;
for k=4:20

    S1 = Robot.textdata(k,2);
    for l=4:20
        S2 = Sensor.textdata(l,2);
        if (strcmp(S1,S2) == 1)
            flag=1; 
            robotSyncpos = k;
            sensorSyncpos = l;
            break
        end
    end
          if(flag==1)
          break
          end
end

%removal of unnecessary rows
for q=robotSyncpos:-1:1
    Robot.data(q,:)=[];
    Robot.textdata(q,:)=[];
end

for w=sensorSyncpos:-1:1
    Sensor.data(w,:)=[];
    Sensor.textdata(w,:)=[];
end

%performing of forward kinematics, saving results to commons file and
%comparision
[robotSizeV, robotSizeH] = size(Robot.data);
A = zeros(robotSizeV,3);
B = zeros(robotSizeV,3);
RESULT = zeros(robotSizeV, 9);
for i = 2:robotSizeV
    
    %A(i,:) = forwardkinematics(data(i,1),data(i,2),data(i,3),data(i,4));
    A(i,:) = forwardkinematics(Robot.data(i,1),Robot.data(i,2),Robot.data(i,3),Robot.data(i,4));
    B(i,:) = forwardkinematics(Robot.data(i-1,1),Robot.data(i-1,2),Robot.data(i-1,3),Robot.data(i-1,4));
    robotData1 = Robot.textdata{i,2};
    robotData2 = Robot.textdata{i-1,2};
    c1 = str2double(robotData1(end-8:end));
    c2 = str2double(robotData2(end-8:end));
    dt=c1-c2;
    droll = real(A(i,1)) - real(B(i,1));
    dyaw = real(A(i,2)) - real(B(i,2));
    dpitch = real(A(i,2)) - real(B(i,2));
%     Robot.data(i,7) = droll/dt;
%     Robot.data(i,8) = dyaw/dt;
%     Robot.data(i,9) = dpitch/dt;
    RESULT(i,1) = droll/dt;
    RESULT(i,2) = dyaw/dt;
    RESULT(i,3) = dpitch/dt;
    %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    % Sensitivity
    % 145.6 if measurement is taken at +-225 deg/sec
    % 72.8 if measurement is taken at +-450s deg/sec
    sens = 145.6;
    %sens = 72.8
    RESULT(i,4) = Sensor.data(i,1)/sens;
    RESULT(i,5) = Sensor.data(i,2)/sens;
    RESULT(i,6) = Sensor.data(i,3)/sens;
    RESULT(i,7) = RESULT(i,1) - RESULT(i,4);
    RESULT(i,8) = RESULT(i,2) - RESULT(i,5);
    RESULT(i,9) = RESULT(i,3) - RESULT(i,6);
end