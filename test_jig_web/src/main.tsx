import { CssBaseline, ThemeProvider } from "@mui/material";
import React from "react";
import ReactDOM from "react-dom/client";
import { Provider } from "react-redux";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import App from "./App";

import { store } from "./store/store";
import Dashboard from "./views/dashboard/dashboard";
import { theme } from "./theme";
import Login from "./views/login/login";
import PowerSupplyList from "./views/tests/power-supply/power-supply-list";
import ValveList from "./views/tests/valve/valve-list";
import AirPumpList from "./views/tests/air-pump/air-dump-list";
import PowerPcbTestList from "./views/tests/power-pcb/power-pcb-test-list";
import BTDeviceList from "./views/bt_device/bt_device_list";
import FinalAssemblyList from "./views/fa/fa_list";
import AirPumpV2List from "./views/tests/air-pump-v2/air-pump-v2-list";
import PowerPcbV2TestList from "./views/tests/power-pcb-v2/power-pcb-v2-test-list";
import PowerSupplyV2List from "./views/tests/power-supply-v2/power-supply-v2-list";
import OPValveList from "./views/tests/op-valve/op-valve-list";
import ValveSequenceList from "./views/tests/valve-sequence/valve-sequence-list";
import ValveCardList from "./views/tests/valve-card/valve-card-list";
import ManiFoldLeakList from "./views/tests/mani-fold-leak/mani-fold-leak-list";
import UIPcbList from "./views/tests/ui-pcb/ui-pcb-list";
import CableList from "./views/tests/cable/cable-list";
import FanList from "./views/tests/fan/fan-list";
import DisplayList from "./views/tests/display/display-list";

ReactDOM.createRoot(document.getElementById("root") as HTMLElement).render(
  <React.StrictMode>
    <ThemeProvider theme={theme}>
      <CssBaseline />
      <Provider store={store}>
        <BrowserRouter>
          <App>
            <Routes>
              <Route path="/login" element={<Login />} />
              <Route path="/air-pump" element={<AirPumpList />} />
              <Route path="/air-pump-2" element={<AirPumpV2List />} />
              <Route path="/power-supply-test" element={<PowerSupplyList />} />
              <Route path="/power-supply-test-2" element={<PowerSupplyV2List />} />
              <Route path="/valve-test" element={<ValveList />} />
              <Route path="/power-pcb-test" element={<PowerPcbTestList />} />
              <Route path="/power-pcb-test-2" element={<PowerPcbV2TestList />} />
              <Route path="/op-valve-test" element={<OPValveList />} />
              <Route path="/display-test" element={<DisplayList />} />
              <Route path="/valve-sequence-test" element={<ValveSequenceList />} />
              <Route path="/valve-card-test" element={<ValveCardList />} />
              <Route path="/mani-fold-leak-test" element={<ManiFoldLeakList />} />
              <Route path="/ui-pcb-test" element={<UIPcbList />} />
              <Route path="/cable-test" element={<CableList />} />
              <Route path="/fan-test" element={<FanList />} />
              <Route path="/bt-device" element={<BTDeviceList />} />
              <Route path="/fas" element={<FinalAssemblyList />} />
              <Route path="*" element={<Dashboard />} />
            </Routes>
          </App>
        </BrowserRouter>
      </Provider>
    </ThemeProvider>
  </React.StrictMode>
);
