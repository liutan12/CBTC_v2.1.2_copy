close all;
clear all;
clc;
name='differ15252696315632.csv';
d1=xlsread(name,'A:A')/1000;
figure(1);
plot(d1-10000);
xlabel('packet'),ylabel('固有时间差(ms)');
title('differ time');
hold on;

% for i=1:100000
%     if i>1
%         maxdiff=0;
%         idx=1;
%         for j=1:length(d1);
%             tmp=abs(d1(j)-dm1);
%             if tmp>maxdiff
%                 maxdiff=tmp;
%                 idx=j;
%             end;
%         end;
%         if maxdiff<0.5
%             break;
%         end;
%         d1(idx)=[];
%     end;
%     len=length(d1);
%     dm1=0;
%     for j=1:len
%         dm1=dm1+d1(j)/len;
%     end;
% end;
% 
% figure(2);
% plot(d1);
% xlabel('packet'),ylabel('固有时间差(ms)');
% title('differ time');
% hold on;
