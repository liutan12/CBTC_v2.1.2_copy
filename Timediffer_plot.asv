close all;
clear all;
clc;
name='differ1523935541657.csv';
t1=xlsread(name,'A:A');
d1=xlsread(name,'B:B');
t2=ceil(t1/1000000);
d2=ceil(d1/1000000);
figure(1);
plot(t1,d1);
xlabel('时间（ns）'),ylabel('固有时间差(ns)');
title('differ time');
hold on;
figure(2);
plot(t2,d2);
xlabel('时间（ms）'),ylabel('固有时间差(ms)');
title('differ time');
hold on;



for i=1:100000
    if i>1
        maxdiff=0;
        idx=1;
        for j=1:length(d1);
            tmp=abs(d1(j)-(k.*t1(j)+b));
            if tmp>maxdiff
                maxdiff=tmp;
                idx=j;
            end;
        end;
        if maxdiff<100000
            break;
        end;
        d1(idx)=[];
        t1(idx)=[];
    end;
    halflen=floor(length(d1)/2);
    dm1=0;
    dm2=0;
    tm1=0;
    tm2=0;
    for j=1:halflen
        dm1=dm1+d1(j)/halflen;
        dm2=dm2+d1(j+halflen)/halflen;
        tm1=tm1+t1(j)/halflen;
        tm2=tm2+t1(j+halflen)/halflen;
    end;
    k=(dm2-dm1)/(tm2-tm1);
    b=dm1-k.*tm1;
    fprintf('dm1=%f ,dm2=%f ,tm1=%f ,tm2=%f ,k=%f ,b=%f \n',dm1,dm2,tm1,tm2,k,b);
end;

figure(3);
plot(t1,d1);
xlabel('时间（ns）'),ylabel('固有时间差(ns)');
title('differ time');
hold on;
y1=k.*t1+b;
plot(t1,y1);
hold on;

figure(4);
d2=round(d1/1000000);
t2=round(t1/1000000);
plot(t2,d2);
xlabel('时间（ms）'),ylabel('固有时间差(ms)');
title('differ time');
hold on;
y2=k.*t2+round(b/1000000);
plot(t2,y2);
hold on;
   
        
        
        
        
