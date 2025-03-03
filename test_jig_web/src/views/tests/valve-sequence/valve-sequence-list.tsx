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
import { handleGenerateValveExcel } from "./valve-sequence-excel";
import { ValveTest, useGetValveTestQQuery, useGetValveTestsMutation } from "../../../services/valve_service";
import SessionTimeoutPopup from "../../common_components/session_logout";
import { StyledTableCell, StyledTableRow } from "../../common_components/common";
import GridOnIcon from '@mui/icons-material/GridOn';
import TableSearchFormCommon from "../../common_components/table_search_form";
import { format, parseISO } from "date-fns";
import { useGetValveSequenceTestQQuery, useGetValveSequenceTestsMutation, ValveSequenceTest } from "../../../services/valve_sequence_service";

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
    field: "physicalInspectionState",
    headerName: "Physical Inspection",
    width: 100,
  },
  {
    field: "manifoldSealPressure",
    headerName: "Manifold Seal Pressure",
    width: 200,
  },
  {
    field: "manifoldPressureAfter1Sec",
    headerName: "Manifold Pressure After 1 Sec",
    width: 200,
  },
  {
    field: "manifoldPressureState",
    headerName: "Manifold Pressure State",
    width: 200,
  },
  {
    field: "valve1InflationPressure",
    headerName: "Valve 1 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve1State",
    headerName: "Valve 1 State",
    width: 200,
  },
  {
    field: "valve3InflationPressure",
    headerName: "Valve 3 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve3State",
    headerName: "Valve 3 State",
    width: 200,
  },
  {
    field: "valve5InflationPressure",
    headerName: "Valve 5 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve5State",
    headerName: "Valve 5 State",
    width: 200,
  },
  {
    field: "valve7InflationPressure",
    headerName: "Valve 7 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve7State",
    headerName: "Valve 7 State",
    width: 200,
  },
  {
    field: "valve9InflationPressure",
    headerName: "Valve 9 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve9State",
    headerName: "Valve 9 State",
    width: 200,
  },
  {
    field: "valve11InflationPressure",
    headerName: "Valve 11 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve11State",
    headerName: "Valve 11 State",
    width: 200,
  },
  {
    field: "valve13InflationPressure",
    headerName: "Valve 13 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve13State",
    headerName: "Valve 13 State",
    width: 200,
  },
  {
    field: "valve15InflationPressure",
    headerName: "Valve 15 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve15State",
    headerName: "Valve 15 State",
    width: 200,
  },
  {
    field: "valve17InflationPressure",
    headerName: "Valve 17 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve17State",
    headerName: "Valve 17 State",
    width: 200,
  },
  {
    field: "valve19InflationPressure",
    headerName: "Valve 19 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve19State",
    headerName: "Valve 19 State",
    width: 200,
  },
  {
    field: "valve21InflationPressure",
    headerName: "Valve 21 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve21State",
    headerName: "Valve 21 State",
    width: 200,
  },
  {
    field: "valve23InflationPressure",
    headerName: "Valve 23 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve23State",
    headerName: "Valve 23 State",
    width: 200,
  },
  {
    field: "valve25InflationPressure",
    headerName: "Valve 25 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve25State",
    headerName: "Valve 25 State",
    width: 200,
  },
  {
    field: "valve27InflationPressure",
    headerName: "Valve 27 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve27State",
    headerName: "Valve 27 State",
    width: 200,
  },
  {
    field: "valve29InflationPressure",
    headerName: "Valve 29 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve29State",
    headerName: "Valve 29 State",
    width: 200,
  },
  {
    field: "valve31InflationPressure",
    headerName: "Valve 31 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve31State",
    headerName: "Valve 31 State",
    width: 200,
  },
  {
    field: "valve33InflationPressure",
    headerName: "Valve 33 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve33State",
    headerName: "Valve 33 State",
    width: 200,
  },
  {
    field: "valve35InflationPressure",
    headerName: "Valve 35 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve35State",
    headerName: "Valve 35 State",
    width: 200,
  },
  {
    field: "valve37InflationPressure",
    headerName: "Valve 37 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve37State",
    headerName: "Valve 37 State",
    width: 200,
  },
  {
    field: "valve39InflationPressure",
    headerName: "Valve 39 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve39State",
    headerName: "Valve 39 State",
    width: 200,
  },
  {
    field: "valve41InflationPressure",
    headerName: "Valve 41 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve41State",
    headerName: "Valve 41 State",
    width: 200,
  },
  {
    field: "valve43InflationPressure",
    headerName: "Valve 43 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve43State",
    headerName: "Valve 43 State",
    width: 200,
  },
  {
    field: "valve45InflationPressure",
    headerName: "Valve 45 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve45State",
    headerName: "Valve 45 State",
    width: 200,
  },
  {
    field: "valve47InflationPressure",
    headerName: "Valve 47 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve47State",
    headerName: "Valve 47 State",
    width: 200,
  },
  {
    field: "valve49InflationPressure",
    headerName: "Valve 49 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve49State",
    headerName: "Valve 49 State",
    width: 200,
  },
  {
    field: "valve51InflationPressure",
    headerName: "Valve 51 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve51State",
    headerName: "Valve 51 State",
    width: 200,
  },
  {
    field: "valve53InflationPressure",
    headerName: "Valve 53 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve53State",
    headerName: "Valve 53 State",
    width: 200,
  },
  {
    field: "valve55InflationPressure",
    headerName: "Valve 55 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve55State",
    headerName: "Valve 55 State",
    width: 200,
  },
  {
    field: "valve57InflationPressure",
    headerName: "Valve 57 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve57State",
    headerName: "Valve 57 State",
    width: 200,
  },
  {
    field: "valve59InflationPressure",
    headerName: "Valve 59 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve59State",
    headerName: "Valve 59 State",
    width: 200,
  },
  {
    field: "valve61InflationPressure",
    headerName: "Valve 61 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve61State",
    headerName: "Valve 61 State",
    width: 200,
  },
  {
    field: "valve63InflationPressure",
    headerName: "Valve 63 Inflation Pressure",
    width: 200,
  },
  {
    field: "valve63State",
    headerName: "Valve 63 State",
    width: 200,
  },
  {
    field: "valve2DeflationPressure",
    headerName: "Valve 2 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve2State",
    headerName: "Valve 2 State",
    width: 200,
  },
  {
    field: "valve4DeflationPressure",
    headerName: "Valve 4 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve4State",
    headerName: "Valve 4 State",
    width: 200,
  },
  {
    field: "valve6DeflationPressure",
    headerName: "Valve 6 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve6State",
    headerName: "Valve 6 State",
    width: 200,
  },
  {
    field: "valve8DeflationPressure",
    headerName: "Valve 8 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve8State",
    headerName: "Valve 8 State",
    width: 200,
  },
  {
    field: "valve10DeflationPressure",
    headerName: "Valve 10 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve10State",
    headerName: "Valve 10 State",
    width: 200,
  },
  {
    field: "valve12DeflationPressure",
    headerName: "Valve 12 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve12State",
    headerName: "Valve 12 State",
    width: 200,
  },
  {
    field: "valve14DeflationPressure",
    headerName: "Valve 14 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve14State",
    headerName: "Valve 14 State",
    width: 200,
  },
  {
    field: "valve16DeflationPressure",
    headerName: "Valve 16 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve16State",
    headerName: "Valve 16 State",
    width: 200,
  },
  {
    field: "valve18DeflationPressure",
    headerName: "Valve 18 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve18State",
    headerName: "Valve 18 State",
    width: 200,
  },
  {
    field: "valve20DeflationPressure",
    headerName: "Valve 20 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve20State",
    headerName: "Valve 20 State",
    width: 200,
  },
  {
    field: "valve22DeflationPressure",
    headerName: "Valve 22 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve22State",
    headerName: "Valve 22 State",
    width: 200,
  },
  {
    field: "valve24DeflationPressure",
    headerName: "Valve 24 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve24State",
    headerName: "Valve 24 State",
    width: 200,
  },
  {
    field: "valve26DeflationPressure",
    headerName: "Valve 26 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve26State",
    headerName: "Valve 26 State",
    width: 200,
  },
  {
    field: "valve28DeflationPressure",
    headerName: "Valve 28 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve28State",
    headerName: "Valve 28 State",
    width: 200,
  },
  {
    field: "valve30DeflationPressure",
    headerName: "Valve 30 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve30State",
    headerName: "Valve 30 State",
    width: 200,
  },
  {
    field: "valve32DeflationPressure",
    headerName: "Valve 32 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve32State",
    headerName: "Valve 32 State",
    width: 200,
  },
  {
    field: "valve34DeflationPressure",
    headerName: "Valve 34 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve34State",
    headerName: "Valve 34 State",
    width: 200,
  },
  {
    field: "valve36DeflationPressure",
    headerName: "Valve 36 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve36State",
    headerName: "Valve 36 State",
    width: 200,
  },
  {
    field: "valve38DeflationPressure",
    headerName: "Valve 38 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve38State",
    headerName: "Valve 38 State",
    width: 200,
  },
  {
    field: "valve40DeflationPressure",
    headerName: "Valve 40 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve40State",
    headerName: "Valve 40 State",
    width: 200,
  },
  {
    field: "valve42DeflationPressure",
    headerName: "Valve 42 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve42State",
    headerName: "Valve 42 State",
    width: 200,
  },
  {
    field: "valve44DeflationPressure",
    headerName: "Valve 44 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve44State",
    headerName: "Valve 44 State",
    width: 200,
  },
  {
    field: "valve46DeflationPressure",
    headerName: "Valve 46 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve46State",
    headerName: "Valve 46 State",
    width: 200,
  },
  {
    field: "valve48DeflationPressure",
    headerName: "Valve 48 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve48State",
    headerName: "Valve 48 State",
    width: 200,
  },
  {
    field: "valve50DeflationPressure",
    headerName: "Valve 50 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve50State",
    headerName: "Valve 50 State",
    width: 200,
  },
  {
    field: "valve52DeflationPressure",
    headerName: "Valve 52 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve52State",
    headerName: "Valve 52 State",
    width: 200,
  },
  {
    field: "valve54DeflationPressure",
    headerName: "Valve 54 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve54State",
    headerName: "Valve 54 State",
    width: 200,
  },
  {
    field: "valve56DeflationPressure",
    headerName: "Valve 56 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve56State",
    headerName: "Valve 56 State",
    width: 200,
  },
  {
    field: "valve58DeflationPressure",
    headerName: "Valve 58 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve58State",
    headerName: "Valve 58 State",
    width: 200,
  },
  {
    field: "valve60DeflationPressure",
    headerName: "Valve 60 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve60State",
    headerName: "Valve 60 State",
    width: 200,
  },
  {
    field: "valve62DeflationPressure",
    headerName: "Valve 62 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve62State",
    headerName: "Valve 62 State",
    width: 200,
  },
  {
    field: "valve64DeflationPressure",
    headerName: "Valve 64 Deflation Pressure",
    width: 200,
  },
  {
    field: "valve64State",
    headerName: "Valve 64 State",
    width: 200,
  },
  {
    field: "overallValveSequenceState",
    headerName: "Overall Valve Sequence State",
    width: 200,
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
  },
];

