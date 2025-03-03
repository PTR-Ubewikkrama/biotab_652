import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface PowerPcbTest {
    testId: number;
    serialNumber: string;
    powerGroundResistanceUpperLimit: number;
    powerGroundResistance: number;
    powerGroundResistanceStatus: boolean;
    dcBarrelJackConnectivityStatus: boolean;
    usbCPowerOutletConnectivity: boolean;
    status: boolean;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: PowerPcbTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export interface PowerPcbV2TestDto {
    testId: number;
    deviceId: number;
    loadVoltageLowThresh: number;
    serialNumber: string;
    loadCurrentLowThresh: number;
    usbCPowerOutletConnectivity: string;
    loadVoltage: number;
    loadVoltageStatus: boolean;
    loadCurrentStatus: boolean;
    loadCurrent: number;
    deviceStatus: boolean;
    noiseLevelStatus: boolean;
    status: boolean;
    dateTime: string;
}

export interface ApiResponseV2 {
    status: string;
    statusDescription: string;
    data: {
        tests: PowerPcbV2TestDto[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const powerPcbTestApi = createApi({
    reducerPath: 'powerPcbTestApi',
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
    tagTypes: ['powerPcbTestList'],
    endpoints: (build) => ({
        getPowerPcbTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/power-pcb-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'powerPcbTestList', id: "getAirPumpTests" }]
        }),
        getPowerPcbTestQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/power-pcb-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'powerPcbTestList', id: 'getAirPumpQ' }]
        }),
        getPowerPcbV2Tests: build.mutation<ApiResponseV2, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/power-pcb-v2-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'powerPcbTestList', id: "getAirPumpTests" }]
        }),
        getPowerPcbV2TestQ: build.query<ApiResponseV2, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/power-pcb-v2-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'powerPcbTestList', id: 'getAirPumpQ' }]
        }),
    }),
})

export const {
    useGetPowerPcbTestsMutation,
    useGetPowerPcbTestQQuery,
    useGetPowerPcbV2TestsMutation,
    useGetPowerPcbV2TestQQuery
} = powerPcbTestApi