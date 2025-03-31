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
          {/* <Grid item lg={3} sm={6} xl={2} xs={12}>
            <SummaryCard
              title="Air Pump Test"
              icon={<LooksOneIcon />}
              value={((data?.data.totalFailedAirPumpTest ?? 0) + (data?.data.totalSuccessAirPumpTest ?? 0)).toString() || "0"}
              color="warning.main"
              path="/air-pump"
              image={card1}
            />
          </Grid> */}
          {/* <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="PCB Test"
              icon={<LooksTwoIcon />}
              value={((data?.data.totalFailedPcbTest ?? 0) + (data?.data.totalSuccessPcbTest ?? 0)).toString() || "0"}
              color="success.light"
              path="/power-pcb-test"
              image={card2}
            />
          </Grid> */}
          {/* <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Power Supply Test"
              icon={<Looks3Icon />}
              value={((data?.data.totalFailedPowerSupplyTest ?? 0) + (data?.data.totalSuccessPowerSupplyTest ?? 0)).toString() || "0"}
              color="success.main"
              path="/power-supply-test"
              image={card3}
            />
          </Grid> */}
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
          {/* <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Power PCB Test"
              icon={<InventoryIcon />}
              value={((data?.data.totalFailedPowerPcbTest ?? 0) + (data?.data.totalSuccessPowerPcbTest ?? 0)).toString() || "0"}
              color="success.dark"
              path="/power-pcb-test"
              image={card1}
            />
          </Grid> */}
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Air Pump V2 Test"
              icon={<ListAltIcon />}
              value={((data?.data.totalFailedAirPumpV2Test ?? 0) + (data?.data.totalSuccessAirPumpV2Test ?? 0)).toString() || "0"}
              color="success.main"
              path="/air-pump-2"
              image={card2}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Power Supply V2 Test"
              icon={<InventoryIcon />}
              value={((data?.data.totalFailedPowerSupplyV2Test ?? 0) + (data?.data.totalSuccessPowerSupplyV2Test ?? 0)).toString() || "0"}
              color="success.dark"
              path="/power-supply-test-2"
              image={card3}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Power PCB V2 Test"
              icon={<ListAltIcon />}
              value={((data?.data.totalFailedPowerPcbV2Test ?? 0) + (data?.data.totalSuccessPowerPcbV2Test ?? 0)).toString() || "0"}
              color="success.main"
              path="/power-pcb-test-2"
              image={card4}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Main PCB Test"
              icon={<ListAltIcon />}
              value={((data?.data.totalFailedMainPcbTest ?? 0) + (data?.data.totalSuccessMainPcbTest ?? 0)).toString() || "0"}
              color="success.main"
              path="/main-pcb-test"
              image={card1}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Op Valve Test"
              icon={<ListAltIcon />}
              value={((data?.data.totalFailedOpValveTest ?? 0) + (data?.data.totalSuccessOpValveTest ?? 0)).toString() || "0"}
              color="success.main"
              path="/op-valve-test"
              image={card2}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Display Test"
              icon={<ListAltIcon />}
              value={((data?.data.totalFailedDisplayTest ?? 0) + (data?.data.totalSuccessDisplayTest ?? 0)).toString() || "0"}
              color="success.main"
              path="/display-test"
              image={card2}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Valve Sequence Test"
              icon={<InventoryIcon />}
              value={((data?.data.totalFailedValveSequenceTest ?? 0) + (data?.data.totalSuccessValveSequenceTest ?? 0)).toString() || "0"}
              color="success.dark"
              path="/valve-sequence-test"
              image={card3}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Valve Card Test"
              icon={<ListAltIcon />}
              value={((data?.data.totalFailedValveCardTest ?? 0) + (data?.data.totalSuccessValveCardTest ?? 0)).toString() || "0"}
              color="success.main"
              path="/valve-card-test"
              image={card4}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Manifold Leak Test"
              icon={<InventoryIcon />}
              value={((data?.data.totalFailedManiFoldLeakTest ?? 0) + (data?.data.totalSuccessManiFoldLeakTest ?? 0)).toString() || "0"}
              color="success.dark"
              path="/mani-fold-leak-test"
              image={card1}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Ui PCB Test"
              icon={<ListAltIcon />}
              value={((data?.data.totalFailedUiPcbTest ?? 0) + (data?.data.totalSuccessUiPcbTest ?? 0)).toString() || "0"}
              color="success.main"
              path="/ui-pcb-test"
              image={card2}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Cable Test"
              icon={<InventoryIcon />}
              value={((data?.data.totalFailedCableTest ?? 0) + (data?.data.totalSuccessCableTest ?? 0)).toString() || "0"}
              color="success.dark"
              path="/cable-test"
              image={card3}
            />
          </Grid>
          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="Fan Test"
              icon={<ListAltIcon />}
              value={((data?.data.totalFailedFanTest ?? 0) + (data?.data.totalSuccessFanTest ?? 0)).toString() || "0"}
              color="success.main"
              path="/fan-test"
              image={card4}
            />
          </Grid>

          <Grid item xl={2} lg={3} sm={6} xs={12}>
            <SummaryCard
              title="BT Device"
              icon={<InventoryIcon />}
              value={((data?.data.totalHHDevice ?? 0) + (data?.data.totalHHDevice ?? 0)).toString() || "0"}
              color="success.dark"
              path="/bt-device"
              image={card4}
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
