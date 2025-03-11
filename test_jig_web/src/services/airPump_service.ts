import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';


export interface AirPumpTest {
    testId: number;
    deviceId: number;
    idleVoltageLowThresh: number;
    idleVoltageUpThresh: number;
    idleCurrentUpThresh: number;
    loadVoltageLowThresh: number;
    loadVoltageUpThresh: number;
    loadCurrentUpThresh: number;
    setPressure: number;
    serialNumber: string;
    idleVoltage: number;
    idleVoltageStatus: boolean | null;
    idleCurrent: number;
    idleCurrentStatus: boolean | null;
    loadVoltage: number;
    loadVoltageStatus: boolean | null;
    loadCurrent: number;
    loadCurrentStatus: boolean | null;
    flowRate: number;
    flowRateStatus: boolean | null;
    noiseLevelStatus: boolean | null;
    status: boolean | null;
    deviceStatus: boolean | null;
    dateTime: string;
}

export interface AirPumpV2Test {
    testId: number;
    deviceId: number;
    flowRateLowThresh: number;
    flowRateUpThresh: number;
    loadVoltageLowThresh: number;
    loadVoltageUpThresh: number;
    loadCurrentUpThresh: number;
    pressureLowThresh: number;
    pressureUpThresh: number;
    serialNumber: string;
    pressure: number;
    pressureStatus: boolean | null;
    loadVoltage: number;
    loadVoltageStatus: boolean | null;
    loadCurrent: number;
    loadCurrentStatus: boolean | null;
    flowRate: number;
    flowRateStatus: boolean | null;
    noiseLevelStatus: boolean | null;
    status: boolean | null;
    deviceStatus: boolean | null;
    dateTime: string;
}

export interface GetAirPumpV2Response {
    status: string;
    statusDescription: string;
    data: {
        tests: AirPumpV2Test[];
        totalRecords: number;
        totalFailed: number;
    };
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        tests: AirPumpTest[];
        totalRecords: number;
        totalFailed: number;
    };
}

export const airPumpApi = createApi({
    reducerPath: 'airPumpApi',
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
    tagTypes: ['airPumpTestList'],
    endpoints: (build) => ({
        getAirPumpTests: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/air-pump-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'airPumpTestList', id: "getAirPumpTests" }]
        }),
        getAirPumpQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/air-pump-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'airPumpTestList', id: 'getAirPumpQ' }]
        }),
        getAirPumpV2Tests: build.mutation<GetAirPumpV2Response, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/air-pump-v2-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'airPumpTestList', id: "getAirPumpV2Tests" }]
        }),
        getAirPumpV2Q: build.query<GetAirPumpV2Response, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/air-pump-v2-test/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'airPumpTestList', id: 'getAirPumpV2Q' }]
        }),
    }),

})

export const {
    useGetAirPumpTestsMutation,
    useGetAirPumpQQuery,
    useGetAirPumpV2TestsMutation,
    useGetAirPumpV2QQuery
} = airPumpApi