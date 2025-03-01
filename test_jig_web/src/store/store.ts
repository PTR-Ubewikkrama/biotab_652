import { configureStore } from '@reduxjs/toolkit'
import { dashboardApi } from '../services/dashboard_service'
import { deviceApi } from '../services/device_service'
import { loginApi } from '../services/login_service'
import { userApi } from '../services/user_service'
import { airPumpApi } from '../services/airPump_service'
import { powerSupplyApi } from '../services/powerSupply_service'
import { valveApi } from '../services/valve_service'
import { loginSlice } from '../views/login/auth-slice'
import { batteryTestApi } from '../services/battery_test_service'
import { overPressureTestApi } from '../services/over_pressure_test_service'
import { latchButtonTestApi } from '../services/latch_button_test_service'
import { pcbTestApi } from '../services/pcb_test_service'
import { hhDeviceApi } from '../services/hh_device_service'
import { faApi } from '../services/fa_service'

export const store = configureStore({
    reducer: {
        [loginApi.reducerPath]: loginApi.reducer,
        [deviceApi.reducerPath]: deviceApi.reducer,
        [userApi.reducerPath]: userApi.reducer,
        [dashboardApi.reducerPath]: dashboardApi.reducer,
        [airPumpApi.reducerPath]: airPumpApi.reducer,
        [powerSupplyApi.reducerPath]: powerSupplyApi.reducer,
        [valveApi.reducerPath]: valveApi.reducer,
        [batteryTestApi.reducerPath]: batteryTestApi.reducer,
        [overPressureTestApi.reducerPath]: overPressureTestApi.reducer,
        [latchButtonTestApi.reducerPath]: latchButtonTestApi.reducer,
        [pcbTestApi.reducerPath]: pcbTestApi.reducer,
        [hhDeviceApi.reducerPath]: hhDeviceApi.reducer,
        [faApi.reducerPath]: faApi.reducer,
        loginStatus: loginSlice.reducer
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(loginApi.middleware)
            .concat(deviceApi.middleware)
            .concat(userApi.middleware)
            .concat(dashboardApi.middleware)
            .concat(airPumpApi.middleware)
            .concat(powerSupplyApi.middleware)
            .concat(batteryTestApi.middleware)
            .concat(overPressureTestApi.middleware)
            .concat(latchButtonTestApi.middleware)
            .concat(pcbTestApi.middleware)
            .concat(hhDeviceApi.middleware)
            .concat(faApi.middleware)
            .concat(valveApi.middleware),
})

// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch