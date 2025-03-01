import { Container, Grid, Box } from "@mui/material";
import { SummaryCard } from "./summary_card";
import { useGetDashboardSummaryQuery } from "../../services/dashboard_service";
import LooksOneIcon from "@mui/icons-material/LooksOne";
import LooksTwoIcon from "@mui/icons-material/LooksTwo";
import Looks3Icon from "@mui/icons-material/Looks3";
import Looks4Icon from "@mui/icons-material/Looks4";
import SessionTimeoutPopup from "../common_components/session_logout";
import card1 from '../../images/01.jpg';
import card2 from '../../images/02.jpg';
import card3 from '../../images/03.jpg';
import card4 from '../../images/04.jpg';
import loading from '../../images/loading.gif';
import InventoryIcon from '@mui/icons-material/Inventory';
import ListAltIcon from '@mui/icons-material/ListAlt';

export default function Dashboard() {
  const { data, error, isLoading } = useGetDashboardSummaryQuery("");
  // const isLoading = false;
  // const error = null;
  var userRoles: string | string[] = [];
  var admin = "";
  var roles = localStorage.getItem("roles");

  //get user roles from local storage
  if (roles === null) {
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

  if (isLoading) {
    return (
      <div style={{
        display: 'flex',
        flexDirection: 'row',
        justifyContent: 'center',
        height: '100%',
      }}>
        <Box
          component="img"
          sx={{
            height: 300,
          }}
          src={loading}
        />
      </div>
    );
  }

  if (error != null && "status" in error) {
    if (error.status == 401 && error.data == null) {
      return <SessionTimeoutPopup />;
    } else {
      return <></>;
    }
  } else {
    return (
      // isLoading ? <></> :
      <Container maxWidth={false}>
        <Grid container spacing={3}>
          <Grid item lg={3} sm={6} xl={2} xs={12}>
            <SummaryCard
              title="Air Pump Test"
              icon={<LooksOneIcon />}
              value={((data?.data.totalFailedAirPumpTest ?? 0) + (data?.data.totalSuccessAirPumpTest ?? 0)).toString() || "0"}
              color="warning.main"
              path="/air-dump-test"
              image={card1}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Pcb Test"
              icon={<LooksTwoIcon />}
              value={((data?.data.totalFailedPcbTest ?? 0) + (data?.data.totalSuccessPcbTest ?? 0)).toString() || "0"}
              color="success.light"
              path="/pcb-test"
              image={card2}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Power Supply Test"
              icon={<Looks3Icon />}
              value={((data?.data.totalFailedPowerSupplyTest ?? 0) + (data?.data.totalSuccessPowerSupplyTest ?? 0)).toString() || "0"}
              color="success.main"
              path="/power-supply-test"
              image={card3}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Valve Test"
              icon={<Looks4Icon />}
              value={((data?.data.totalFailedValueTest ?? 0) + (data?.data.totalSuccessValueTest ?? 0)).toString() || "0"}
              color="success.dark"
              path="/valve-test"
              image={card4}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Battery Test"
              icon={<Looks4Icon />}
              value={((data?.data.totalFailedBatteryTest ?? 0) + (data?.data.totalSuccessBatteryTest ?? 0)).toString() || "0"}
              color="success.dark"
              path="/battery-test"
              image={card3}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Latch Button Test"
              icon={<Looks4Icon />}
              value={((data?.data.totalFailedLatchButtonTest ?? 0) + (data?.data.totalSuccessLatchButtonTest ?? 0)).toString() || "0"}
              color="success.dark"
              path="/latch-button-test"
              image={card4}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Over Pressure Test"
              icon={<Looks4Icon />}
              value={((data?.data.totalFailedOverPressureTest ?? 0) + (data?.data.totalSuccessOverPressureTest ?? 0)).toString() || "0"}
              color="green"
              path="/over-pressure-test"
              image={card2}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="HH Device"
              icon={<InventoryIcon />}
              value={((data?.data.totalHHDevice ?? 0) + (data?.data.totalHHDevice ?? 0)).toString() || "0"}
              color="success.dark"
              path="/hh-device"
              image={card4}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Final Assembly"
              icon={<ListAltIcon />}
              value={((data?.data.totalFinalAssembly ?? 0) + (data?.data.totalFinalAssembly ?? 0)).toString() || "0"}
              color="success.main"
              path="/fas"
              image={card2}
            />
          </Grid>
        </Grid>

        {/* <Grid container spacing={3} sx={{ mt: 5 }}>
          <Grid item lg={6} sm={6} xl={6} xs={12}>
            <SummeryCardGraph/>
          </Grid>
        </Grid> */}
      </Container>
    );
  }
}
