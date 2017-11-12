close all; clear; 

% Part 1 - Pie Chart
figure;
[numData,textData] = xlsread('part1.csv');
pie(numData);
title('Graph 1 - AS Classification');
legend(textData)

% Part 2 - Histogram
% Changed to get correct format and normalize
figure;
[numDataHistogram] = xlsread('part2.csv');
[a,b] = size(numDataHistogram);
edges = [1 2 5 100 200 1000 Inf];
binDescription = {'1','2-5','5-100','100-200','200-1000','>1000'};  
bin(1) = sum(numDataHistogram(:,2)  == 1);
bin(2) = sum(numDataHistogram(:,2) > 2 & numDataHistogram(:,2) < 5);
bin(3) = sum(numDataHistogram(:,2) > 5 & numDataHistogram(:,2) < 100);
bin(4) = sum(numDataHistogram(:,2) > 100 & numDataHistogram(:,2) < 200);
bin(5) = sum(numDataHistogram(:,2) > 200 & numDataHistogram(:,2) < 1000);
bin(6) = sum(numDataHistogram(:,2) > 1000);
h = bar(bin/a, 'hist');
set(gca, 'xticklabel', binDescription);
set(h,'FaceColor', [0.5843 0.8157 0.9882],'EdgeColor','black');
%c = histc(numDataHistogram(:,2), 'BinEdges'edges);
%bar(edges, c)
%histogram(numDataHistogram(:,2), 6, 'Normalization', 'count', 'BinEdges', edges);
%histogram('BinEdges', edges, 'BinCounts', bin/a);
title('Graph 2 - Node Degree Histogram');
xlabel('AS node degree')
ylabel('Count normalized to number of ASes')
%ylim([0 a])
%xlim([1 1200]);

% Part 3 - Histogram
figure;
[numDataHistogram] = xlsread('part3.csv');
edges = 2.^(0:20-1);
edges = [0, edges, Inf];
histogram(numDataHistogram, edges);
title('Graph 3 - IP Space Histogram');
xlabel('IP Space')
ylabel('Count')
%ylim([0 2^32])
xlim([0 10000]);

% Part 4 - Pie Chart
figure;
[numData,textData] = xlsread('part4.csv');
pie(numData);
title('Graph 4 - AS Classification');
legend(textData)

