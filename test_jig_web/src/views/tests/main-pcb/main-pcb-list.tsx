import {
  Box,
  Button,
  Card,
  CardContent,
  Container,
  Dialog,
  DialogContent,
  DialogTitle,
  Divider,
  Grid,
  Pagination,
  TableCell,
  TableRow,
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
import { handleGenerateMainPCBExcel } from "./main-pcb-excel";
import SessionTimeoutPopup from "../../common_components/session_logout";
import { StyledTableCell, StyledTableRow } from "../../common_components/common";
import GridOnIcon from '@mui/icons-material/GridOn';
import VisibilityIcon from '@mui/icons-material/Visibility';
import TableSearchFormCommon from "../../common_components/table_search_form";
import { format, parseISO } from "date-fns";
import { MainPCBTest, useGetMainPCBTestQQuery, useGetMainPCBTestsMutation } from "../../../services/Main_PCB_test_service";

const columns: GridColDef[] = [
  {
    field: "Select",
    headerName: "Select",
    width: 80,
  },
  {
    field: "serialNumber",
    headerName: "Serial Number",
    width: 150,
  },
  {
    field: "Software Version",
    headerName: "Software Version",
    width: 150,
  },
  {
    field: "Batch Number",
    headerName: "Batch Number",
    width: 150,
  },
  {
    field: "Test Result",
    headerName: "Test Result",
    width: 150,
  },
  {
    field: "Status",
    headerName: "Status",
    width: 100,
  },
  {
    field: "dateTime",
    headerName: "Date Time",
    width: 200,
  }
];

export default function MainPCBList() {
  const [page, setPage] = useState(1);
  const [fromDate, setFromDate] = React.useState<Date | null>(null);
  const [toDate, setToDate] = React.useState<Date | null>(null);
  const [pageCount, setPageCount] = React.useState(1);
  const [isFilter, setIsFilter] = useState<boolean>(true);
  const [filterType, setFilterType] = React.useState('');
  const [filterValue, setFilterValue] = React.useState('');
  const [status, setStatus] = React.useState('');
  const [selectedRows, setSelectedRows] = React.useState<MainPCBTest[]>([]);
  const [openDetailView, setOpenDetailView] = React.useState(false);
  const [selectedTest, setSelectedTest] = React.useState<MainPCBTest | null>(null);

  var { data, error, isLoading } = useGetMainPCBTestQQuery({
    data: {
      filterType: filterType,
      filterValue: filterValue,
      fromDate: fromDate,
      toDate: toDate,
      status: status
    }, page: page.toString()
  })
  const [getAll] = useGetMainPCBTestsMutation();

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const handleRowClick = (item: MainPCBTest) => {
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
                        Main PCB Test Results
                      </Typography>
                    </Grid>
                    <Grid item xs={4} sm={4} md={6} >
                      <Box display="flex" justifyContent="flex-end">
                        <Button variant="contained" startIcon={<Download />} color="success" onClick={() =>
                          handleGenerateMainPCBExcel(selectedRows)
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
                              handleGenerateMainPCBExcel(payload.data!.tests)
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
                          {data?.data?.tests
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
                                    {box.serialNumber}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.softwareVersion}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.batchNumber}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    <Button
                                      variant="contained"
                                      color="primary"
                                      startIcon={<VisibilityIcon />}
                                      onClick={() => {
                                        setSelectedTest(box);
                                        setOpenDetailView(true);
                                      }}
                                    >
                                      View
                                    </Button>
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.status
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {format(parseISO(box.dateTime), "yyyy-MM-dd HH:mm:ss")}
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

        <Dialog open={openDetailView} onClose={() => {
          setOpenDetailView(false);
          setSelectedTest(null);
        }} maxWidth="md" fullWidth>
          <DialogTitle>Test Result Details</DialogTitle>
          <DialogContent>
            {selectedTest && (
              <Table>
                <TableHead>
                  <TableRow>
                    <TableCell>Test Name</TableCell>
                    <TableCell>Test Type</TableCell>
                    <TableCell>Validation Type</TableCell>
                    <TableCell>Actual Value</TableCell>
                    <TableCell>Min Value</TableCell>
                    <TableCell>Max Value</TableCell>
                    <TableCell>Unit</TableCell>
                    <TableCell>Status</TableCell>
                    <TableCell>Date/Time</TableCell>
                  </TableRow>
                </TableHead>
                <TableBody>
                  {selectedTest.testResultData.map((unit, index) => (
                    <TableRow key={index}>
                      <TableCell>{unit.testName}</TableCell>
                      <TableCell>{unit.testType}</TableCell>
                      <TableCell>{unit.validationType}</TableCell>
                      <TableCell>{unit.actualValue}</TableCell>
                      <TableCell>{unit.minValue}</TableCell>
                      <TableCell>{unit.maxValue}</TableCell>
                      <TableCell>{unit.unit}</TableCell>
                      <TableCell>{unit.status
                        ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}</TableCell>
                      <TableCell>{format(parseISO(unit.dateTime), "yyyy-MM-dd HH:mm:ss")}</TableCell>
                    </TableRow>
                  ))}
                </TableBody>
              </Table>
            )}
          </DialogContent>
        </Dialog>
      </Box>
    )
  }
}
