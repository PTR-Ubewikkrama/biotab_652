import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface AirPumpTest {
    testId: number;
    deviceId: number;
    idleVolLowTh: number;
    idleVolUpTh: number;
    idleCurUpTh: number;
    loadVolLowTh: number;
    loadVolUp: number;
    loadCurUpTh: number;
    setPressure: number;
    serialNumber: string;
    idleVol: number;
    idleVolStatus: boolean;
    idleCurrent: number;
    idleCurrentStatus: boolean;
    loadVoltage: number;
    loadVoltageStatus: boolean;
    loadCurrent: number;
    loadCurrentStatus: boolean;
    maxPressure: number;
    maxPressureStatus: boolean;
    noiseLevel: boolean;
    dateTime: string;
    status: boolean | null;
    deviceStatus: boolean | null;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        airPumpTests: AirPumpTest[];
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
    }),

})

export const {
    useGetAirPumpTestsMutation,
    useGetAirPumpQQuery
} = airPumpApi