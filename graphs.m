close all; clear; 

% Part 1 - Pie Chart
[numData,textData] = xlsread('part1.csv');
pie(numData, textData);
title('Graph 1 - AS Classification');
figure

% Part 2 - Histogram
[numDataHistogram] = xlsread('part2.csv');
edges = [1 2 5 100 200 1000 Inf];
histogram(numDataHistogram(:,2), edges);
title('Graph 2 - Node Degree Histogram');
[a,b] = size(numDataHistogram);
ylim([0 a])
xlim([1 1200]);