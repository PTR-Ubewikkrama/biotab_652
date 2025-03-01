import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface PowerSupplyTest {
    testId: number;
    deviceId: number;
    idleVolLowTh: number;
    idleVolUpTh: number;
    loadVolLowTh: number;
    loadVolUpTh: number;
    loadCurUpTh: number;
    serialNumber: string;
    idleVol: number;
    idleVolStatus: boolean;
    loadVol: number;
    loadVolStatus: boolean;
    loadCurrent: number;
    loadCurrentStatus: boolean;
    operatingPower: number;
    noiseLevel: boolean;
    deviceStatus: string | null;
    status: boolean | null;
    dateTime: string;
}

export interface PowerSupplyTestsResponse {
    status: string;
    statusDescription: string;
    data: {
        powerSupplyTests: PowerSupplyTest[];
        totalRecords: number;
    };
}

export const powerSupplyApi = createApi({
    reducerPath: 'powerSupplyApi',
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
    tagTypes: ['powerSupplyList'],
    endpoints: (build) => ({
        getPowerSupplyTests: build.mutation<PowerSupplyTestsResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/power-supply-test/all`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'powerSupplyList', id: "getPowerSupplyTests" }]
        }),
        getPowerSupplyTestQ: build.query<PowerSupplyTestsResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `test/get/power-supply-test/all`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'powerSupplyList', id: "getPowerSupplyTests" }]
        }),
    }),
})

export const {
    useGetPowerSupplyTestsMutation,
    useGetPowerSupplyTestQQuery
} = powerSupplyApi