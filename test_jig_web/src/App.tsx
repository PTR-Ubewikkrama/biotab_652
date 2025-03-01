import { Grid } from "@mui/material";
import { Box, Container } from "@mui/system";
import { ReactNode, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { useAppSelector } from "./store/hooks";
import { useDispatch } from "react-redux";
import { DashboardLayout } from "./views/common_components/dashboard-layout";
import Login from "./views/login/login";

type AppProps = {
  children: ReactNode;
}

const footerStyles = {
  position: 'fixed',
  bottom: 0,
  left: 0,
  width: '100%',
  height: '50px',
  backgroundColor: '#f5f5f5',
  display: 'flex',
  alignItems: 'center',
  justifyContent: 'center',
};

const copyrightStyles = {
  fontSize: '12px',
  color: '#999',
};


function App({ children }: AppProps) {

  const isLogin = localStorage.getItem("jwt") != null;
  const stateLogin = useAppSelector((state) => state.loginStatus.jwt)
  const navigate = useNavigate();
  const currentYear = new Date().getFullYear();
  const dispatch = useDispatch()

  return (
    isLogin ? <>
      <DashboardLayout>
        <Box
          component="main"
          sx={{
            flexGrow: 1,
            py: 8
          }}
        >
          <Container maxWidth={false}>
            <Grid
              container
              spacing={3}
            >
              <Grid
                item
                xs={12}
              >
                {children}
              </Grid>
            </Grid>
          </Container>
        </Box>
        <Box sx={footerStyles}>
          <span style={copyrightStyles}>
            &copy; {currentYear} wavetec. All rights reserved.
          </span>
        </Box>
      </DashboardLayout>
    </>
      :
      <>
        {localStorage.getItem("resetJwt") != null ? <Login /> : <Login />}
      </>

  )
}

export default App
