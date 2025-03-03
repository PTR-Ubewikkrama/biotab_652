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
        totalSuccessPowerPcbTest: number;
        totalFailedPowerPcbTest: number;
        totalSuccessAirPumpV2Test: number;
        totalFailedAirPumpV2Test: number;
        totalSuccessPowerSupplyV2Test: number;
        totalFailedPowerSupplyV2Test: number;
        totalSuccessPowerPcbV2Test: number;
        totalFailedPowerPcbV2Test: number;
        totalSuccessOpValveTest: number;
        totalFailedOpValveTest: number;
        totalSuccessValveSequenceTest: number;
        totalFailedValveSequenceTest: number;
        totalSuccessValveCardTest: number;
        totalFailedValveCardTest: number;
        totalSuccessManiFoldLeakTest: number;
        totalFailedManiFoldLeakTest: number;
        totalSuccessUiPcbTest: number;
        totalFailedUiPcbTest: number;
        totalSuccessCableTest: number;
        totalFailedCableTest: number;
        totalSuccessFanTest: number;
        totalFailedFanTest: number;
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