export default function ValveSequenceList() {
  const [page, setPage] = useState(1);
  const [fromDate, setFromDate] = React.useState<Date | null>(null);
  const [toDate, setToDate] = React.useState<Date | null>(null);
  const [pageCount, setPageCount] = React.useState(1);
  const [isFilter, setIsFilter] = useState<boolean>(true);
  const [filterType, setFilterType] = React.useState('');
  const [filterValue, setFilterValue] = React.useState('');
  const [status, setStatus] = React.useState('');
  const [selectedRows, setSelectedRows] = React.useState<ValveSequenceTest[]>([]);

  var { data, error, isLoading } = useGetValveSequenceTestQQuery({
    data: {
      filterType: filterType,
      filterValue: filterValue,
      fromDate: fromDate,
      toDate: toDate,
      status: status
    }, page: page.toString()
  })
  const [getAll] = useGetValveSequenceTestsMutation();

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const handleRowClick = (item: ValveSequenceTest) => {
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
                        Valve Sequence Test Results
                      </Typography>
                    </Grid>
                    <Grid item xs={4} sm={4} md={6} >
                      <Box display="flex" justifyContent="flex-end">
                        <Button variant="contained" startIcon={<Download />} color="success" onClick={() =>
                          handleGenerateValveExcel(selectedRows)
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
                              handleGenerateValveExcel(payload.data!.tests)
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
                                    {box.physicalInspectionState
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.manifoldSealPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.manifoldPressureAfter1Sec}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.manifoldPressureState
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve1InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve1State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve3InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve3State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve5InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve5State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve7InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve7State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve9InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve9State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve11InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve11State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve13InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve13State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve15InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve15State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve17InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve17State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve19InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve19State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve21InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve21State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve23InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve23State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve25InflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve25State
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.valve27InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve27State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve29InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve29State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve31InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve31State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve33InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve33State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve35InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve35State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve37InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve37State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve39InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve39State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve41InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve41State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve43InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve43State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve45InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve45State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve47InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve47State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve49InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve49State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve51InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve51State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve53InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve53State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve55InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve55State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve57InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve57State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve59InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve59State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve61InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve61State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>{box.valve63InflationPressure}</StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve63State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve2DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve2State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve4DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve4State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve6DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve6State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve8DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve8State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve10DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve10State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve12DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve12State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve14DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve14State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve16DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve16State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve18DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve18State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve20DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve20State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve22DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve22State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve24DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve24State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve26DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve26State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve28DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve28State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve30DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve30State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve32DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve32State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve34DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve34State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve36DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve36State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve38DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve38State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve40DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve40State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve42DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve42State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve44DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve44State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve46DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve46State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve48DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve48State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve50DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve50State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve52DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve52State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve54DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve54State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve56DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve56State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve58DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve58State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve60DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve60State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve62DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve62State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.valve64DeflationPressure}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valve64State ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>}
                                  </StyledTableCell>

                                  <StyledTableCell align={"left"}>
                                    {box.overallValveSequenceState
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>Pass</Typography>
                                      : <Typography sx={{ color: "red", fontWeight: 'bold' }}>Fail</Typography>
                                    }
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
      </Box>
    )
  }
}
