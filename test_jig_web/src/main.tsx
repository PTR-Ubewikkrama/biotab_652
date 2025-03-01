import { CssBaseline, ThemeProvider } from "@mui/material";
import React from "react";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "./App";

import { store } from "./store/store";
import Dashboard from "./views/dashboard/dashboard";
import UserList from "./views/user/view/UserList";
import { theme } from "./theme";
import DevicesList from "./views/device/view/DeviceList";
import Login from "./views/login/login";
import PowerSupplyList from "./views/tests/power-supply/power-supply-list";
import ValveList from "./views/tests/valve/valve-list";
import AirDumpList from "./views/tests/air-pump/air-dump-list";
import BatteryTestList from "./views/tests/battery-test/battery-test-list";
import LatchButtonTestList from "./views/tests/latch-button-test/latch-button-test-list";
import OverPressureTestList from "./views/tests/over-pressure-test/over-pressure-test-list";
import PcbTestList from "./views/tests/pcb-test/pcb-test-list";
import HHDeviceList from "./views/hh_device/hh_device_list";
import FinalAssemblyList from "./views/fa/fa_list";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Provider store={store}>
        <BrowserRouter>
          <App>
            <Routes>
              <Route path="/users" element={<UserList />} />
              <Route path="/login" element={<Login />} />
              <Route path="/devices" element={<DevicesList />} />
              <Route path="/power-supply-test" element={<PowerSupplyList />} />
              <Route path="/valve-test" element={<ValveList />} />
              <Route path="/air-dump-test" element={<AirDumpList />} />
              <Route path="/battery-test" element={<BatteryTestList />} />
              <Route path="/latch-button-test" element={<LatchButtonTestList />} />
              <Route path="/over-pressure-test" element={<OverPressureTestList />} />
              <Route path="/pcb-test" element={<PcbTestList />} />
              <Route path="/hh-device" element={<HHDeviceList />} />
              <Route path="/fas" element={<FinalAssemblyList />} />
              <Route path="*" element={<Dashboard />} />
            </Routes>
          </App>
        </BrowserRouter>
      </Provider>
    </ThemeProvider>
  </React.StrictMode>
);
