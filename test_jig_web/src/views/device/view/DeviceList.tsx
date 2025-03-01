import { Add } from "@mui/icons-material";
import {
  Box,
  Button,
  Card,
  CardActionArea,
  CardContent,
  Container,
  Divider,
  Grid,
  Pagination,
  Paper,
  Table,
  TableBody,
  TableContainer,
  TableHead,
  Typography,
} from "@mui/material";
import { List } from "reselect/es/types";
import {
  GridColDef,
  GridValueGetterParams,
} from "@mui/x-data-grid";
import React, { useEffect, useState } from "react";
import { Device, useGetDevicesMutation } from "../../../services/device_service";
import { AddDevice } from "../add/AddDevice";
import SessionTimeoutPopup from "../../common_components/session_logout";
import { StyledTableCell, StyledTableRow } from "../../common_components/common";

const columns: GridColDef[] = [
  { field: "deviceType", headerName: "device Type", width: 200 },
  { field: "deviceName", headerName: "device Name", width: 200 },
  {
    field: "deviceMac",
    headerName: "device Mac",
    width: 200,
  },
  {
    field: "createdDateTime",
    headerName: "Created Date",
    width: 300,
    valueGetter: (params: GridValueGetterParams) =>
      `${new Date(params.row.createdDate).toLocaleString() || ""}`,
  },
];

export default function DeviceList() {
  const [getDevices, { data, error, isLoading }] = useGetDevicesMutation();
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [page, setPage] = useState(1);
  const [devicesList, setDevicesList] = useState<List<Device>>([]);

  const startIndex = (page - 1) * rowsPerPage;
  const endIndex = startIndex + rowsPerPage;
  const paginatedDevices = devicesList.slice(startIndex, endIndex);

  useEffect(() => {
    getDevices({});
  }, []);

  useEffect(() => {
  }, [page]);

  useEffect(() => {
    if (data?.data) {
      setDevicesList(data.data.devices);
    }
  }, [data]);
  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  if (isLoading) {
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
      <>
        {isLoading ? (
          isLoading
        ) : (
          <Box
            component="main"
            sx={{
              flexGrow: 1,
            }}
          >
            <Container maxWidth={false}>
              <>
                <Box m="0px 0 0 0" sx={{}}>
                  <Card
                    sx={{
                      maxWidth: 1600,
                      maxHeight: "150vh",
                      backgroundColor: "#FFFFFF",
                      borderRadius: "30px",
                    }}
                  >
                    <CardActionArea>
                      <CardContent sx={{}}>
                        <Grid sx={{ display: 'flex' }}>

                          <Typography
                            gutterBottom
                            variant="h5"
                            component="div"
                            color="grey"
                          >
                            Devices
                          </Typography>
                          <Grid sx={{ display: 'flex', justifyContent: 'right', width: '100%', mr: '10px' }}>
                            <Box sx={{ ml: 3, }} >
                              <Button variant="contained" sx={{ borderRadius: '30px', backgroundColor: "#24325f", }} startIcon={<Add />} onClick={handleClickOpen}>
                                Add Device
                              </Button>
                            </Box>
                          </Grid>

                          <AddDevice openModel={open} close={() => handleClose()} />
                        </Grid>
                        <Divider
                          sx={{
                            borderColor: "grey",
                            my: 1,
                            borderStyle: "dashed",
                          }}
                        />
                        <Divider
                          sx={{
                            borderColor: "grey",
                            my: 1,
                            borderStyle: "dashed",
                          }}
                        />
                        <Paper sx={{ width: "100%", overflow: "hidden" }}>
                          <TableContainer sx={{ maxHeight: "100%" }}>
                            <Table stickyHeader aria-label="sticky table">
                              <TableHead sx={{ backgroundColor: "#9e9e9e" }}>
                                <StyledTableRow>
                                  {columns.map((column) => (
                                    <StyledTableCell
                                      key={column.headerName}
                                      align={column.align}
                                      style={{
                                        maxWidth: column.width,
                                        // backgroundColor:'#07032B'                                       
                                      }}
                                    >
                                      {column.headerName}
                                    </StyledTableCell>
                                  ))}
                                </StyledTableRow>
                              </TableHead>
                              <TableBody>
                                {paginatedDevices.map((item, index) => {
                                  return (
                                    <StyledTableRow
                                      hover
                                      role="checkbox"
                                      tabIndex={-1}
                                    >
                                      <StyledTableCell align={"left"}>
                                        {item.deviceType}
                                      </StyledTableCell>
                                      <StyledTableCell align={"left"}>
                                        {item.deviceName}
                                      </StyledTableCell>
                                      <StyledTableCell align={"left"}>
                                        {item.deviceMac}
                                      </StyledTableCell>
                                      <StyledTableCell align={"left"}>
                                        {item.createdAt}
                                      </StyledTableCell>
                                    </StyledTableRow>
                                  );
                                })}
                              </TableBody>
                            </Table>
                          </TableContainer>
                        </Paper>
                        <Box display="flex" justifyContent="flex-end">
                          <Pagination
                            count={Math.ceil(devicesList.length / rowsPerPage)}
                            sx={{ mt: 2 }}
                            variant="outlined"
                            shape="rounded"
                            page={page}
                            onChange={handleChange}
                          />
                        </Box>
                      </CardContent>
                    </CardActionArea>
                  </Card>
                </Box>
              </>
            </Container>
          </Box>
        )}
      </>
    );
  }
}
