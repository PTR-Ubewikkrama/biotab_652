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
import { handleGenerateHHDeviceExcel } from "./hh_device-excel";
import GridOnIcon from '@mui/icons-material/GridOn';
import { DeviceHHDto, useGetHHDevicesMutation, useGetHHDevicesQQuery } from "../../services/hh_device_service";
import SessionTimeoutPopup from "../common_components/session_logout";
import { StyledTableCell, StyledTableRow } from "../common_components/common";
import TableSearchFormCustom from "../common_components/table_search_form_custom";

const columns: GridColDef[] = [
  {
    field: "Select",
    headerName: "Select",
    width: 80,
  },
  {
    field: "deviceCode",
    headerName: "Device Code",
    width: 150,
  },
  {
    field: "deviceCodeStatus",
    headerName: "Device Code Status",
    width: 150,
  },
  {
    field: "pcbTestCode",
    headerName: "PCB Test Code",
    width: 150,
  },
  {
    field: "pcbTestCodeStatus",
    headerName: "PCB Test Code Status",
    width: 150,
  },
  {
    field: "valveTestOneCode",
    headerName: "Valve Test One Code",
    width: 150,
  },
  {
    field: "valveTestOneCodeStatus",
    headerName: "Valve Test One Code Status",
    width: 150,
  },
  {
    field: "valveTestTwoCode",
    headerName: "Valve Test Two Code",
    width: 150,
  },
  {
    field: "valveTestTwoCodeStatus",
    headerName: "Valve Test Two Code Status",
    width: 150,
  },
  {
    field: "airPumpTestCode",
    headerName: "Air Pump Test Code",
    width: 150,
  },
  {
    field: "airPumpTestCodeStatus",
    headerName: "Air Pump Test Code Status",
    width: 150,
  },
  {
    field: "latchButtonTestCode",
    headerName: "Latch Button Test Code",
    width: 150,
  },
  {
    field: "latchButtonTestCodeStatus",
    headerName: "Latch Button Test Code Status",
    width: 150,
  },
  {
    field: "overPressureValveTestCode",
    headerName: "Over Pressure Valve Test Code",
    width: 150,
  },
  {
    field: "overPressureValveTestCodeStatus",
    headerName: "Over Pressure Valve Test Code Status",
    width: 150,
  },
  {
    field: "batteryTestCode",
    headerName: "Battery Test Code",
    width: 150,
  },
  {
    field: "batteryTestCodeStatus",
    headerName: "Battery Test Code Status",
    width: 150,
  },
  {
    field: "enclosureCode",
    headerName: "Enclosure Code",
    width: 150,
  },
  {
    field: "enclosureCodeStatus",
    headerName: "Enclosure Code Status",
    width: 150,
  },
  {
    field: "airBladderCode",
    headerName: "Air Bladder Code",
    width: 150,
  },
  {
    field: "airBladderCodeStatus",
    headerName: "Air Bladder Code Status",
    width: 150,
  },
  {
    field: "powerSupplyTestCode",
    headerName: "Power Supply Test Code",
    width: 150,
  },
  {
    field: "powerSupplyTestCodeStatus",
    headerName: "Power Supply Test Code Status",
    width: 150,
  },
  {
    field: "createdBy",
    headerName: "Created By",
    width: 150,
  },
  {
    field: "dateTime",
    headerName: "Date Time",
    width: 150,
  },
];
export default function HHDeviceList() {

  const [page, setPage] = useState(1);
  const [fromDate, setFromDate] = React.useState<Date | null>(null);
  const [pageCount, setPageCount] = React.useState(1);
  const [isFilter, setIsFilter] = useState<boolean>(true);
  const [filterType, setFilterType] = React.useState('');
  const [filterValue, setFilterValue] = React.useState('');
  const [selectedRows, setSelectedRows] = React.useState<DeviceHHDto[]>([]);

  var { data, error, isLoading } = useGetHHDevicesQQuery({
    data: {
      filterType: filterType,
      filterValue: filterValue,
      fromDate: fromDate,
    }, page: page.toString()
  })
  const [getAll] = useGetHHDevicesMutation();

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const handleRowClick = (item: DeviceHHDto) => {
    if (selectedRows.includes(item)) {
      setSelectedRows(selectedRows.filter((rowId) => rowId !== item));
    } else {
      setSelectedRows([...selectedRows, item]);
    }
  };

  function setSearchParams(fromDate: Date | null, filterType: string, filterValue: string) {
    setFilterType(filterType);
    setFilterValue(filterValue);
    setFromDate(fromDate);
    setPage(1)
  }

  React.useEffect(() => {
    if (data?.data != null) {
      setPageCount(Math.trunc((data.data.total + 15 - 1) / 15))
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
                        HH Devices
                      </Typography>
                    </Grid>
                    <Grid item xs={4} sm={4} md={6} >
                      <Box display="flex" justifyContent="flex-end">
                        <Button variant="contained" startIcon={<Download />} color="success" onClick={() =>
                          handleGenerateHHDeviceExcel(selectedRows)
                        } disabled={selectedRows.length == 0}>
                          Download selected
                        </Button>
                        <Button sx={{ ml: 2 }} variant="contained" startIcon={<GridOnIcon />} color="success" onClick={() => {
                          getAll({
                            data: {
                              filterType: filterType,
                              filterValue: filterValue,
                              fromDate: fromDate
                            }, page: "all"
                          }).unwrap()
                            .then((payload) => {
                              handleGenerateHHDeviceExcel(payload.data!.devices)
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
                  <TableSearchFormCustom
                    dropDownItems={{
                      "ALL": "ALL", "CODE": "DEVICE CODE", "PCB": "PCB TEST CODE",
                      "VALVE_ONE": "VALVE TEST ONE CODE",
                      "VALVE_TWO": "VALVE TEST TWO CODE",
                      "AIR_PUMP": "AIR PUMP TEST CODE",
                      "LATCH_BUTTON": "LATCH BUTTON TEST CODE",
                      "OVER_PRESSURE_VALVE": "OVER PRESSURE VALVE TEST CODE",
                      "BATTERY": "BATTERY TEST CODE",
                      "ENCLOSURE": "ENCLOSURE CODE",
                      "AIR_BLADDER": "AIR BLADDER CODE",
                      "POWER_SUPPLY": "POWER SUPPLY TEST CODE"
                    }}
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
                          {data?.data?.devices
                            .map((box) => {
                              return (
                                <StyledTableRow
                                  hover
                                  role="checkbox"
                                  tabIndex={-1}
                                  key={box.deviceId}
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
                                    {box.deviceCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.deviceCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.pcbTestCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.pcbTestCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valveTestOneCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valveTestOneCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valveTestTwoCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valveTestTwoCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.airPumpTestCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.airPumpTestCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.latchButtonTestCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.latchButtonTestCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.overPressureValveTestCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.overPressureValveTestCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.batteryTestCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.batteryTestCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.enclosureCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.enclosureCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.airBladderCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.airBladderCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.powerSupplyTestCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.powerSupplyTestCodeStatus}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.createdBy}
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
