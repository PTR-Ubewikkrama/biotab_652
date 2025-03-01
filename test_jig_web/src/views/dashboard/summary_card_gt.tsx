import {
  Avatar,
  Box,
  Button,
  Card,
  CardContent,
  Container,
  FormControl,
  Grid,
  Typography,
} from "@mui/material";
import React, { ReactNode, useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import {
  CartesianGrid,
  Legend,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
// import {
//   GetDashboardSummeryResponse,
//   useGetBatterySummeryQuery,
// } from "../../services/battery_service";
import SessionTimeoutPopup from "../common_components/session_logout";
import { DatePicker, LocalizationProvider } from "@mui/x-date-pickers";
import { AdapterDayjs } from "@mui/x-date-pickers/AdapterDayjs";

interface DataPoint {
  name: string;
  current: number;
  past: number;
}

export function GraphSummaryCardBattery() {
  const navigate = useNavigate();
  const [frequency, setFrequency] = useState("WEEKLY");
  const [selectedFromDate, setSelectedFromDate] = useState<Date | null>(null);

  const handleFromDateChange = (date: Date | null) => {
    setSelectedFromDate(date);
  };

  const [selectedToDate, setSelectedToDate] = useState<Date | null>(null);

  const handleToDateChange = (date: Date | null) => {
    setSelectedToDate(date);
  };

  const handleSelect = (option: string) => {
    setFrequency(option);
  };

  // var { data, error, isLoading } = useGetBatterySummeryQuery({
  //   data: { fromDate: selectedFromDate, toDate: selectedToDate },
  // });

  // function getData(params: GetDashboardSummeryResponse) {
  //   return params.current.map((currentItem) => {
  //     const pastItem = params.past.find(
  //       (pastItem) => pastItem.date === currentItem.date
  //     ) || { count: 0 };
  //     return {
  //       name: currentItem.date,
  //       current: currentItem.count,
  //       past: pastItem.count,
  //     };
  //   });
  // }

  function isFutureDate(date: Date) {
    const today = new Date();
    return (
      date > today || (selectedFromDate != null && date < selectedFromDate)
    );
  }

  function isValidDate(date: Date) {
    const today = new Date();
    return date > today || (selectedToDate != null && date > selectedToDate);
  }

  // if (isLoading) {
  //   return <div>Loading...</div>;
  // }

  return (
    <Card
      sx={{
        maxWidth: 2000,
        backgroundColor: "#FFFFFF",
        boxShadow: "1px 1px 10px 10px #e8e8e8",
        transition: "transform 0.3s",
        "&:hover": {
          transform: "scale(1.01)", // Adjust the scaling factor as desired
        },
      }}
    // onClick={() => navigate(path)}
    >
      <CardContent>
        <Grid container sx={{ justifyContent: "space-between" }}>
          <Grid item xs={12} sm={12} md={12}>
            <Typography
              sx={{ mt: 1, mb: 1 }}
              color="grey"
              gutterBottom
              variant="h6"
            >
              Battery manufacturing Summery
            </Typography>
            <Typography
              sx={{ mb: 2, color: "grey", fontSize: 11 }}
              color="textPrimary"
            >
              Select from and to date & at least two days gap should be there
            </Typography>
          </Grid>
          <Grid item xs={6} sm={6} md={6} sx={{ mb: 1 }}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                label="Select From Date"
                value={selectedFromDate}
                onChange={handleFromDateChange}
                shouldDisableDate={isValidDate}
              />
            </LocalizationProvider>
          </Grid>
          <Grid item xs={6} sm={6} md={6}>
            <LocalizationProvider dateAdapter={AdapterDayjs}>
              <DatePicker
                label="Select To Date"
                value={selectedToDate}
                onChange={handleToDateChange}
                shouldDisableDate={isFutureDate}
              />
            </LocalizationProvider>
          </Grid>
          <Typography
            sx={{ mt: 1, mb: 5 }}
            color="grey"
            gutterBottom
            variant="h6"
          >
            {/* Total : {data!.total} */}
          </Typography>
          <Grid item xs={12} sm={12} md={12}>
            <Container>
              <LineChart
                width={550}
                height={400}
                // data={getData(data!)}
                margin={{
                  top: 20,
                  right: 5,
                  left: 5,
                  bottom: 5,
                }}
              >
                <CartesianGrid strokeDasharray="4 4" />
                <XAxis dataKey="name" />
                <YAxis />
                <Tooltip />
                <Legend />

                {/* Line for "past" data (dashed) */}
                {/* <Line type="monotone" dataKey="past" name="Past" stroke="#8884d8" strokeDasharray="5 5" /> */}

                {/* Line for "current" data (not dashed) */}
                <Line
                  type="monotone"
                  dataKey="current"
                  name="Current"
                  stroke="#82ca9d"
                />
              </LineChart>
            </Container>
          </Grid>
        </Grid>
      </CardContent>
    </Card>
  );
}
