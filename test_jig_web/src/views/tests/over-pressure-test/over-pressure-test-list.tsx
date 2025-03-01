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
import { handleGenerateOverPressureTesExcel } from "./over-pressure-test-excel";
import GridOnIcon from '@mui/icons-material/GridOn';
import { StyledTableCell, StyledTableRow } from "../../common_components/common";
import SessionTimeoutPopup from "../../common_components/session_logout";
import TableSearchFormCommon from "../../common_components/table_search_form";
import { OverPressureTest, useGetOverPressureQQuery, useGetOverPressureTestsMutation } from "../../../services/over_pressure_test_service";

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
    field: "deviceMac",
    headerName: "Device MAC",
    width: 150,
  },
  {
    field: "qrCode",
    headerName: "QR Code",
    width: 150,
  },
  {
    field: "maxPressure",
    headerName: "Max Pressure",
    width: 150,
  },
  {
    field: "maxPressureTime",
    headerName: "Max Pressure Time",
    width: 150,
  },
  {
    field: "maxPressureFlowRate",
    headerName: "Max Pressure Flow Rate",
    width: 150,
  },
  {
    field: "normalPressure",
    headerName: "Normal Pressure",
    width: 150,
  },
  {
    field: "normalPressureTime",
    headerName: "Normal Pressure Time",
    width: 150,
  },
  {
    field: "normalPressureFlowRate",
    headerName: "Normal Pressure Flow Rate",
    width: 150,
  },
  {
    field: "overPressureValveStatus",
    headerName: "Over Pressure Valve Status",
    width: 150,
  },
  {
    field: "status",
    headerName: "Status",
    width: 150,
  },
  {
    field: "dateTime",
    headerName: "Date Time",
    width: 150,
  },
];
export default function OverPressureTestList() {

  const [page, setPage] = useState(1);
  const [fromDate, setFromDate] = React.useState<Date | null>(null);
  const [toDate, setToDate] = React.useState<Date | null>(null);
  const [pageCount, setPageCount] = React.useState(1);
  const [isFilter, setIsFilter] = useState<boolean>(true);
  const [filterType, setFilterType] = React.useState('');
  const [filterValue, setFilterValue] = React.useState('');
  const [status, setStatus] = React.useState('');
  const [selectedRows, setSelectedRows] = React.useState<OverPressureTest[]>([]);

  var { data, error, isLoading } = useGetOverPressureQQuery({
    data: {
      filterType: filterType,
      filterValue: filterValue,
      fromDate: fromDate,
      toDate: toDate,
      status: status
    }, page: page.toString()
  })
  const [getAll] = useGetOverPressureTestsMutation();

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const handleRowClick = (item: OverPressureTest) => {
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
                        Over Pressure Test Results
                      </Typography>
                    </Grid>
                    <Grid item xs={4} sm={4} md={6} >
                      <Box display="flex" justifyContent="flex-end">
                        <Button variant="contained" startIcon={<Download />} color="success" onClick={() =>
                          handleGenerateOverPressureTesExcel(selectedRows)
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
                              handleGenerateOverPressureTesExcel(payload.data!.overPressureValveTests)
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
                          {data?.data?.overPressureValveTests
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
                                    {box.deviceMac}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.qrCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.maxPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.maxPressureTime}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.maxPressureFlowRate}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.normalPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.normalPressureTime}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.normalPressureFlowRate}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.overPressureValveStatus ? "Pass" : "Fail"}
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
