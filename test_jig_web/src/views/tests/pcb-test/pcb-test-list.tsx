import {
  Box,
  Button,
  Card,
  CardContent,
  Container,
  Divider,
  Grid,
  Pagination,
  Typography,
} from "@mui/material";
import { GridColDef } from "@mui/x-data-grid";
import * as React from "react";
import Paper from "@mui/material/Paper";
import Table from "@mui/material/Table";
import TableBody from "@mui/material/TableBody";
import TableContainer from "@mui/material/TableContainer";
import TableHead from "@mui/material/TableHead";
import { Download } from "@mui/icons-material";
import { useState } from "react";
import { handleGeneratePcbTestExcel } from "./pcb-test-excel";
import GridOnIcon from '@mui/icons-material/GridOn';
import { StyledTableCell, StyledTableRow } from "../../common_components/common";
import SessionTimeoutPopup from "../../common_components/session_logout";
import TableSearchFormCommon from "../../common_components/table_search_form";
import { PcbTest, useGetPcbTestQQuery, useGetPcbTestsMutation } from "../../../services/pcb_test_service";

const columns: GridColDef[] = [
  {
    field: "Select",
    headerName: "Select",
    width: 80,
  },
  {
    field: "testId",
    headerName: "Test ID",
    width: 150,
  },
  {
    field: "serialNumber",
    headerName: "Serial Number",
    width: 150,
  },
  {
    field: "chargePortConnectionStatus",
    headerName: "Charge Port Connection Status",
    width: 250,
  },
  {
    field: "intensityButtonStatus",
    headerName: "Intensity Button Status",
    width: 250,
  },
  {
    field: "runPauseButtonStatus",
    headerName: "Run Pause Button Status",
    width: 250,
  },
  {
    field: "modeButtonStatus",
    headerName: "Mode Button Status",
    width: 250,
  },
  {
    field: "greenLedStatus",
    headerName: "Green Led Status",
    width: 250,
  },
  {
    field: "blueLedStatus",
    headerName: "Blue Led Status",
    width: 250,
  },
  {
    field: "redLedRingStatus",
    headerName: "Red Led Ring Status",
    width: 250,
  },
  {
    field: "greenLedRingStatus",
    headerName: "Green Led Ring Status",
    width: 250,
  },
  {
    field: "blueLedRingStatus",
    headerName: "Blue Led Ring Status",
    width: 250,
  },
  {
    field: "whiteLedRingStatus",
    headerName: "White Led Ring Status",
    width: 250,
  },
  {
    field: "ledRingOffStatus",
    headerName: "Led Ring Off Status",
    width: 250,
  },
  {
    field: "buzzerStatus",
    headerName: "Buzzer Status",
    width: 250,
  },
  {
    field: "latchSwitchLedOnStatus",
    headerName: "Latch Switch Led On Status",
    width: 250,
  },
  {
    field: "latchSwitchOffStatus",
    headerName: "Latch Switch Off Status",
    width: 250,
  },
  {
    field: "latchSwitchLedOffStatus",
    headerName: "Latch Switch Led Off Status",
    width: 250,
  },
  {
    field: "latchSwitchOnStatus",
    headerName: "Latch Switch On Status",
    width: 250,
  },
  {
    field: "chargerPortDisconnectStatus",
    headerName: "Charger Port Disconnect Status",
    width: 250,
  },
  {
    field: "pumpStatus",
    headerName: "Pump Status",
    width: 250,
  },
  {
    field: "batteryChargingStatus",
    headerName: "Battery Charging Status",
    width: 250,
  },
  {
    field: "startBatteryChargingPercentage",
    headerName: "Start Battery Charging Percentage",
    width: 250,
  },
  {
    field: "endBatteryChargingPercentage",
    headerName: "End Battery Charging Percentage",
    width: 250,
  },
  {
    field: "batteryTemperatureStatus",
    headerName: "Battery Temperature Status",
    width: 250,
  },
  {
    field: "batteryTemperature",
    headerName: "Battery Temperature",
    width: 250,
  },
  {
    field: "pressurePathStatus",
    headerName: "Pressure Path Status",
    width: 250,
  },
  {
    field: "startPressurePathValue",
    headerName: "Start Pressure Path Value",
    width: 250,
  },
  {
    field: "endPressurePathValue",
    headerName: "End Pressure Path Value",
    width: 250,
  },
  {
    field: "pressureSensorStatus",
    headerName: "Pressure Sensor Status",
    width: 250,
  },
  {
    field: "startPressureSensorValue",
    headerName: "Start Pressure Sensor Value",
    width: 250,
  },
  {
    field: "endPressureSensorValue",
    headerName: "End Pressure Sensor Value",
    width: 250,
  },
  {
    field: "valve01Status",
    headerName: "Valve 01 Status",
    width: 250,
  },
  {
    field: "valve01StartValue",
    headerName: "Valve 01 Start Value",
    width: 250,
  },
  {
    field: "valve01EndValue",
    headerName: "Valve 01 End Value",
    width: 250,
  },
  {
    field: "valve02Status",
    headerName: "Valve 02 Status",
    width: 250,
  },
  {
    field: "valve02StartValue",
    headerName: "Valve 02 Start Value",
    width: 250,
  },
  {
    field: "valve02EndValue",
    headerName: "Valve 02 End Value",
    width: 250,
  },
  {
    field: "valve03Status",
    headerName: "Valve 03 Status",
    width: 250,
  },
  {
    field: "valve03StartValue",
    headerName: "Valve 03 Start Value",
    width: 250,
  },
  {
    field: "valve03EndValue",
    headerName: "Valve 03 End Value",
    width: 250,
  },
  {
    field: "valve04Status",
    headerName: "Valve 04 Status",
    width: 250,
  },
  {
    field: "valve04StartValue",
    headerName: "Valve 04 Start Value",
    width: 250,
  },
  {
    field: "valve04EndValue",
    headerName: "Valve 04 End Value",
    width: 250,
  },
  {
    field: "valve05Status",
    headerName: "Valve 05 Status",
    width: 250,
  },
  {
    field: "valve05StartValue",
    headerName: "Valve 05 Start Value",
    width: 250,
  },
  {
    field: "valve05EndValue",
    headerName: "Valve 05 End Value",
    width: 250,
  },
  {
    field: "valve06Status",
    headerName: "Valve 06 Status",
    width: 250,
  },
  {
    field: "valve06StartValue",
    headerName: "Valve 06 Start Value",
    width: 250,
  },
  {
    field: "valve06EndValue",
    headerName: "Valve 06 End Value",
    width: 250,
  },
  {
    field: "phaseTwoValve01Status",
    headerName: "Phase Two Valve 01 Status",
    width: 250,
  },
  {
    field: "phaseTwoValve01StartValue",
    headerName: "Phase Two Valve 01 Start Value",
    width: 250,
  },
  {
    field: "phaseTwoValve01EndValue",
    headerName: "Phase Two Valve 01 End Value",
    width: 250,
  },
  {
    field: "phaseTwoValve02Status",
    headerName: "Phase Two Valve 02 Status",
    width: 250,
  },
  {
    field: "phaseTwoValve02StartValue",
    headerName: "Phase Two Valve 02 Start Value",
    width: 250,
  },
  {
    field: "phaseTwoValve02EndValue",
    headerName: "Phase Two Valve 02 End Value",
    width: 250,
  },
  {
    field: "phaseTwoValve03Status",
    headerName: "Phase Two Valve 03 Status",
    width: 250,
  },
  {
    field: "phaseTwoValve03StartValue",
    headerName: "Phase Two Valve 03 Start Value",
    width: 250,
  },
  {
    field: "phaseTwoValve03EndValue",
    headerName: "Phase Two Valve 03 End Value",
    width: 250,
  },
  {
    field: "phaseTwoValve04Status",
    headerName: "Phase Two Valve 04 Status",
    width: 250,
  },
  {
    field: "phaseTwoValve04StartValue",
    headerName: "Phase Two Valve 04 Start Value",
    width: 250,
  },
  {
    field: "phaseTwoValve04EndValue",
    headerName: "Phase Two Valve 04 End Value",
    width: 250,
  },
  {
    field: "phaseTwoValve05Status",
    headerName: "Phase Two Valve 05 Status",
    width: 250,
  },
  {
    field: "phaseTwoValve05StartValue",
    headerName: "Phase Two Valve 05 Start Value",
    width: 250,
  },
  {
    field: "phaseTwoValve05EndValue",
    headerName: "Phase Two Valve 05 End Value",
    width: 250,
  },
  {
    field: "phaseTwoValve06Status",
    headerName: "Phase Two Valve 06 Status",
    width: 250,
  },
  {
    field: "phaseTwoValve06StartValue",
    headerName: "Phase Two Valve 06 Start Value",
    width: 250,
  },
  {
    field: "phaseTwoValve06EndValue",
    headerName: "Phase Two Valve 06 End Value",
    width: 250,
  },
  {
    field: "phaseTwoPressureSensorStatus",
    headerName: "Phase Two Pressure Sensor Status",
    width: 250,
  },
  {
    field: "phaseTwoPumpStatus",
    headerName: "Phase Two Pump Status",
    width: 250,
  },
  {
    field: "status",
    headerName: "Status",
    width: 250,
  },
  {
    field: "dateTime",
    headerName: "Date Time",
    width: 250,
  },
];
export default function PcbTestList() {

  const [page, setPage] = useState(1);
  const [fromDate, setFromDate] = React.useState<Date | null>(null);
  const [toDate, setToDate] = React.useState<Date | null>(null);
  const [pageCount, setPageCount] = React.useState(1);
  const [isFilter, setIsFilter] = useState<boolean>(true);
  const [filterType, setFilterType] = React.useState('');
  const [filterValue, setFilterValue] = React.useState('');
  const [status, setStatus] = React.useState('');
  const [selectedRows, setSelectedRows] = React.useState<PcbTest[]>([]);

  var { data, error, isLoading } = useGetPcbTestQQuery({
    data: {
      filterType: filterType,
      filterValue: filterValue,
      fromDate: fromDate,
      toDate: toDate,
      status: status
    }, page: page.toString()
  })
  const [getAll] = useGetPcbTestsMutation();

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const handleRowClick = (item: PcbTest) => {
    if (selectedRows.includes(item)) {
      setSelectedRows(selectedRows.filter((rowId) => rowId !== item));
    } else {
      setSelectedRows([...selectedRows, item]);
    }
  };

  function setSearchParams(fromDate: Date | null, toDate: Date | null, filterType: string, filterValue: string, status: string) {
    setFilterType(filterType);
    setFilterValue(filterValue);
    setFromDate(fromDate);
    setToDate(toDate);
    setStatus(status);
    setPage(1)
  }

  React.useEffect(() => {
    if (data?.data != null) {
      setPageCount(Math.trunc((data.data.totalRecords + 15 - 1) / 15))
    }
  }, [data])

  if (isLoading && isFilter) {
    return <div>Loading...</div>;
  }
  if (error != null && "status" in error) {
    if (error.status == 401 && error.data == null) {
      return <SessionTimeoutPopup />;
    } else {
      return <></>;
    }
  } else {
    return (
      <Box
        component="main"
        sx={{
          flexGrow: 1,
        }}
      >
        <Container maxWidth={false}>
          <>
            <Box m="0px 0 0 0" sx={{}}>
              <Card sx={{ maxWidth: 1600, backgroundColor: "#FFFFFF", boxShadow: "1px 1px 10px 10px #e8e8e8" }}>
                <CardContent sx={{}}>
                  <Grid container spacing={{ xs: 2, md: 3 }} columns={{ xs: 4, sm: 8, md: 12 }}>
                    <Grid item xs={4} sm={4} md={6} >
                      <Typography gutterBottom variant="h5" component="div" color="grey">
                        Pcb Test Results
                      </Typography>
                    </Grid>
                    <Grid item xs={4} sm={4} md={6} >
                      <Box display="flex" justifyContent="flex-end">
                        <Button variant="contained" startIcon={<Download />} color="success" onClick={() =>
                          handleGeneratePcbTestExcel(selectedRows)
                        } disabled={selectedRows.length == 0}>
                          Download selected
                        </Button>
                        <Button sx={{ ml: 2 }} variant="contained" startIcon={<GridOnIcon />} color="success" onClick={() => {
                          getAll({
                            data: {
                              filterType: filterType,
                              filterValue: filterValue,
                              fromDate: fromDate,
                              toDate: toDate,
                              status: status
                            }, page: "all"
                          }).unwrap()
                            .then((payload) => {
                              handleGeneratePcbTestExcel(payload.data!.pcbTests)
                            });
                        }}>
                          Download
                        </Button>
                      </Box>
                    </Grid>
                  </Grid>
                  <Divider
                    sx={{
                      borderColor: 'grey',
                      my: 1.5,
                      borderStyle: 'dashed'
                    }}
                  />
                  <TableSearchFormCommon
                    searchFun={setSearchParams}
                  />
                  <Divider
                    sx={{
                      borderColor: 'grey',
                      my: 0.5,
                      borderStyle: 'dashed'
                    }}
                  />
                  <Grid container spacing={2}>
                    <Grid item xs={12}>
                    </Grid>
                  </Grid>
                  <Divider
                    sx={{
                      borderColor: "grey",
                      my: 1.5,
                      borderStyle: "dashed",
                    }}
                  />
                  <Paper sx={{ width: "100%", overflow: "hidden" }}>
                    <TableContainer sx={{ maxHeight: 440 }}>
                      <Table stickyHeader aria-label="sticky table">
                        <TableHead sx={{ backgroundColor: "#9e9e9e" }}>
                          <StyledTableRow>
                            {columns.map((column) => (
                              <StyledTableCell
                                key={column.headerName}
                                align={column.align}
                                style={{
                                  minWidth: column.width,
                                }}
                              >
                                {column.headerName}
                              </StyledTableCell>
                            ))}
                          </StyledTableRow>
                        </TableHead>
                        <TableBody>
                          {data?.data?.pcbTests
                            .map((box) => {
                              return (
                                <StyledTableRow
                                  hover
                                  role="checkbox"
                                  tabIndex={-1}
                                  key={box.testId}
                                  onClick={() => handleRowClick(box)}
                                  selected={selectedRows.includes(box)}
                                >
                                  <StyledTableCell align={"left"}>
                                    <input
                                      type="checkbox"
                                      checked={selectedRows.includes(box)}
                                    />
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.testId}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.serialNumber}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.chargePortConnectStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.intensityButtonStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.runPauseButtonStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.modeButtonStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.greenLedStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.blueLedStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.redLedRingStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.greenLedRingStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.blueLedRingStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.whiteLedRingStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.ledRingOffStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.buzzerStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.latchSwitchLedOnStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.latchSwitchOffStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.latchSwitchLedOffStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.latchSwitchOnStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.chargerPortDisconnectStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.pumpStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.batteryChargingStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.startBatteryChargingPercentage}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.endBatteryChargingPercentage}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.batteryTemperatureStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.batteryTemperature}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.pressurePathStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.startPressurePathValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.endPressurePathValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.pressureSensorStatus ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.startPressureSensorValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.endPressureSensorValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve01Status ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve01StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve01EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve02Status ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve02StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve02EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve03Status ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve03StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve03EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve04Status ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve04StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve04EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve05Status ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve05StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve05EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve06Status ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve06StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve06EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve01Status != null ? (box.phaseTwoValve01Status ? "Pass" : "Fail") : ""}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve01StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve01EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve02Status != null ? (box.phaseTwoValve02Status ? "Pass" : "Fail") : ""}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve02StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve02EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve03Status != null ? (box.phaseTwoValve03Status ? "Pass" : "Fail") : ""}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve03StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve03EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve04Status != null ? (box.phaseTwoValve04Status ? "Pass" : "Fail") : ""}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve04StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve04EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve05Status != null ? (box.phaseTwoValve05Status ? "Pass" : "Fail") : ""}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve05StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve05EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve06Status != null ? (box.phaseTwoValve06Status ? "Pass" : "Fail") : ""}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve06StartValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoValve06EndValue}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoPressureSensorStatus != null ? (box.phaseTwoPressureSensorStatus ? "Pass" : "Fail") : ""}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.phaseTwoPumpStatus != null ? (box.phaseTwoPumpStatus ? "Pass" : "Fail") : ""}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.status ? "Pass" : "Fail"}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.dateTime}
                                  </StyledTableCell>
                                </StyledTableRow>
                              );
                            })}
                        </TableBody>
                      </Table>
                    </TableContainer>
                  </Paper>
                  {pageCount != 0 ?
                    <Box display="flex" justifyContent="flex-end">
                      <Pagination count={pageCount} sx={{ mt: 2 }} variant="outlined" shape="rounded" page={page} onChange={handleChange} />
                    </Box>
                    : <></>
                  }
                </CardContent>
              </Card>
            </Box>
          </>
        </Container>
      </Box>
    )
  }
}
