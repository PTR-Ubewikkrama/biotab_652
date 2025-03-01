import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface DashBoardSummaryResponse {
    status: string;
    statusDescription: string;
    data: {
        totalSuccessAirPumpTest: number;
        totalFailedAirPumpTest: number;
        totalSuccessPowerSupplyTest: number;
        totalFailedPowerSupplyTest: number;
        totalSuccessValueTest: number;
        totalFailedValueTest: number;
        totalSuccessPcbTest: number;
        totalFailedPcbTest: number;
        totalSuccessOverPressureTest: number;
        totalFailedOverPressureTest: number;
        totalSuccessLatchButtonTest: number;
        totalFailedLatchButtonTest: number;
        totalSuccessBatteryTest: number;
        totalFailedBatteryTest: number;
        totalFinalAssembly: number;
        totalHHDevice: number;
    };
}


export const dashboardApi = createApi({
    reducerPath: 'dashboardApi',
    baseQuery: fetchBaseQuery({
        baseUrl: config.apiBaseUrl,
        prepareHeaders: (headers) => {
            const token = localStorage.getItem("jwt") as string;
            if (!headers.has("Authorization") && token) {
                headers.set("Authorization", `Bearer ${token}`);
            }
            return headers;
        },
    }),
    tagTypes: ['dashboard'],
    endpoints: (build) => ({
        getDashboardSummary: build.query<DashBoardSummaryResponse, {}>({
            query: () => `dashboard/get/summary`,
            providesTags: [
                { type: 'dashboard', id: "getDashboardSummary" }
            ]
        }),
    }),
})

export const {
    useGetDashboardSummaryQuery,
} = dashboardApi