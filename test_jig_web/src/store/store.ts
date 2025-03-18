import { configureStore } from '@reduxjs/toolkit'
import { dashboardApi } from '../services/dashboard_service'
import { deviceApi } from '../services/device_service'
import { loginApi } from '../services/login_service'
import { userApi } from '../services/user_service'
import { airPumpApi } from '../services/airPump_service'
import { powerSupplyApi } from '../services/powerSupply_service'
import { valveApi } from '../services/valve_service'
import { loginSlice } from '../views/login/auth-slice'
import { pcbTestApi } from '../services/pcb_test_service'
import { btDeviceApi } from '../services/bt_device_service'
import { faApi } from '../services/fa_service'
import { powerPcbTestApi } from '../services/power_pcb_test_service'
import { opValveApi } from '../services/op_valve_service'
import { valveSequenceApi } from '../services/valve_sequence_service'
import { valveCardApi } from '../services/valve_card_service'
import { maniFoldLeakApi } from '../services/mani_fold_leak_service'
import { uiPcbApi } from '../services/ui_pcb_service'
import { cableTestApi } from '../services/cable_test_service'
import { fanTestApi } from '../services/fan_test_service'
import { displayTestApi } from '../services/display_test_service'
import { mainPCBTestApi } from '../services/Main_PCB_test_service'

export const store = configureStore({
    reducer: {
        [loginApi.reducerPath]: loginApi.reducer,
        [deviceApi.reducerPath]: deviceApi.reducer,
        [userApi.reducerPath]: userApi.reducer,
        [dashboardApi.reducerPath]: dashboardApi.reducer,
        [airPumpApi.reducerPath]: airPumpApi.reducer,
        [powerSupplyApi.reducerPath]: powerSupplyApi.reducer,
        [valveApi.reducerPath]: valveApi.reducer,
        [pcbTestApi.reducerPath]: pcbTestApi.reducer,
        [powerPcbTestApi.reducerPath]: powerPcbTestApi.reducer,
        [btDeviceApi.reducerPath]: btDeviceApi.reducer,
        [opValveApi.reducerPath]: opValveApi.reducer,
        [faApi.reducerPath]: faApi.reducer,
        [valveSequenceApi.reducerPath]: valveSequenceApi.reducer,
        [valveCardApi.reducerPath]: valveCardApi.reducer,
        [maniFoldLeakApi.reducerPath]: maniFoldLeakApi.reducer,
        [uiPcbApi.reducerPath]: uiPcbApi.reducer,
        [cableTestApi.reducerPath]: cableTestApi.reducer,
        [fanTestApi.reducerPath]: fanTestApi.reducer,
        [displayTestApi.reducerPath]: displayTestApi.reducer,
        [mainPCBTestApi.reducerPath]: mainPCBTestApi.reducer,
        loginStatus: loginSlice.reducer
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(loginApi.middleware)
            .concat(deviceApi.middleware)
            .concat(userApi.middleware)
            .concat(dashboardApi.middleware)
            .concat(airPumpApi.middleware)
            .concat(powerSupplyApi.middleware)
            .concat(pcbTestApi.middleware)
            .concat(btDeviceApi.middleware)
            .concat(faApi.middleware)
            .concat(powerPcbTestApi.middleware)
            .concat(opValveApi.middleware)
            .concat(valveSequenceApi.middleware)
            .concat(valveCardApi.middleware)
            .concat(maniFoldLeakApi.middleware)
            .concat(uiPcbApi.middleware)
            .concat(cableTestApi.middleware)
            .concat(fanTestApi.middleware)
            .concat(displayTestApi.middleware)
            .concat(mainPCBTestApi.middleware)
            .concat(valveApi.middleware),
})

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch