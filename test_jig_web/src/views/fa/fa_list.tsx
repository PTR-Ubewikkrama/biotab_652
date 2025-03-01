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
import { handleGenerateFAExcel } from "./fa-excel";
import GridOnIcon from '@mui/icons-material/GridOn';
import SessionTimeoutPopup from "../common_components/session_logout";
import { StyledTableCell, StyledTableRow } from "../common_components/common";
import TableSearchFormCustom from "../common_components/table_search_form_custom";
import { FinalAssemblyDto, useGetFAsMutation, useGetFAsQQuery } from "../../services/fa_service";
import { formatDateTime } from "../../helpers/date";

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
    field: "category",
    headerName: "Category",
    width: 150,
  },
  {
    field: "bladderCode",
    headerName: "Bladder Code",
    width: 150,
  },
  {
    field: "uplNumber",
    headerName: "UPL Number",
    width: 150,
  },
  {
    field: "udiNumber",
    headerName: "UDI Number",
    width: 150,
  },
  {
    field: "adapterCode",
    headerName: "Adapter Code",
    width: 150,
  },
  {
    field: "cartoonNumber",
    headerName: "Cartoon Number",
    width: 150,
  },
  {
    field: "createdAt",
    headerName: "Created At",
    width: 150,
  },
  {
    field: "updatedAt",
    headerName: "Updated At",
    width: 150,
  },
  {
    field: "updatedBy",
    headerName: "Updated By",
    width: 150,
  },
  {
    field: "createdBy",
    headerName: "Created By",
    width: 150,
  },
];

export default function FinalAssemblyList() {

  const [page, setPage] = useState(1);
  const [fromDate, setFromDate] = React.useState<Date | null>(null);
  const [pageCount, setPageCount] = React.useState(1);
  const [isFilter, setIsFilter] = useState<boolean>(true);
  const [filterType, setFilterType] = React.useState('');
  const [filterValue, setFilterValue] = React.useState('');
  const [selectedRows, setSelectedRows] = React.useState<FinalAssemblyDto[]>([]);

  var { data, error, isLoading } = useGetFAsQQuery({
    data: {
      filterType: filterType,
      filterValue: filterValue,
      fromDate: fromDate,
    }, page: page.toString()
  })
  const [getAll] = useGetFAsMutation();

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const handleRowClick = (item: FinalAssemblyDto) => {
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
                        Final Assembly
                      </Typography>
                    </Grid>
                    <Grid item xs={4} sm={4} md={6} >
                      <Box display="flex" justifyContent="flex-end">
                        <Button variant="contained" startIcon={<Download />} color="success" onClick={() =>
                          handleGenerateFAExcel(selectedRows)
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
                              handleGenerateFAExcel(payload.data!.fas)
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
                      "ALL": "ALL",
                      "DEVICE_CODE": "DEVICE CODE",
                      "CATEGORY": "CATEGORY",
                      "BLADDER_CODE": "BLADDER CODE",
                      "UPL_NUMBER": "UPL NUMBER",
                      "UDI_NUMBER": "UDI NUMBER",
                      "ADAPTER_CODE": "ADAPTER CODE",
                      "CARTOON_NUMBER": "CARTOON NUMBER",
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
                          {data?.data?.fas
                            .map((box) => {
                              return (
                                <StyledTableRow
                                  hover
                                  role="checkbox"
                                  tabIndex={-1}
                                  key={box.id}
                                  onClick={() => handleRowClick(box)}
                                  selected={selectedRows.includes(box)}
                                >
                                  <StyledTableCell align={"left"}>
                                    <input
                                      type="checkbox"
                                      checked={selectedRows.includes(box)}
                                    />
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.deviceCode}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.category}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.bladderCode}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.uplNumber}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.udiNumber}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.adapterCode}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.cartoonNumber}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{formatDateTime(box.createdAt)}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{formatDateTime(box.updatedAt)}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.updatedBy}</StyledTableCell>
                                  <StyledTableCell align={"left"}>{box.createdBy}</StyledTableCell>
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
