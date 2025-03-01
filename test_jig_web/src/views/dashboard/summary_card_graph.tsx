import * as React from 'react';
import { LineChart, lineElementClasses } from '@mui/x-charts/LineChart';
import { Card, CardContent, } from '@mui/material';

const uData = [4000, 3000, 2000, 2780, 1890, 2390, 3490];
const pData = [2400, 1398, 9800, 3908, 4800, 3800, 4300];
const amtData = [2400, 2210, 0, 2000, 2181, 2500, 2100];
const xLabels = [
  'Page A',
  'Page B',
  'Page C',
  'Page D',
  'Page E',
  'Page F',
  'Page G',
];

export default function SummeryCardGraph() {
  return (
    <Card
        sx={{
          maxWidth: 1600, backgroundColor: "#24325f",
          display:"flex", justifyContent: "center", 
          // boxShadow: "1px 1px 10px 10px #e8e8e8",
          transition: 'transform 0.3s',
          borderRadius: '20px',
          '&:hover': {
            transform: 'scale(1.05)',
            borderRadius: '20px'
          },
        }}
      // onClick={() => navigate(path)}
      >
    <CardContent sx={{ m: 0, p: 0 }}>
    <LineChart
      width={500}
      height={300}
      series={[
        { data: uData, label: 'uv', area: true, stack: 'total', showMark: false },
        { data: pData, label: 'pv', area: true, stack: 'total', showMark: false },
        {
          data: amtData,
          label: 'amt',
          area: true,
          stack: 'total',
          showMark: false,
        },
      ]}
      xAxis={[{ scaleType: 'point', data: xLabels }]}
      sx={{
        [`& .${lineElementClasses.root}`]: {
          display: 'none',
        },
      }}
    />
    </CardContent>
    </Card>
  );
}
