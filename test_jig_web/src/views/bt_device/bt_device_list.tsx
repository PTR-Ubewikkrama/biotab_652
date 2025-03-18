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
import { handleGenerateBTDeviceExcel } from "./bt_device-excel";
import GridOnIcon from '@mui/icons-material/GridOn';
import { BTDeviceDto, useGetBTDevicesMutation, useGetBTDevicesQQuery } from "../../services/bt_device_service";
import SessionTimeoutPopup from "../common_components/session_logout";
import { StyledTableCell, StyledTableRow } from "../common_components/common";
import TableSearchFormCustom from "../common_components/table_search_form_custom";
import { format, parseISO } from "date-fns";

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
    field: "powerPcbCode",
    headerName: "Power Pcb Code",
    width: 150,
  },
  {
    field: "powerPcbCodeStatus",
    headerName: "Power Pcb Code Status",
    width: 150,
  },
  {
    field: "pumpCode",
    headerName: "Pump Code",
    width: 150,
  },
  {
    field: "pumpCodeStatus",
    headerName: "Pump Code Status",
    width: 150,
  },
  {
    field: "fanCode",
    headerName: "Fan Code",
    width: 150,
  },
  {
    field: "fanCodeStatus",
    headerName: "Fan Code Status",
    width: 150,
  },
  {
    field: "uiPcbCode",
    headerName: "Ui Pcb Code",
    width: 150,
  },
  {
    field: "uiPcbCodeStatus",
    headerName: "Ui Pcb Code Status",
    width: 150,
  },
  {
    field: "encoderCode",
    headerName: "Encoder Code",
    width: 150,
  },
  {
    field: "encoderCodeStatus",
    headerName: "Encoder Code Status",
    width: 150,
  },
  {
    field: "mainPcbCode",
    headerName: "Main Pcb Code",
    width: 150,
  },
  {
    field: "mainPcbCodeStatus",
    headerName: "Main Pcb Code Status",
    width: 150,
  },
  {
    field: "manifoldCode",
    headerName: "Manifold Code",
    width: 150,
  },
  {
    field: "manifoldCodeStatus",
    headerName: "Manifold Code Status",
    width: 150,
  },
  {
    field: "valveCardInsideCableSetCode",
    headerName: "Valve Card Inside Cable Set Code",
    width: 150,
  },
  {
    field: "valveCardInsideCableSetCodeStatus",
    headerName: "Valve Card Inside Cable Set Code Status",
    width: 150,
  },
  {
    field: "valveCardInputOutputCableSetCode",
    headerName: "Valve Card Input Output Cable Set Code",
    width: 150,
  },
  {
    field: "valveCardInputOutputCableSetCodeStatus",
    headerName
      : "Valve Card Input Output Cable Set Code Status",
    width: 150,
  },
  {
    field: "overPressureValveCode",
    headerName: "Over Pressure Valve Code",
    width: 150,
  },
  {
    field: "overPressureValveCodeStatus",
    headerName: "Over Pressure Valve Code Status",
    width: 150,
  },
  {
    field: "powerCableCode",
    headerName: "Power Cable Code",
    width: 150,
  },
  {
    field: "uiCableCode",
    headerName: "Ui Cable Code",
    width: 150,
  },
  {
    field: "displayCode",
    headerName: "Display Code",
    width: 150,
  },
  {
    field: "frontBracketAssemblyCode",
    headerName: "Front Bracket Assembly Code",
    width: 150,
  },
  {
    field: "frontBracketAssemblyCodeStatus",
    headerName: "Front Bracket Assembly Code Status",
    width: 150,
  },
  {
    field: "powerAdaptorCode",
    headerName: "Power Adaptor Code",
    width: 150,
  },
  {
    field: "powerAdaptorCodeStatus",
    headerName: "Power Adaptor Code Status",
    width: 150,
  },
  {
    field: "enclosureTopCode",
    headerName: "Enclosure Top Code",
    width: 150,
  },
  {
    field: "enclosureBottomCode",
    headerName: "Enclosure Bottom Code",
    width: 150,
  },
  {
    field: "backVentCode",
    headerName: "Back Vent Code",
    width: 150,
  },
  {
    field: "fanMountCode",
    headerName: "Fan Mount Code",
    width: 150,
  },
  {
    field: "encoderSupporterCode",
    headerName: "Encoder Supporter Code",
    width: 150,
  },
  {
    field: "pcbHolderCode",
    headerName: "Pcb Holder Code",
    width: 150,
  },
  {
    field: "valveCardOne",
    headerName: "Valve card One",
    width: 100
  },
  {
    field: "valveCardTwo",
    headerName: "Valve card Two",
    width: 100
  },
  {
    field: "valveCardThree",
    headerName: "Valve card Three",
    width: 100
  },
  {
    field: "valveCardFour",
    headerName: "Valve card Four",
    width: 100
  },
  {
    field: "valveCardFive",
    headerName: "Valve card Five",
    width: 100
  },
  {
    field: "valveCardSix",
    headerName: "Valve card Six",
    width: 100
  },
  {
    field: "valveCardSeven",
    headerName: "Valve card Seven",
    width: 100
  },
  {
    field: "valveCardEight",
    headerName: "Valve card Eight",
    width: 100
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
  }
];
export default function BTDeviceList() {

  const [page, setPage] = useState(1);
  const [fromDate, setFromDate] = React.useState<Date | null>(null);
  const [pageCount, setPageCount] = React.useState(1);
  const [isFilter, setIsFilter] = useState<boolean>(true);
  const [filterType, setFilterType] = React.useState('');
  const [filterValue, setFilterValue] = React.useState('');
  const [selectedRows, setSelectedRows] = React.useState<BTDeviceDto[]>([]);

  var { data, error, isLoading } = useGetBTDevicesQQuery({
    data: {
      filterType: filterType,
      filterValue: filterValue,
      fromDate: fromDate,
    }, page: page.toString()
  })
  const [getAll] = useGetBTDevicesMutation();

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
  };

  const handleRowClick = (item: BTDeviceDto) => {
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
                        BT Devices
                      </Typography>
                    </Grid>
                    <Grid item xs={4} sm={4} md={6} >
                      <Box display="flex" justifyContent="flex-end">
                        <Button variant="contained" startIcon={<Download />} color="success" onClick={() =>
                          handleGenerateBTDeviceExcel(selectedRows)
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
                              handleGenerateBTDeviceExcel(payload.data!.devices)
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
                      "ALL": "ALL", "CODE": "DEVICE CODE",
                      "POWER_PCB": "POWER PCB CODE", "PUMP": "PUMP CODE",
                      "FAN": "FAN CODE", "UI_PCB": "UI PCB CODE",
                      "ENCODER": "ENCODER CODE", "MAIN_PCB": "MAIN PCB CODE",
                      "MANIFOLD": "MANIFOLD CODE", "VALVE_CARD_INSIDE_CABLE_SET": "VALVE CARD INSIDE CABLE CODE",
                      "VALVE_CARD_INPUT_OUTPUT_CABLE_SET": "VALVE CARD INPUT OUTPUT CABLE CODE",
                      "OVER_PRESSURE_VALVE": "OVER PRESSURE VALVE CODE", "POWER_CABLE": "POWER CABLE CODE",
                      "UI_CABLE": "UI CABLE CODE", "DISPLAY": "DISPLAY CODE",
                      "FRONT_BRACKET_ASSEMBLY": "FRONT BRACKET ASSEMBLY CODE", "POWER_ADAPTOR": "POWER ADAPTOR CODE",
                      "ENCLOSURE_TOP": "ENCLOSURE TOP CODE", "ENCLOSURE_BOTTOM": "ENCLOSURE BOTTOM CODE",
                      "BACK_VENT": "BACK VENT CODE", "FAN_MOUNT": "FAN MOUNT CODE",
                      "ENCODER_SUPPORTER": "ENCODER SUPPORTER CODE", "PCB_HOLDER": "PCB HOLDER CODE"
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
                                    {box.powerPcbCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.powerPcbCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.powerPcbCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.pumpCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.pumpCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.pumpCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.fanCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.fanCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.fanCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.uiPcbCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.uiPcbCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.uiPcbCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.encoderCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.encoderCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.encoderCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.mainPcbCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.mainPcbCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.mainPcbCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.manifoldCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.manifoldCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.manifoldCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valveCardInsideCableSetCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valveCardInsideCableSetCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.valveCardInsideCableSetCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valveCardInputOutputCableSetCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.valveCardInputOutputCableSetCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.valveCardInputOutputCableSetCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.overPressureValveCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.overPressureValveCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.overPressureValveCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.powerCableCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.uiCableCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.displayCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.frontBracketAssemblyCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.frontBracketAssemblyCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.frontBracketAssemblyCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.powerAdaptorCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.powerAdaptorCodeStatus == "VERIFIED"
                                      ? <Typography sx={{ color: "green", fontWeight: 'bold' }}>VERIFIED</Typography>
                                      : box.powerAdaptorCodeStatus == "N/A"
                                        ? <Typography sx={{ color: "grey", fontWeight: 'bold' }}>N/A</Typography>
                                        : <Typography sx={{ color: "red", fontWeight: 'bold' }}>NOT VERIFIED</Typography>
                                    }
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.enclosureTopCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.enclosureBottomCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.backVentCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.fanMountCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.encoderSupporterCode}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {box.pcbHolderCode}
                                  </StyledTableCell>
                                  {
                                    box.valveCards.map((valveCard, index) => {
                                      return (
                                        <StyledTableCell align={"left"}>
                                          {valveCard}
                                        </StyledTableCell>
                                      );
                                    })
                                  }
                                  <StyledTableCell align={"left"}>
                                    {box.createdBy}
                                  </StyledTableCell>
                                  <StyledTableCell align={"left"}>
                                    {format(parseISO(box.dateTime), "yyyy-MM-dd HH:mm:ss")},
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
