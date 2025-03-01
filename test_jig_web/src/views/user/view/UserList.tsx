import {
  Box,
  Button,
  Card,
  CardActionArea,
  CardContent,
  Container,
  Divider,
  Pagination,
  Paper,
  Table,
  TableBody,
  TableContainer,
  TableHead,
} from "@mui/material";
import { GridColDef } from "@mui/x-data-grid";
import { useGetUsersQuery, User } from "../../../services/user_service";
import { List } from "reselect/es/types";
import React, { useEffect, useState } from "react";
import SessionTimeoutPopup from "../../common_components/session_logout";
import { AddUserModel } from "./add-user";
import { StyledTableCell, StyledTableRow } from "../../common_components/common";
const columns: GridColDef[] = [
  { field: "UserName", headerName: "User Name", width: 200 },
  {
    field: "Status",
    headerName: "Status",
    width: 200,
  },
  {
    field: "passwordType",
    headerName: "Password Status",
    width: 200,
    align: "center"
  },
  {
    field: "Update",
    headerName: "Update",
    type: "actions",
    width: 200,
    align: "center"
  },
];

export default function UserList() {
  const { data, error, isLoading } = useGetUsersQuery("");
  const [rowsPerPage, setRowsPerPage] = useState(5);
  const [page, setPage] = useState(1);
  var userRoles: string | string[] = [];
  var admin = "";
  const [userList, setUserList] = useState<List<User>>([]);
  const [isUsersUpdated, setIsUsersUpdated] = useState(false);
  const [open, setOpen] = useState(false);
  var roles = localStorage.getItem("roles");

  const startIndex = (page - 1) * rowsPerPage;
  const endIndex = startIndex + rowsPerPage;
  const paginatedUsers = userList.slice(startIndex, endIndex);

  useEffect(() => {
    // console.log(paginatedUsers);
  }, [page]);

  useEffect(() => {
    if (data?.users) {
      setUserList(data.users);
    }
  }, [data]);

  if (roles === null) {
    admin = "ADMIN";
    console.log("roles empty");
  } else {
    userRoles = roles.split(",").map((item) => item.trim());
    if (
      userRoles.includes("CREATE_TOP_ADMIN") &&
      userRoles.includes("CREATE_ADMIN")
    ) {
      admin = "TOP_ADMIN";
    } else if (userRoles.includes("CREATE_USER")) {
      admin = "ADMIN";
    }
  }

  const handleChange = (event: React.ChangeEvent<unknown>, value: number) => {
    setPage(value);
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
                      maxHeight: "80vh",
                      backgroundColor: "#FFFFFF",
                      // boxShadow: "1px 1px 10px 10px #e8e8e8",
                    }}
                  >
                    <CardActionArea>
                      <CardContent sx={{}}>
                        {/* <Typography
                          gutterBottom
                          variant="h5"
                          component="div"
                          color="grey"
                        >
                          Users
                        </Typography>
                        <Box display="flex" justifyContent="flex-end">
                          <Button
                            variant="contained"
                            startIcon={<AddIcon />}
                            sx={{
                              backgroundColor: "#00e676",
                              "&:hover": { backgroundColor: "#00a152" },
                            }}
                            onClick={() => setOpen(true)}
                          >
                            ADD
                          </Button>
                        </Box> */}
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
                                      style={{ maxWidth: column.width, backgroundColor: '#07032B' }}
                                    >
                                      {column.headerName}
                                    </StyledTableCell>
                                  ))}
                                </StyledTableRow>
                              </TableHead>
                              <TableBody>
                                {paginatedUsers
                                  .filter((user: User) => {
                                    if (admin === "ADMIN") {
                                      return user.group === "USER";
                                    } else {
                                      return user;
                                    }
                                  })
                                  .map((item: any, index: any) => {
                                    return (
                                      <StyledTableRow
                                        key={index}
                                        id={item.username}
                                        hover
                                        role="checkbox"
                                        tabIndex={-1}
                                      >
                                        <StyledTableCell align={"left"}>
                                          {item.username}
                                        </StyledTableCell>
                                        <StyledTableCell align={"left"}>
                                          {item.createdDateTime}
                                        </StyledTableCell>
                                        <StyledTableCell align={"center"}>
                                          {item.passwordType == "PASSWORD" ? (
                                            <Button
                                              variant="contained"
                                              color="success"
                                              sx={{ borderRadius: '20px' }}
                                            >
                                              ACTIVE
                                            </Button>
                                          ) : item.passwordType == "DEFAULT" ? (
                                            <Button
                                              variant="contained"
                                              color="warning"
                                              sx={{ borderRadius: '20px' }}
                                            >
                                              DEFAULT
                                            </Button>
                                          ) : (
                                            <Button
                                              variant="contained"
                                              color="error"
                                              sx={{ borderRadius: '20px' }}
                                            >
                                              RESET
                                            </Button>
                                          )}
                                        </StyledTableCell>
                                        <StyledTableCell align={"center"}>
                                          {item.status == "ACTIVE" ? (
                                            <Button
                                              variant="contained"
                                              color="primary"
                                              sx={{ borderRadius: '20px' }}
                                            >
                                              ACTIVE
                                            </Button>
                                          ) : (
                                            <Button
                                              variant="contained"
                                              color="error"
                                              sx={{ borderRadius: '20px' }}
                                            >
                                              TEMPORARY_BLOCKED
                                            </Button>
                                          )}
                                        </StyledTableCell>
                                      </StyledTableRow>
                                    );
                                  })
                                  .reverse()}
                              </TableBody>
                            </Table>
                          </TableContainer>
                        </Paper>
                        <Box display="flex" justifyContent="flex-end">
                          <Pagination
                            count={Math.ceil(userList.length / rowsPerPage)}
                            sx={{
                              mt: 2,
                            }}
                            // variant="outlined"
                            color="primary"
                            // shape="rounded"
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
            <AddUserModel open={open} changeOpenStatus={setOpen} />
          </Box>
        )}
      </>
    );
  }
}
