import { createApi, fetchBaseQuery } from '@reduxjs/toolkit/query/react'
import config from '../config/config';

export interface DeviceHHDto {
    deviceId: number;
    deviceCode: string;
    deviceCodeStatus: string;
    pcbTestCode: string;
    pcbTestCodeStatus: string;
    valveTestOneCode: string;
    valveTestOneCodeStatus: string;
    valveTestTwoCode: string;
    valveTestTwoCodeStatus: string;
    airPumpTestCode: string;
    airPumpTestCodeStatus: string;
    latchButtonTestCode: string;
    latchButtonTestCodeStatus: string;
    overPressureValveTestCode: string;
    overPressureValveTestCodeStatus: string;
    batteryTestCode: string;
    batteryTestCodeStatus: string;
    enclosureCode: string;
    enclosureCodeStatus: string;
    airBladderCode: string;
    airBladderCodeStatus: string;
    powerSupplyTestCode: string;
    powerSupplyTestCodeStatus: string;
    createdBy: string;
    dateTime: string;
}

export interface ApiResponse {
    status: string;
    statusDescription: string;
    data: {
        devices: DeviceHHDto[];
        total: number;
    };
}

export const hhDeviceApi = createApi({
    reducerPath: 'hhDeviceApi',
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
    tagTypes: ['hhDeviceList'],
    endpoints: (build) => ({
        getHHDevices: build.mutation<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `hh_device/get/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            invalidatesTags: [{ type: 'hhDeviceList', id: "getAirPumpTests" }]
        }),
        getHHDevicesQ: build.query<ApiResponse, { data: {}, page: string }>({
            query(data) {
                return {
                    url: `hh_device/get/${data.page}`,
                    method: 'POST',
                    body: data.data,
                }
            },
            providesTags: [{ type: 'hhDeviceList', id: 'getAirPumpQ' }]
        }),
    }),

})

export const {
    useGetHHDevicesMutation,
    useGetHHDevicesQQuery
} = hhDeviceApi